package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.ProxyService;
import com.ovit.nswy.member.util.DateUtil;
import com.ovit.nswy.member.util.SmsUtil;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    /**
     *
     */
    @Autowired
    private ProxyService proxyService;
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private LoginUserService loginUserService;

    @Value("${cloudmas.sms.userInfoTemplateName}")
    private String templateName;

    /**
     * 查询所有已代理/未代理的机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result query(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        logger.debug("查询已代理的机构：{}",params);
        String TABLE_NAME;
        String name;
        String logo_url;
        String infoName;
        if ("机关".equals(MapUtils.getString(params,"type"))){
            TABLE_NAME = "proxy_gov_info";
            name ="gov_name";
            infoName="gov_info";
            logo_url="logo_picture_list";
        } else {
            TABLE_NAME = "proxy_corp_info";
            name ="corp_name";
            infoName="corp_info";
            logo_url="logo_url";
        }
        params.put("logo_url",logo_url);
        params.put("info_name",infoName);
        params.put("name",name);
        params.put("TABLE_NAME",TABLE_NAME);
        //TODO 查询代理审核
        PageInfo<List<Map<String, Object>>> page = proxyService.queryList(params);
        logger.info("查询已代理的成功：{}",page.getList());
        result.setData(page);
        return result;
    }

    /**
     * 删除代理
     * @param params
     * @return
     */
    @RequestMapping(value = "/delProxy", method = RequestMethod.POST)
    public Result delProxy(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        proxyService.delProxy(params);
        return result;
    }

    /**
     * 删除认证
     * @param params
     * @return
     */
    @RequestMapping(value = "/delProxyInfo", method = RequestMethod.POST)
    public Result delProxyInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        proxyService.delProxyInfo(params);
        return result;
    }

    /**
     * 代理进度查询
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryStatus", method = RequestMethod.POST)
    public Result queryStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        PageInfo<Map<String, Object>> page = proxyService.queryStatus(params);
        result.setData(page);
        return result;
    }

    /**
     * 代理进度详情查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryStatusDetail", method = RequestMethod.POST)
    public Result queryStatusDetail(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        Map<String, Object> page = proxyService.queryStatusDetail(params);
        result.setData(page);
        return result;
    }

    /**
     * 认证审核进度
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryInfo", method = RequestMethod.POST)
    public Result queryInfo(@RequestBody Map<String, Object> params, HttpServletRequest request){
        Result result = new Result();
        PageInfo<Map<String, Object>> page = proxyService.queryInfo(params);
        result.setData(page);
        return result;
    }

    /**
     * 认证审核进度详情查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryInfoDetail", method = RequestMethod.POST)
    public Result queryInfoDetail(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        Map<String, Object> page = proxyService.queryInfoDetail(params);
        result.setData(page);
        return result;
    }

    @RequestMapping(value = "/queryAgency", method = RequestMethod.POST)
    public Result queryAgency(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        PageInfo<Map<String, Object>> page = proxyService.queryStatus(params);
        result.setData(page);
        return result;
    }

    @RequestMapping(value = "/queryStatusById", method = RequestMethod.POST)
    public Result queryStatusById(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        Map<String, Object> map = proxyService.queryStatusById(params);
        result.setData(map);
        return result;
    }

    @RequestMapping(value = "/delProxyById", method = RequestMethod.POST)
    public Result delProxyById(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        proxyService.delProxyById(params);
        return result;
    }

    /**
     * 提交代理申请
     * @param params
     * @return
     */
    @RequestMapping(value = "/addProxy", method = RequestMethod.POST)
    public Result addProxy(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.debug("代理管理：{}",params);
            proxyService.addProxy(params);
            logger.info("代理成功！");
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("代理失败：{}",e);
        }
        return result;
    }

    /**
     * 查询被代理的名称
     * 根据类型查询机构数据
     * @return
     */
    @RequestMapping(value = "/getProxyName", method = RequestMethod.POST)
    public Result getProxyName(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            String type = MapUtils.getString(params,"type");
            //数据库表名
            String TABLE_NAME;
            //表字段名
            String name;
            //根据状态分别查询机关和企业
            if ("corp".equals(type)) {
                TABLE_NAME = "proxy_corp_info";
                name = "corp_name";
            } else {
                TABLE_NAME = "proxy_gov_info";
                name = "gov_name";
            }
            params.put("TABLE_NAME",TABLE_NAME);
            params.put("name",name);
            Map<String,Object> map = proxyService.queryNameFromCorpOrGov(params);
            logger.info("被代理的：{}",map);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询被代理名称失败：{}",e);
        }
        return result;
    }

    /**
     * 代理审核后发送用户账号密码
     * 并修改明文密码为密文密码
     * @param params
     * @return
     */
    @RequestMapping(value = "/smsSendUserInfo", method = RequestMethod.POST)
    public Result smsSendUserInfo(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("代理审核通过：{}",params);
           //通过ID获取代理人和被代理机构的信息
            Map<String,Object> info = proxyService.queryInfoById(params);
            logger.info("代理的机构信息：{}",info);
            //将信息组装成短信发送的格式
            //;
            String[] phone = {MapUtils.getString(info,"mobile")};
            String password = MapUtils.getString(info,"loginPassword");
            String displayName = MapUtils.getString(info,"displayName");
            String agencyer = MapUtils.getString(info,"agencyer");
             String[] templateParams = {
                     StringUtils.isEmpty(displayName) ? agencyer : displayName,
                     MapUtils.getString(info,"name"),
                     MapUtils.getString(info,"account"),
                     password,
                     DateUtil.getTime()
            };
            //调用云信接口发送短信
            Map<String, Map<String, String>> smsResult = smsUtil.sendTemplateSms12(phone,templateParams,templateName);
            logger.info("发送短信状态：{}",smsResult);
            for (Map.Entry<String, Map<String, String>> smsResultEntry : smsResult.entrySet()) {
                Map<String, String> map = smsResultEntry.getValue();
                //如果短信发送成功则修改数据库明文密码为密文密码
                String jsonCode = MapUtils.getString(map,"ret_code");
                Map<String,Object> codeMap = JSON.parseObject(jsonCode);
                if ("SUCCESS".equalsIgnoreCase(MapUtils.getString(codeMap,"code_value"))) {
                    //获取的明文密码加密
                    String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, password).toString();
                    params.put("loginPassword",pwdMD5);
                    params.put("account",MapUtils.getString(info,"account"));
                    //将信息发送给用户之后修改数据库
                    loginUserService.updatePassword(params);
                    logger.info("修改明文密码为密文密码成功！");
                    result.setCode(ResultCode.SUCCESS);
                }
                //如果短信失败返回错误码以及错误信息
                else {
                    String errorMsg = MapUtils.getString(codeMap,"code_describle");
                    logger.error("被代理机构账号信息短信发送失败：{}",errorMsg);
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                    result.setData(errorMsg);
                }
            }
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("代理审核通过后失败：{}",e);
        }
        return result;
    }



    @RequestMapping(value = "/smsSendMasUserInfo", method = RequestMethod.POST)
    public Result smsSendMasUserInfo(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            //将信息组装成短信发送的格式
            //;
            String[] phone = {MapUtils.getString(params,"phone")};
            String password = MapUtils.getString(params,"loginPassword");
            String displayName = MapUtils.getString(params,"displayName");
            String agencyer = MapUtils.getString(params,"agencyer");
            String[] templateParams = {
                    StringUtils.isEmpty(displayName) ? agencyer : displayName,
                    MapUtils.getString(params,"name"),
                    MapUtils.getString(params,"account"),
                    password,
                    DateUtil.getTime()
            };
            //调用云信接口发送短信
            Map<String, Map<String, String>> smsResult = smsUtil.sendTemplateSms12(phone,templateParams,templateName);
            logger.info("发送短信状态：{}",smsResult);
            for (Map.Entry<String, Map<String, String>> smsResultEntry : smsResult.entrySet()) {
                Map<String, String> map = smsResultEntry.getValue();
                //如果短信发送成功则修改数据库明文密码为密文密码
                String jsonCode = MapUtils.getString(map,"ret_code");
                Map<String,Object> codeMap = JSON.parseObject(jsonCode);
                if ("SUCCESS".equalsIgnoreCase(MapUtils.getString(codeMap,"code_value"))) {
                    //获取的明文密码加密
                    String pwdMD5 = new SimpleHash("SHA-1", Constants.SESSION_SALT, password).toString();
                    params.put("loginPassword",pwdMD5);
                    params.put("account",MapUtils.getString(params,"account"));
                    //将信息发送给用户之后修改数据库
                    loginUserService.updatePassword(params);
                    logger.info("修改明文密码为密文密码成功！");
                    result.setCode(ResultCode.SUCCESS);
                }
                //如果短信失败返回错误码以及错误信息
                else {
                    String errorMsg = MapUtils.getString(codeMap,"code_describle");
                    logger.error("账号信息短信发送失败：{}",errorMsg);
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                    result.setData(errorMsg);
                }
            }
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("代理审核通过后失败：{}",e);
        }
        return result;
    }
}
