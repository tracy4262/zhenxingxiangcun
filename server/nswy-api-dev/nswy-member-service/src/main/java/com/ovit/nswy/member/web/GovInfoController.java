package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.GovInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.GovInfoService;
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
@RequestMapping("/govInfo")
public class GovInfoController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private GovInfoService govInfoService;
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    private VideoImgService videoImgService;
    @RequestMapping("/")
    public Result show() {
        PageInfo<Map<String, Object>> list = govInfoService.query(0, 5);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 机关展示
     *
     * @return
     */
    @RequestMapping("/find/{page}")
    public Result showExp(@PathVariable String page, HttpServletRequest request) {
        int currentPage = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(page);
        int pageSize = StringUtils.isEmpty(page) ? 0 : Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<Map<String, Object>> list = govInfoService.query(currentPage, pageSize);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    /**
     * 根据条件查找机关
     * @param page
     * @return
     */
    @RequestMapping(value = "/findByName/{page}", method = RequestMethod.POST)
    public Result findByName(@PathVariable String page,@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try{
            logger.info("查询机关，参数：{}",params);
            params.put("pageNum",page);
            params.put("pageSize",12);
            PageInfo<Map<String, Object>> pageInfo = govInfoService.selectByName(params);
            logger.info("机关查询成功：{}",pageInfo.getList());
            result.setData(pageInfo);
        }catch (Exception e){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("机关查询 失败：{}",e);
            return result;
        }
        return result;
    }

    /**
     * 机关认证/乡村认证
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveGov", method = RequestMethod.POST)
    public Result saveGov(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            logger.debug("机关认证：{}",params);
            String proxyName = StringUtils.join("g",RandomStringUtils.randomAlphanumeric(7).toLowerCase());
            params.put("login_account",proxyName);
            params.put("logo_picture_list", JsonToStringUtils.initJson(params,"logo_picture_list"));
            String qualification_certificate_picture_list = MapUtils.getString(params,"qualification_certificate_picture_list");
            if(StringUtils.isNotBlank(qualification_certificate_picture_list)){
                params.put("qualification_certificate_picture_list",JsonToStringUtils.initJson(params,"qualification_certificate_picture_list"));
            }else{
                params.put("qualification_certificate_picture_list","");
            }
            params.put("unit_person_picture_list",JsonToStringUtils.initJson(params,"unit_person_picture_list"));
            govInfoService.insert(params);

            logger.info("机关认证成功！");
            //认证机关成功后生成一条登录账号数据存，密码以明文形式，审核通过下发账号密码后修改为密文
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginAccount(proxyName);
            loginUser.setUniqueId(UuidUtil.get32UUID());
            loginUser.setRegisterTime(new Date());
            loginUser.setTourNumber(0);
            loginUser.setAvatar(MapUtils.getString(params,"logo_picture_list"));
            loginUser.setDisplayName(MapUtils.getString(params,"gov_name"));
            loginUser.setUserType(Integer.valueOf(MapUtils.getString(params,"country_type")));
            loginUser.setLoginPasswd(RandomStringUtils.randomAlphanumeric(6).toLowerCase());
            loginUserService.insertProxyUser(loginUser);
            logger.info("企业账号入登录表成功");
            result.setData(1);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("机关认证失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/getApproveStatus", method = RequestMethod.GET)
    public Result getApproveStatus(HttpServletRequest request) {
        Result result = null;
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String loginAccount = loginUser.getLoginAccount();
            result = new Result();
            Integer status = govInfoService.getApproveStatus(loginAccount);
            Map<String, Integer> map = new HashMap<>();
            map.put("approveStatus", status);
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询所有未被代理成功的机关
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-gov-not-proxy", method = RequestMethod.POST)
    public Result queryNotProxy(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询所有未被代理成功的机关：{}",params);
            PageInfo<List<Map<String,Object>>> pageInfo = govInfoService.queryGovNotProxy(params);
            result.setData(pageInfo);
            logger.debug("查询所有未被代理成功的机关成功：{}",pageInfo.getList());
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.debug("查询所有未被代理成功的失败：{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/findByAccount/{account}", method = RequestMethod.GET)
    public Result findByAccount(@PathVariable("account")  String account) {
        Result result = new Result();
        try {
            GovInfo govInfo = new GovInfo();
            govInfo = govInfoService.findByAccount(account);
            result.setCode(ResultCode.SUCCESS);
            result.setData(govInfo);
            result.setMsg("机关信息查询成功");
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setData(e.getMessage());
            result.setMsg("查询失败");
            return result;
        }
        return result;
    }
}