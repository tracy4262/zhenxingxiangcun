package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.model.Industrial;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.CorpInfoService;
import com.ovit.nswy.member.service.IndustrialService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.VideoImgService;
import com.ovit.nswy.member.util.JsonToStringUtils;
import com.ovit.nswy.member.util.UuidUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/corpInfo")
public class CorpInfoController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private CorpInfoService corpInfoService;
    @Autowired
    private IndustrialService industrialService;
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    private VideoImgService videoImgService;
    @RequestMapping("/find/{id}")
    public Result findById(@PathVariable int id) {
        CorpInfo cInfo = corpInfoService.selectByPrimaryKey(id);
        Result result = new Result();
        result.setData(cInfo);
        return result;
    }

    /**
     * 根据名字查找企业
     * @param page
     * @return
     */
    @RequestMapping(value = "/findCorpInfoTitle/{page}", method = RequestMethod.POST)
    public Result findByName(@PathVariable String page,@RequestBody Map<String,Object>  map) {
        Result result = new Result();
        try{
            logger.info("查询企业第{}页，参数：{}",page,map);
            String serviceName = MapUtils.getString(map,"servicename");
            String goodName = MapUtils.getString(map,"goodname");
            String industry = MapUtils.getString(map,"industry");
            String species = MapUtils.getString(map,"species");
            map.put("district",MapUtils.getString(map,"district").replaceAll("/",""));
            if (StringUtils.isNotEmpty(serviceName)) {
                String[] serviceNameArray = serviceName.split(" ");
                map.put("serviceNameArray",serviceNameArray);
            }
            if (StringUtils.isNotEmpty(goodName)) {
                String[] goodNameArray = goodName.split(" ");
                map.put("goodNameArray",goodNameArray);
            }
            if (StringUtils.isNotEmpty(industry)) {
                String[] industryArray = industry.split(" ");
                map.put("industryArray",industryArray);
            }
            if (StringUtils.isNotEmpty(species)) {
                String[] speciesArray = species.split(" ");
                map.put("speciesArray",speciesArray);
            }
            map.put("pageNum",page);
            map.put("pageSize",12);
            PageInfo<Map<String, Object>> list = corpInfoService.selectByName(map);
            result.setData(list);
            logger.info("企业查询成功 {} ",list.getList());
        }catch (Exception e){
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            logger.error("企业查询失败 {} 原因" ,e);
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody CorpInfo corpInfo) {
        Result result = new Result();
        try {
            if (null != corpInfo && corpInfo.getId() != null && StringUtils.isNotEmpty(corpInfo.getLoginAccount())) {
                corpInfoService.updateByPrimaryKeySelective(corpInfo);
                result.setCode(ResultCode.SUCCESS);
                result.setData("ok");
                result.setMsg("企业信息更新成功");
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("更新失败");
            }
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setData(e.getMessage());
            result.setMsg("更新失败");
            return result;
        }
        return result;
    }


    @RequestMapping(value = "/findByAccount/{account}", method = RequestMethod.GET)
    public Result findByAccount(@PathVariable("account")  String account) {
        Result result = new Result();
        try {
            CorpInfo corpInfo = new CorpInfo();
            corpInfo.setLoginAccount(account);
            corpInfo = corpInfoService.findByAccount(corpInfo);
            result.setCode(ResultCode.SUCCESS);
            result.setData(corpInfo);
            result.setMsg("企业信息查询成功");
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setData(e.getMessage());
            result.setMsg("查询失败");
            return result;
        }
        return result;
    }

    @RequestMapping("/")
    public Result findById() {
        PageInfo<Map<String, Object>> list = corpInfoService.query(0, 5);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    @RequestMapping("/findPage/{page}")
    public Result find(@PathVariable String page, HttpServletRequest request) {
        Result result = null;
        try {
            int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
            int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
            PageInfo<Map<String, Object>> cInfos = corpInfoService.query(currentPage, pageSize);
            result = new Result();
            result.setData(cInfos);
        } catch (NumberFormatException e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("error",e);
        }
        return result;
    }

    /**
     * 企业认证
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCorp", method = RequestMethod.POST)
    public Result saveCorp(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.debug("企业认证：{}",params);
            String proxyName = StringUtils.join("c",RandomStringUtils.randomAlphanumeric(7).toLowerCase());
            String jsonString = JSON.toJSONString(params);
            CorpInfo corpInfo = JSON.parseObject(jsonString,CorpInfo.class);
            corpInfo.setLoginAccount(proxyName);
            //身份证照片正反面
            corpInfo.setIdentificationCardUrl(JsonToStringUtils.initJson(params,"identification_card_url"));
            //营业期限
            corpInfo.setBusnissTerm(JsonToStringUtils.initJson(params,"busniss_term"));
            //传企业LOGO
            corpInfo.setLogoUrl(JsonToStringUtils.initJson(params,"logo_url"));
            //企业工商营业执照
            corpInfo.setBusinessLicenseUrl(JsonToStringUtils.initJson(params,"business_license_url"));
            corpInfoService.insert(corpInfo);
            logger.info("企业认证成功！");
            //认证企业成功后生成一条登录账号数据存，密码以明文形式，审核通过下发账号密码后修改为密文
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginAccount(proxyName);
            loginUser.setUniqueId(UuidUtil.get32UUID());
            loginUser.setRegisterTime(new Date());
            loginUser.setUserType(1);
            loginUser.setTourNumber(0);
            loginUser.setDisplayName(MapUtils.getString(params,"corp_name"));
            loginUser.setAvatar(corpInfo.getLogoUrl());
            loginUser.setLoginPasswd(RandomStringUtils.randomAlphanumeric(6).toLowerCase());
            loginUserService.insertProxyUser(loginUser);
            logger.info("企业账号入登录表成功");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
            logger.error("企业认证失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/getIndustryType", method = RequestMethod.GET)
    public Result getIndustryType() {
        Result result = new Result();
        List<Industrial> list = industrialService.getIndustryType();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/getApproveStatus", method = RequestMethod.GET)
    public Result getApproveStatus(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String loginAccount = loginUser.getLoginAccount();
        Result result = new Result();
        Integer status = corpInfoService.getApproveStatus(loginAccount);
        Map<String, Integer> map = new HashMap<>();
        Integer approveStatus = map.put("approveStatus", status);
        result.setData(map);
        return result;
    }

    @RequestMapping(value = "/query-corp-by-location", method = RequestMethod.POST)
    public Result queryCorpByLocation(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        String location = params.get("location").toString();
        List<Map<String, Object>> list = corpInfoService.queryCorpByLocation(location);
        result.setData(list);
        return result;
    }

    /**
     * 查询所有未被代理成功的企业
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-corp-not-proxy", method = RequestMethod.POST)
    public Result queryCorpNotProxy(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询所有未被代理成功的企业：{}",params);
            PageInfo<List<Map<String,Object>>> pageInfo = corpInfoService.queryCoryNotProxy(params);
            result.setData(pageInfo);
            logger.debug("查询所有未被代理成功的企业成功：{}",pageInfo.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.debug("查询所有未被代理成功的企业失败：{}",e);
        }
        return result;
    }
}
