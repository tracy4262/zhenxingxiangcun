package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.common.enums.CertificationStepEnum;
import com.ovit.nswy.member.feigen.RelatedServiceFeign;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import com.sun.xml.internal.fastinfoset.util.StringArray;
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
 * 关注模块控制器
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/indivi")
public class IndividuationController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IndividuationService individuationService;
    @Autowired
    private SpecieService service;
    @Autowired
    private FavoriteKnowledgeService fkservice;
    @Autowired
    private FavoritePolicyService fpservice;
    @Autowired
    private FavoriteInformationService fiservice;
    @Autowired
    private RemarkService remark;
    @Autowired
    private ReportService reportService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private FriendGroupService friendGroupService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private RelatedServiceFeign relatedServiceFeign;
    @Autowired
    private SysFollowDictService sysFollowDictService;


    // 通过类别查找物种
    @RequestMapping(value = "species", method = RequestMethod.POST)
    private Object listSpecies(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        List<Map<String, Object>> spec = (List<Map<String, Object>>) params.get("species");
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Result result = new Result();
        if (!spec.isEmpty()) {
            List<String> species = new ArrayList<>();
            for (Map<String, Object> s : spec) {
                JSONObject js = new JSONObject(s);
                String fileName = (String) js.get("name");
                String val = js.get("value").toString();
                List<String> list = individuationService.selectByLeib(val);
                Map<String, Object> map1 = new HashMap();
                map1.put("name", fileName);
                map1.put("value", list);
                maps.add(map1);
            }
            result.setData(maps);
        } else {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setData("null");
        }
        return result;
    }

    // 保存关注的物种
    @RequestMapping(value = "save", method = RequestMethod.POST)
    private Object saveSpecies(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            FavoriteSpecies favoriteSpecies = new FavoriteSpecies();
            String account = params.get("account").toString();
            String fieldName = params.get("fieldName").toString();
            String speciesName =params.get("speciesName").toString();
            favoriteSpecies.setAccount(account);
            favoriteSpecies.setFieldName(fieldName);
            favoriteSpecies.setSpeciesName(speciesName);
            service.empty(account);
            service.insert(favoriteSpecies);
            result.setData("OK");
            result.setCode(ResultCode.SUCCESS);
            String step = params.get("step").toString();
            if (org.springframework.util.StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setData(e.getMessage());
            return result;
        }
        return result;
    }

    // 保存收藏
    @RequestMapping(value = "remark", method = RequestMethod.POST)
    private Object saveRemark(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            String spec = (String) params.get("remark");
            if (StringUtils.isEmpty(spec)) {
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                return result;
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("account", account);
                map.put("data", spec);
                map.put("createtime", new Date());
                Map<String, Object> oldmap = remark.selectByAccount(account);
                if (oldmap.isEmpty()) {
                    remark.insert(map);
                } else {
                    oldmap.put("data", spec);
                    oldmap.put("createtime", new Date());
                    remark.insert(oldmap);
                }
                result.setData("OK");
                String step = params.get("step").toString();
                if (org.springframework.util.StringUtils.hasText(step)) {
                    String str = step.substring(12, 16);
                    if (!str.equals("prog")) {
                        loginUserService.updateIdentityFlag(account, step);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    // 查找选择物种的类型
    @RequestMapping(value = "findFieldName", method = RequestMethod.POST)
    private Object findFieldName(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        List<String> spec = (List<String>) params.get("species");
        List<String> results = new ArrayList<String>();
        Map<String, String> fieldName = new HashMap<>();
        fieldName.put("WZ0101 ", "粮食类");
        fieldName.put("WZ0102", "纤维类");
        fieldName.put("WZ0103", "油料类");
        fieldName.put("WZ0104", "糖料类");
        fieldName.put("WZ0105", "饮料类");
        fieldName.put("WZ0106", "调料类");
        fieldName.put("WZ0107", "染料类");
        fieldName.put("WZ0108", "漆料类");
        fieldName.put("WZ0109", "胶液料类");
        fieldName.put("WZ0110", "鞣料类");
        fieldName.put("WZ0111", "嗜好类");
        fieldName.put("WZ0112", "药用类");
        fieldName.put("WZ0113", "香料类");
        fieldName.put("WZ0114", "瓜果蔬类");
        fieldName.put("WZ0115", "能源类");
        fieldName.put("WZ0116", "食用菌藻类");
        fieldName.put("WZ0117", "花卉类");
        fieldName.put("WZ0118", "饲料类");
        fieldName.put("WZ0119", "肥料类");
        fieldName.put("WZ0120", "防护用材林木");
        fieldName.put("WZ0121", "绿化观赏林木");
        fieldName.put("WZ0201", "肉用类");
        fieldName.put("WZ0202", "肉蛋兼用类");
        fieldName.put("WZ0203", "肉奶兼用类");
        fieldName.put("WZ0204", "肉毛(绒)兼用类");
        fieldName.put("WZ0206", "肉皮兼用类");
        fieldName.put("WZ0207", "肉药兼用类");
        fieldName.put("WZ0208", "役力类");
        fieldName.put("WZ0209", "丝蜜类");
        fieldName.put("WZ0210", "宠娱观赏类");
        fieldName.put("WZ0211", "实验类");
        fieldName.put("WZ0213", "饲饵类");
        Result result = new Result();
        if (spec != null && spec.size() > 0) {
            for (String speciesName : spec) {
                //得到当前物种类型
                String temp = individuationService.selectByName(speciesName);
                results.add(fieldName.get(temp));
            }
        }
        result.setData(results);
        return result;
    }

    //寻找已经关注的物种
    @RequestMapping(value = "hadSaveSpecies", method = RequestMethod.POST)
    private Object hadSaveSpecies(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Result result = new Result();
        try {
            logger.info("param->{0}",params);
            String account = params.get("account").toString();
            FavoriteSpecies favoriteSpecies = service.findSpecByAccount(account);
            if(null != favoriteSpecies){
                result.setData(favoriteSpecies);
            }else {
                result.setData("");
            }
            logger.info("favoriteSpecies->{0}",favoriteSpecies);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCode.FAIL);
            result.setData(e.getMessage());
            return result;
        }
        return result;
    }

    private boolean equals(String temp) {
        return temp.equalsIgnoreCase("肉用类") || temp.equalsIgnoreCase("肉蛋兼用类") || temp.equalsIgnoreCase("肉奶兼用类") || temp.equalsIgnoreCase("肉毛(绒)兼用类") || temp.equalsIgnoreCase("肉皮兼用类") || temp.equalsIgnoreCase("肉药兼用类")
                || temp.equalsIgnoreCase("役力类") || temp.equalsIgnoreCase("丝蜜类") || temp.equalsIgnoreCase("宠娱观赏类") || temp.equalsIgnoreCase("实验类") || temp.equalsIgnoreCase("饲饵类");
    }

    //寻找已经关注的知识
    @RequestMapping(value = "hadSaveKnowlege", method = RequestMethod.POST)
    private Object hadSaveKnowlege(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        Result result = new Result();
        FavoriteKnowledge fav = fkservice.selectByAccount(account);
        // 关注的知识类型
        String category = fav.getCategory();
        //是否关联物种,产品,服务
        Integer referSpecies = fav.getReferSpecies();
        Integer referService = fav.getReferService();
        Integer referProduct = fav.getReferProduct();
        // 关联的知识
        //List<String> spec = (List<String>) params.get("ledge");
        // 关联的物种,产品,服务
        //List<String> leibie = (List<String>) params.get("leibie");
        Map<String, List<String>> datas = new HashMap<>();
        String[] categorys = null;
        if (category != null && category.contains(","))
            categorys = category.split(",");
        else
            categorys = new String[]{category};
        List<String> ledge = Arrays.asList(categorys);
        List<String> leibie = new ArrayList<>();
        if (referSpecies == 1) {
            leibie.add("关联物种");
        }
        if (referService == 1) {
            leibie.add("关联服务");
        }
        if (referProduct == 1) {
            leibie.add("关联产品");
        }
        datas.put("ledge", ledge);
        datas.put("leibie", leibie);
        result.setData(datas);
        return result;
    }

    //查找已经关注的咨询
    @RequestMapping(value = "hadSaveInfo", method = RequestMethod.POST)
    private Object hadSaveInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        Result result = new Result();
        FavoriteInformation fav = fiservice.selectByAccount(account);
        // 关注的资讯类型
        String category = fav.getCategory();
        //是否关联物种,产品,服务
        Integer referSpecies = fav.getReferSpecies();
        Integer referService = fav.getReferService();
        Integer referProduct = fav.getReferProduct();
        Map<String, List<String>> datas = new HashMap<>();
        String[] categorys = category.split(",");
        List<String> ledge = Arrays.asList(categorys);
        List<String> leibie = new ArrayList<>();
        if (referSpecies == 1) {
            leibie.add("关联物种");
        }
        if (referService == 1) {
            leibie.add("关联服务");
        }
        if (referProduct == 1) {
            leibie.add("关联产品");
        }
        datas.put("ledge", ledge);
        datas.put("leibie", leibie);
        result.setData(datas);
        return result;
    }

    //查找已经关注的政策
    @RequestMapping(value = "hadSavePolicy", method = RequestMethod.POST)
    private Object hadSavePolicy(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        Result result = new Result();
        FavoritePolicy fav = fpservice.selectByAccount(account);
        // 关注的政策类型
        String category = fav.getCategory();
        //是否关联物种,产品,服务
        Integer referSpecies = fav.getReferSpecies();
        Integer referService = fav.getReferService();
        Integer referProduct = fav.getReferProduct();
        Map<String, List<String>> datas = new HashMap<>();
        String[] categorys = category.split(",");
        List<String> ledge = Arrays.asList(categorys);
        List<String> leibie = new ArrayList<>();
        if (referSpecies == 1) {
            leibie.add("关联物种");
        }
        if (referService == 1) {
            leibie.add("关联服务");
        }
        if (referProduct == 1) {
            leibie.add("关联产品");
        }
        datas.put("ledge", ledge);
        datas.put("leibie", leibie);
        result.setData(datas);
        return result;
    }

    // 保存关注知识
    @RequestMapping(value = "ledge", method = RequestMethod.POST)
    private Object saveKnowlege(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        List<String> spec = (List<String>) params.get("ledge");
        List<String> leibie = (List<String>) params.get("leibie");
        String know = null;
        Result result = new Result();
        if (spec.isEmpty() || leibie.isEmpty()) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        } else {
            for (int i = 0; i < spec.size(); i++) {
                if (i == 0) {
                    know = spec.get(0);
                } else {
                    know += "," + spec.get(i);
                }
            }
        }
        try {
            FavoriteKnowledge fav = fkservice.selectByAccount(account);
            if (fav == null) {
                FavoriteKnowledge fk = new FavoriteKnowledge();
                fk.setAccount(account);
                setValue(leibie, know, fk);
                int num = fkservice.insert(fk);
                if (num == 1) {
                    result.setData("OK");
                }
            } else {
                setValue(leibie, know, fav);
                int num = fkservice.insert(fav);
                if (num == 2) {
                    result.setData("OK");
                }
            }
            String step = params.get("step").toString();
            if (org.springframework.util.StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private void setValue(List<String> leibie, String know, FavoriteKnowledge fk) {
        fk.setCreateTime(new Date());
        fk.setCategory(know);
        if (leibie.contains("关联产品")) {
            fk.setReferProduct(1);
        } else {
            fk.setReferProduct(0);
        }
        if (leibie.contains("关联物种")) {
            fk.setReferSpecies(1);
        } else {
            fk.setReferSpecies(0);
        }
        if (leibie.contains("关联服务")) {
            fk.setReferService(1);
        } else {
            fk.setReferService(0);
        }
    }

    // 保存关注的咨询
    @RequestMapping(value = "info", method = RequestMethod.POST)
    private Object savefi(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        List<String> spec = (List<String>) params.get("ledge");
        List<String> leibie = (List<String>) params.get("leibie");
        String know = null;
        Result result = new Result();
        if (spec.isEmpty() || leibie.isEmpty()) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        } else {
            for (int i = 0; i < spec.size(); i++) {
                if (i == 0) {
                    know = spec.get(0);
                } else {
                    know += "," + spec.get(i);
                }
            }
        }
        try {
            FavoriteInformation fav = fiservice.selectByAccount(account);
            if (fav == null) {
                FavoriteInformation fk = new FavoriteInformation();
                fk.setAccount(account);
                setValue(leibie, know, fk);
                int num = fiservice.insert(fk);
                if (num == 1) {
                    result.setData("OK");
                }
            } else {
                setValue(leibie, know, fav);
                int num = fiservice.insert(fav);
                if (num == 2) {
                    result.setData("OK");
                }
            }
            String step = params.get("step").toString();
            if (org.springframework.util.StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private void setValue(List<String> leibie, String know, FavoriteInformation fk) {
        fk.setCreateTime(new Date());
        fk.setCategory(know);
        if (leibie.contains("关联产品")) {
            fk.setReferProduct(1);
        } else {
            fk.setReferProduct(0);
        }
        if (leibie.contains("关联物种")) {
            fk.setReferSpecies(1);
        } else {
            fk.setReferSpecies(0);
        }
        if (leibie.contains("关联服务")) {
            fk.setReferService(1);
        } else {
            fk.setReferService(0);
        }
    }

    // 保存关注的政策
    @RequestMapping(value = "policy", method = RequestMethod.POST)
    private Object savePolicy(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        List<String> spec = (List<String>) params.get("ledge");
        List<String> leibie = (List<String>) params.get("leibie");
        String know = null;
        Result result = new Result();
        if (spec.isEmpty() || leibie.isEmpty()) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            return result;
        } else {
            for (int i = 0; i < spec.size(); i++) {
                if (i == 0) {
                    know = spec.get(0);
                } else {
                    know += "," + spec.get(i);
                }
            }
        }
        try {
            FavoritePolicy fav = fpservice.selectByAccount(account);
            if (fav == null) {
                FavoritePolicy fk = new FavoritePolicy();
                fk.setAccount(account);
                setValue(leibie, know, fk);
                int num = fpservice.insert(fk);
                if (num == 1) {
                    result.setData("OK");
                }
            } else {
                setValue(leibie, know, fav);
                int num = fpservice.insert(fav);
                if (num == 2) {
                    result.setData("OK");
                }
            }
            String step = params.get("step").toString();
            if (org.springframework.util.StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!str.equals("prog")) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private void setValue(List<String> leibie, String know, FavoritePolicy fk) {
        fk.setCreateTime(new Date());
        fk.setCategory(know);
        if (leibie.contains("关联产品")) {
            fk.setReferProduct(1);
        } else {
            fk.setReferProduct(0);
        }
        if (leibie.contains("关联物种")) {
            fk.setReferSpecies(1);
        } else {
            fk.setReferSpecies(0);
        }
        if (leibie.contains("关联服务")) {
            fk.setReferService(1);
        } else {
            fk.setReferService(0);
        }
    }

    //查找收藏列表
    @RequestMapping(value = "/findCollect", method = RequestMethod.POST)
    public Result findCollect(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        if (loginUser == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        MyFavorite report = new MyFavorite();
        report.setAccount(loginUser.getLoginAccount());
        Result result = new Result();
        try {
            List<MyFavorite> list = reportService.findCollect(report);
            map.put("list", list);
            List<Map<String, String>> datas = new ArrayList<>();
            //组装数据
            for (MyFavorite mf : list) {
                Map<String, String> mapTemp = new HashMap<>();
                mapTemp.put("name", mf.getTitle());
                mapTemp.put("urlKey", mf.getPath());
                mapTemp.put("type", mf.getType());
                datas.add(mapTemp);
            }
            result.setData(datas);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //根据提供的path和account删除收藏列表
    @RequestMapping(value = "/delCollect", method = RequestMethod.POST)
    public Result delCollect(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        Map<String, Object> map = new HashMap<String, Object>();
        MyFavorite report = new MyFavorite();
        report.setAccount(loginUser.getLoginAccount());
        Result result = new Result();
        try {
            String path = (String) params.get("path");
            MyFavorite mf = new MyFavorite();
            mf.setPath(path);
            mf.setAccount(account);
            reportService.delFavorite(mf);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    //根据提供的path和account修改收藏列表
    @RequestMapping(value = "/updateCollect", method = RequestMethod.POST)
    public Result updateCollect(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        Result result = new Result();
        try {
            String path = (String) params.get("path");
            String type = (String) params.get("type");
            MyFavorite mf = new MyFavorite();
            mf.setPath(path);
            mf.setAccount(account);
            mf.setType(type);
            reportService.updateMyFavorite(mf);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 新版认证第三步->个性化，保存数据
     * @param params
     */
    @RequestMapping(value = "/saveIndividInfo", method = RequestMethod.POST)
    public Result saveIndividInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            logger.debug("数据格式：{}", params);
            String account = MapUtils.getString(params,"LoginAccount");

            //账户设置的数据
            Map<String,Object> bankInfo = (Map<String, Object>) params.get("BankSettingData");
            bankInfo.put("account", account);
            boolean saveBank = bankAccountService.saveBankInfo(bankInfo);
            if (!saveBank) {
                result.setCode(ResultCode.FAIL);
                result.setMsg("验证码不正确");
                return result;
            }

            List<Map<String,Object>> FollowData = (List<Map<String, Object>>) params.get("FollowData");
            if (FollowData.size() > 0) {
                //保存知识、资讯、政策关注信息
                sysFollowDictService.followInsert(FollowData, account);

                //关联物种的数据
                List<Map<String,Object>> relevaData  = (List<Map<String, Object>>) FollowData.get(0).get("releva");
                String associatedSpeciesInfo = JSON.toJSONString(relevaData.get(0));
                FavoriteSpecies associatedSpecies = service.findSpecByAccount(account);
                if (null == associatedSpecies) {
                    //无历史数据，则新增数据
                    associatedSpecies = new FavoriteSpecies();
                    associatedSpecies.setAccount(account);
                    associatedSpecies.setSpeciesName(associatedSpeciesInfo);
                    logger.info("新增关联物种信息为：{}", associatedSpecies);
                    service.insert(associatedSpecies);
                } else {
                    //有历史数据，则更新数据
                    associatedSpecies.setSpeciesName(associatedSpeciesInfo);
                    logger.info("更新关联物种信息为：{}", associatedSpecies);
                    service.updateByPrimaryKey(associatedSpecies);
                }
            }

            //设置好友分组的数据
            List<Map<String, Object>> friendGroupList = (List<Map<String, Object>>) params.get("FriendGroupData");
            if (friendGroupList.size() > 0) {
                int addNum = friendGroupService.addFriendGroup(friendGroupList, account);
            }

            //保存认证步骤到登陆表中
            LoginUser loginInfo = loginUserService.findByLoginName(account);
            int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
            if (step < 3) {
                loginUserService.updateIdentityFlag(account, String.valueOf(CertificationStepEnum.STEP_INDIVICATIONSETTINGS.getStepNum()));
            }
            logger.info("个性化信息保存成功");

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("个性化信息保存失败");
        }
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
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询关注信息失败");
        }
        logger.info("查询关注信息成功： {}", result.getData());
        return  result;
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
            //知识、资讯、政策关注列表
            Map<String,Object> followMap = new HashMap<>();
            followMap.put("follow_type", params.get("follow_type"));
            followMap.put("account", account);
            List<Map<String,Object>> followList = sysFollowDictService.findList(followMap);
            if (followList.size() == 0) {
                individInfo.put("FollowListData", "");
            } else {
                individInfo.put("FollowListData", followList);
            }
            //知识、资讯、政策已关注信息
            List<List<Map<String,Object>>> hasFollowList = sysFollowDictService.findFllowDict(followMap);

            //已关联物种的数据
            FavoriteSpecies favoriteSpecies = service.findSpecByAccount(account);
            List<Map<String,Object>> associatedSpeciesData = new ArrayList<>();
            if (null == favoriteSpecies) {
                //不作处理
            } else {
                JSONArray associatedSpecies = JSONArray.parseArray(favoriteSpecies.getSpeciesName());
                associatedSpeciesData  = jsonArrayToList(associatedSpecies);
            }
            List<List<Map<String,Object>>> relevaList = new ArrayList<>();
            relevaList.add(associatedSpeciesData);
            relevaList.add(new ArrayList<>());
            relevaList.add(new ArrayList<>());

            List<Map<String,Object>> followDataList = new ArrayList<>();
            Map<String,Object> followDataMap = new HashMap<>();
            followDataMap.put("flag", true);
            followDataMap.put("follow", hasFollowList);
            followDataMap.put("releva", relevaList);
            followDataList.add(followDataMap);

            individInfo.put("FollowData", followDataList);

            //收藏文件夹的数据
            Map<String, Object> collectMap = collectService.queryAll(account);
            if (MapUtils.isEmpty(collectMap)) {
                individInfo.put("CollectData", "");
            } else {
                individInfo.put("CollectData", collectMap.get("tree"));
            }
            //好友分组数据
            List<FriendGroup> friendGrouplist = friendGroupService.selectByAccount(account);
            if (friendGrouplist.size() == 0) {
                individInfo.put("FriendGroupData", "");
            } else {
                individInfo.put("FriendGroupData", friendGrouplist);
            }
            //账户设置数据
            BankAccount bankAccount = bankAccountService.selectByAccount(account);
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
            //获取物种分类数据
            List<Map<String,Object>> speciesclassList = service.listAllSpeciesClass();
            individInfo.put("SpeciesclassData", speciesclassList);

            result.setData(individInfo);

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询个性化信息失败");
        }
        logger.debug("查询个性化信息为： {}", result.getData());
        return result;
    }

    /**
     * 查询物种列表信息
     */
    @RequestMapping(value = "/getSpeciesList", method = RequestMethod.POST)
    public Result getSpeciesList(@RequestBody Map<String,Object> queryMap) {
        Result result = new Result();
        try {
            List<SpeciesQuery> SpeciesQueryList = new ArrayList<>();
            int pageNum = MapUtils.getInteger(queryMap, "pageNum");
            int pageSize = MapUtils.getInteger(queryMap, "pageSize");
            JSONArray classArray = JSONArray.parseArray(queryMap.get("fclassifiedid").toString());
            if (classArray.size() == 0) {
                SpeciesQuery speciesQuery = new SpeciesQuery();
                if (queryMap.get("fpinyin") != null) {
                    speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                }
                if (queryMap.get("keywords") != null) {
                    speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                }
                Object data = relatedServiceFeign.getAllSpeciesData(speciesQuery);
                JSONObject jsonObject = JSONObject.parseObject(data.toString());
                List<Map<String,Object>> speciesList = JSON.parseObject(jsonObject.get("data").toString(), List.class);
                int totalNum = speciesList.size();
                Map<String,Object> speciesMap = new HashMap<>();
                List<Map<String,Object>> speciesPageInfo = service.speciesPageInfo(speciesList, pageNum, pageSize);
                speciesMap.put("speciesListData", speciesPageInfo);
                speciesMap.put("totalNum", totalNum);
                result.setData(speciesMap);
                logger.info("筛选条件：{}，筛选后的数据物种数据为: {}", speciesQuery, speciesList);

            } else {
                Object[] classIds = classArray.toArray();
                for (Object classId : classIds) {
                    if ("0".equals(String.valueOf(classId))) {
                        //选择了动物
                        Map<String,Object> speciesclassList = service.listClassifySpeciesInfo();
                        List<Map<String,Object>> animalList = (List<Map<String, Object>>) speciesclassList.get("animalList");
                        for (Map<String,Object> animal : animalList) {
                            SpeciesQuery speciesQuery = new SpeciesQuery();
                            speciesQuery.setFclassifiedid(MapUtils.getString(animal, "classId"));
                            if (queryMap.get("fpinyin") != null) {
                                speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                            }
                            if (queryMap.get("keywords") != null) {
                                speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                            }
                            SpeciesQueryList.add(speciesQuery);
                        }
                    } else if ("1".equals(String.valueOf(classId))) {
                        //选择了植物
                        Map<String,Object> speciesclassList = service.listClassifySpeciesInfo();
                        List<Map<String,Object>> plantList = (List<Map<String, Object>>) speciesclassList.get("plantList");
                        for (Map<String,Object> plant : plantList) {
                            SpeciesQuery speciesQuery = new SpeciesQuery();
                            speciesQuery.setFclassifiedid(MapUtils.getString(plant, "classId"));
                            if (queryMap.get("fpinyin") != null) {
                                speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                            }
                            if (queryMap.get("keywords") != null) {
                                speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                            }
                            SpeciesQueryList.add(speciesQuery);
                        }
                    } else {
                        SpeciesQuery speciesQuery = new SpeciesQuery();
                        speciesQuery.setFclassifiedid(String.valueOf(classId));
                        if (queryMap.get("fpinyin") != null) {
                            speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                        }
                        if (queryMap.get("keywords") != null) {
                            speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                        }
                        SpeciesQueryList.add(speciesQuery);
                    }
                }
                //通过classId将SpeciesQueryList去重
                List<SpeciesQuery> queryList = removeDuplicate(SpeciesQueryList);
                List<Map<String,Object>> speciesAllList = new ArrayList<>();
                for (SpeciesQuery species : queryList) {
                    Object data = relatedServiceFeign.getAllSpeciesData(species);
                    JSONObject jsonObject = JSONObject.parseObject(data.toString());
                    List<Map<String,Object>> speciesList = JSON.parseObject(jsonObject.get("data").toString(), List.class);
                    speciesAllList.addAll(speciesList);
                }

                int totalNum = speciesAllList.size();
                Map<String,Object> speciesMap = new HashMap<>();
                List<Map<String,Object>> speciesPageInfo = service.speciesPageInfo(speciesAllList, pageNum, pageSize);
                speciesMap.put("speciesListData", speciesPageInfo);
                speciesMap.put("totalNum", totalNum);
                result.setData(speciesMap);
                logger.info("筛选后的数据物种数据为: {}", speciesAllList);
            }
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询物种列表失败");
        }
        return result;
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

    /**
     * 解析收藏数据
     * @param collectList
     */
    private List<Map<String,Object>> getBasicCollectInfo(List<Map<String,Object>> collectList) {
        List<Map<String,Object>> basicList = new ArrayList<>();
        Map<String,Object> basicMap = new HashMap<>();
        for (Map<String,Object> map : collectList) {
            basicMap.put("id", map.get("id"));
            basicMap.put("group_name", map.get("title"));
            basicMap.put("pid", map.get("parentId"));
            basicList.add(basicMap);
            if (map.containsKey("children")) {
                List<Map<String,Object>> childrenList = (List<Map<String, Object>>) map.get("children");
                getBasicCollectInfo(childrenList);
            }
        }
        return basicList;
    }

    /**
     * 通过classId,去重
     * @param SpeciesQueryList
     * @return
     */
     public List<SpeciesQuery> removeDuplicate(List<SpeciesQuery> SpeciesQueryList) {

            List<SpeciesQuery> result = new ArrayList<>();
            List<String> classIdList  = new ArrayList<>();
            for (SpeciesQuery species : SpeciesQueryList) {
                String classId = species.getFclassifiedid();
                if (classIdList.contains(classId)) {
                    //List包含classId,重复，不加入到result中
                    logger.info("改物种ID：{}，已存在", classId);
                } else {
                    classIdList.add(classId);
                    result.add(species);
                }
            }
            return result;
     }
}
