package com.ovit.nswy.member.certification.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.member.certification.model.BankAccount;
import com.ovit.nswy.member.certification.model.FavoriteSpecies;
import com.ovit.nswy.member.certification.model.FriendGroup;
import com.ovit.nswy.member.certification.model.LoginUser;
import com.ovit.nswy.member.certification.service.*;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.util.CertificationStepEnum;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 个性化模块
 *
 */
@RestController
@RequestMapping("/indivi")
public class IndividuationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private WebsiteSettingsService websiteSettingsService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private FriendGroupService friendGroupService;

    @Autowired
    private SysFollowDictService sysFollowDictService;

    @Autowired
    private SpecieService service;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 个性化，保存数据
     * @param params
     */
    @RequestMapping(value = "/saveIndividInfo", method = RequestMethod.POST)
    public Result saveIndividInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            logger.debug("数据格式：{}", params);
            String account = MapUtils.getString(params,"LoginAccount");
            String templateId = MapUtils.getString(params,"templateId");
            Map<String,Object> accountInfo = new HashMap<>();
            accountInfo.put("account", account);
            accountInfo.put("templateId", templateId);

            //账户设置的数据
            Map<String,Object> bankInfo = (Map<String, Object>) params.get("BankSettingData");
            if (MapUtils.isNotEmpty(bankInfo)) {
                //获取验证码
                bankInfo.put("account", account);
                bankInfo.put("templateId", templateId);
                boolean saveBank = bankAccountService.saveBankInfo(bankInfo);
                if (!saveBank) {
                    result.setCode(ResultCode.FAIL);
                    result.setMsg("验证码不正确");
                    return result;
                }
            }
            List<Map<String,Object>> FollowData = (List<Map<String, Object>>) params.get("FollowData");
            if (CollectionUtils.isNotEmpty(FollowData)) {
                //保存知识、资讯、政策关注信息
                sysFollowDictService.followInsert(FollowData, accountInfo);

                //关联物种的数据
                List<Map<String,Object>> relevaData  = (List<Map<String, Object>>) FollowData.get(0).get("releva");
                String associatedSpeciesInfo = JSON.toJSONString(relevaData.get(0));
                FavoriteSpecies associatedSpecies = service.findSpecByAccount(accountInfo);
                if (null == associatedSpecies) {
                    //无历史数据，则新增数据
                    associatedSpecies = new FavoriteSpecies();
                    associatedSpecies.setAccount(account);
                    associatedSpecies.setSpeciesName(associatedSpeciesInfo);
                    associatedSpecies.setTemplateId(templateId);
                    logger.info("新增关联物种信息为：{}", associatedSpecies);
                    service.insert(associatedSpecies);
                } else {
                    //有历史数据，则更新数据
                    associatedSpecies.setSpeciesName(associatedSpeciesInfo);
                    logger.info("更新关联物种信息为：{}", associatedSpecies);
                    service.updateByPrimaryKey(associatedSpecies);
                }

                //保存推送状态
                boolean push = MapUtils.getBoolean(FollowData.get(0), "flag");
                accountInfo.put("flag", push);
                sysFollowDictService.savePushStatus(accountInfo);

            } else {
                //删除之前存在的数据
                sysFollowDictService.followDelete(accountInfo);
                service.deleteSpecByAccount(accountInfo);
                sysFollowDictService.deletePushStatus(accountInfo);

            }

            //设置好友分组的数据
            List<Map<String, Object>> friendGroupList = (List<Map<String, Object>>) params.get("FriendGroupData");
            if (friendGroupList.size() > 0) {
                int addNum = friendGroupService.addFriendGroup(friendGroupList, accountInfo);
            }

            /*//保存认证步骤到登陆表中
            LoginUser loginInfo = loginUserService.findByLoginName(account);
            int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
            if (step < 3) {
                //保存认证步骤到登陆表中
                Map<String,Object> updateIdentity = new HashMap<>();
                updateIdentity.put("user_id", account);
                updateIdentity.put("flag", String.valueOf(CertificationStepEnum.STEP_INDIVICATIONSETTINGS.getStepNum()));
                loginUserService.updateIdentityFlag(updateIdentity);
            }*/

            Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

            //保存用户步骤
            loginUserService.saveOrUpdateStep(loginMap);

            logger.info("个性化信息保存成功");

        } catch (Exception e) {
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("个性化信息保存失败");
        }
        return result;
    }

    /**
     * 查询个性化信息
     */
    @RequestMapping(value = "/findIndividInfo", method = RequestMethod.POST)
    public Result findIndividInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> individInfo = new HashMap<>();
            String account = MapUtils.getString(params,"account");
            String templateId = MapUtils.getString(params,"templateId");
            Map<String,Object> accountInfo = new HashMap<>();
            accountInfo.put("account", account);
            accountInfo.put("templateId", templateId);
            //知识、资讯、政策关注列表
            Map<String,Object> followMap = new HashMap<>();
            followMap.put("follow_type", params.get("follow_type"));
            followMap.put("account", account);
            followMap.put("templateId", templateId);
            List<Map<String,Object>> followList = sysFollowDictService.findList(followMap);

            /**
             * 当用户侧无数据时，获取管理员配置的模版数据
             */
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", templateId);
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
            } else {
                historyInfo.put("account",account);
            }
            historyInfo.put("templateId", templateId);
            if (followList.size() == 0) {

                followList = sysFollowDictService.findList(historyInfo);

            }

            if(followList.isEmpty()){
                individInfo.put("FollowListData", "");
            } else {
                individInfo.put("FollowListData", followList);
            }
            //知识、资讯、政策已关注信息
            followMap.put("adminAccount", MapUtils.getString(historyInfo, "account"));
            List<List<Map<String,Object>>> hasFollowList = sysFollowDictService.findFllowDict(followMap);

            List<List<Map<String,Object>>> relevaList = new ArrayList<>();

            List<Map<String,Object>> followDataList = new ArrayList<>();

            Map<String,Object> followDataMap = new HashMap<>();

            if(MapUtils.getString(accountInfo,"account") !=null&&MapUtils.getString(accountInfo,"templateId")!=null){
                //已关联物种的数据
                FavoriteSpecies favoriteSpecies = service.findSpecByAccount(accountInfo);
                List<Map<String,Object>> associatedSpeciesData = new ArrayList<>();
                if(null == favoriteSpecies){

                    favoriteSpecies = service.findSpecByAccount(historyInfo);
                }

                if (null == favoriteSpecies) {

                } else {
                    JSONArray associatedSpecies = JSONArray.parseArray(favoriteSpecies.getSpeciesName());
                    associatedSpeciesData  = jsonArrayToList(associatedSpecies);
                }
                relevaList.add(associatedSpeciesData);
                relevaList.add(new ArrayList<>());
                relevaList.add(new ArrayList<>());

                //查询是否推送
                Map<String,Object> pushStatus = sysFollowDictService.findPushStatus(accountInfo);
                if (MapUtils.isEmpty(pushStatus)) {

                    pushStatus = sysFollowDictService.findPushStatus(historyInfo);

                }

                if (MapUtils.isEmpty(pushStatus)) {


                    followDataMap.put("flag", true);
                } else {
                    followDataMap.put("flag", "1".equals(MapUtils.getString(pushStatus, "flag")) ? true : false);
                }
            }else{
                followDataMap.put("flag", true);
            }


            followDataMap.put("follow", hasFollowList);
            followDataMap.put("releva", relevaList);
            followDataList.add(followDataMap);

            individInfo.put("FollowData", followDataList);
            if(MapUtils.getString(accountInfo,"account") !=null&&MapUtils.getString(accountInfo,"templateId")!=null){
                //收藏文件夹的数据
                Map<String, Object> collectMap = collectService.queryAll(accountInfo);
                if(MapUtils.getString(collectMap,"tree").equals("")){
                    collectMap = collectService.queryAll(historyInfo);
                }
                if (MapUtils.isEmpty(collectMap)) {
                    individInfo.put("CollectData", "");
                } else {
                    individInfo.put("CollectData", collectMap.get("tree"));
                }
            }

            if(MapUtils.getString(accountInfo,"account") !=null&&MapUtils.getString(accountInfo,"templateId")!=null){
                //好友分组数据
                List<FriendGroup> friendGrouplist = friendGroupService.selectByAccount(accountInfo);
                if (friendGrouplist.size() == 0) {
                    friendGrouplist = friendGroupService.selectByAccount(historyInfo);
                }

                if (friendGrouplist.size() == 0) {
                    individInfo.put("FriendGroupData", "");
                } else {
                    individInfo.put("FriendGroupData", friendGrouplist);
                }
            }else{
                individInfo.put("FriendGroupData", "");
            }

            //账户设置数据
            if(MapUtils.getString(accountInfo,"account") !=null&&MapUtils.getString(accountInfo,"templateId")!=null){
                BankAccount bankAccount = bankAccountService.selectByAccount(accountInfo);
                if (null == bankAccount) {
                    bankAccount = bankAccountService.selectByAccount(historyInfo);
                }
                if (null == bankAccount) {
                    individInfo.put("BankSettingData", "");
                } else {
                    Map<String,Object> bankMap = new HashMap<>();
                    bankMap.put("name", bankAccount.getRealname());
                    bankMap.put("id", bankAccount.getIdcard());
                    bankMap.put("bankCard", bankAccount.getBankaccount());
                    bankMap.put("mobile", bankAccount.getPhone());
                    bankMap.put("password", bankAccount.getPassword());
                    individInfo.put("BankSettingData", bankMap);
                }
            }else{
                individInfo.put("BankSettingData", "");
            }
            //获取物种分类数据
            List<Map<String,Object>> speciesclassList = service.listAllSpeciesClass();
            individInfo.put("SpeciesclassData", speciesclassList);

            result.setData(individInfo);

        } catch (Exception e) {
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询个性化信息失败");
        }
        logger.debug("查询个性化信息为： {}", result.getData());
        return result;
    }

    /**
     * 资讯，政策，知识
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFollowDict", method = RequestMethod.POST)
    public Result findFollowDict(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            Map<String,Object> individInfo = new HashMap<>();
            List<Map<String,Object>> followList = sysFollowDictService.findList(params);
            if (followList.size() == 0) {
                individInfo.put("FollowListData", "");
            } else {
                individInfo.put("FollowListData", followList);
            }
            //知识、资讯、政策已关注信息
            List<List<Map<String,Object>>> hasFollowList = sysFollowDictService.findFllowDict(params);
            if (hasFollowList.size() == 0) {
                individInfo.put("HasFollowListData", "");
            } else {
                individInfo.put("HasFollowListData", hasFollowList);
            }
            result.setData(individInfo);
        }catch (Exception e) {
            logger.error("异常信息：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询关注信息失败");
        }
        logger.info("查询关注信息成功： {}", result.getData());
        return  result;
    }

    /**
     * JSONArray转为List
     */
    private List<Map<String,Object>> jsonArrayToList(JSONArray jsonArray) {

        List<Map<String,Object>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Map<String,Object> t = JSONObject.toJavaObject(jsonObject, Map.class);
            list.add(t);
        }
        return list;
    }

}
