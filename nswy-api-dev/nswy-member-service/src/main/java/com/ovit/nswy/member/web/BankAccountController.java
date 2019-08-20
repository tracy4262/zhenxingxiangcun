package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.member.model.AllAppInfo;
import com.ovit.nswy.member.model.BankAccount;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.MyApp;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 认证导航第3步个性化设置的银行账号信息
 */
@RestController
@RequestMapping("/bank")
public class BankAccountController {
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private MyAppService myAppService;
    @Autowired
    private AllAppInfoService allAppInfoService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private AppSettingsService appSettingsService;

    @RequestMapping(value = "/insertInfo", method = RequestMethod.POST)
    public Result insertInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        int num = 0;
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            String name = (String) params.get("name");
            String idcard = (String) params.get("idcard");
            BankAccount bank1 = bankAccountService.selectByAccount(account);
            logger.info("根据用户名 {} 查找银行账户信息 {}", account, bank1);
            if (bank1 != null) {
                bank1.setCreateTime(new Date());
                bank1.setIdcard(idcard);
                bank1.setRealname(name);
                num = bankAccountService.insert(bank1);
            } else {
                BankAccount bank2 = new BankAccount();
                bank2.setCreateTime(new Date());
                bank2.setIdcard(idcard);
                bank2.setRealname(name);
                bank2.setAccount(account);
                num = bankAccountService.insert(bank2);
            }
            String step = params.get("step").toString();
            if (org.springframework.util.StringUtils.hasText(step)) {
                String str = step.substring(12, 16);
                if (!"prog".equals(str)) {
                    loginUserService.updateIdentityFlag(account, step);
                }
            }
            result.setData(num);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        logger.info("返回最终数据{}", result);
        return result;
    }

    /**
     * 用户设置查询
     */
    @RequestMapping(value = "/find")
    public Result find(HttpServletRequest request) {
        Result result = new Result();
        HttpSession session = request.getSession();
        try {
            LoginUser loginUser = (LoginUser) session.getAttribute("currentUser");
            String username = loginUser.getLoginAccount();
            BankAccount list = bankAccountService.selectByAccount(username);
            result.setData(list);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insertPwd(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        int num = 0;
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            String bank = (String) params.get("bank");
            String phone = (String) params.get("phone");
            String code = (String) params.get("code");
            //判断验证码是否一样
            String randCode = redisTemplate.opsForValue().get(phone);
            if (StringUtils.equals(randCode, code)) {
                BankAccount bank1 = bankAccountService.selectByAccount(account);
                if (bank1 != null) {
                    bank1.setBankaccount(bank);
                    bank1.setCreateTime(new Date());
                    bank1.setPhone(phone);
                    num = bankAccountService.insert(bank1);
                } else {
                    BankAccount bank2 = new BankAccount();
                    bank2.setBankaccount(bank);
                    bank2.setCreateTime(new Date());
                    bank2.setPhone(phone);
                    bank2.setAccount(account);
                    num = bankAccountService.insert(bank2);
                }
                result.setData(num);
                String step = params.get("step").toString();
                if (org.springframework.util.StringUtils.hasText(step)) {
                    String str = step.substring(12, 16);
                    if (!str.equals("prog")) {
                        loginUserService.updateIdentityFlag(account, step);
                    }
                }
            } else {
                result.setData(9);
            }
            redisTemplate.delete(phone);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 支付密码设置
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Result password(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        String pwd = (String) params.get("pwd");
        String secpwd = (String) params.get("secpwd");
        BankAccount bank1 = new BankAccount();
        BankAccount bank2 = new BankAccount();
        int num = 0;
        Result result = new Result();
        if (null != pwd & null != secpwd) {
            if (!pwd.equals(secpwd)) {
                num = 1;
            } else {
                try {
                    LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
                    String account = loginUser.getLoginAccount();
                    if (null != account) {
                        bank1 = bankAccountService.selectByAccount(account);
                        if (bank1 != null) {
                            bank1.setCreateTime(new Date());
                            bank1.setPassword(pwd);
                            num = bankAccountService.insert(bank1);
                        } else {
                            bank2.setCreateTime(new Date());
                            bank2.setPassword(pwd);
                            bank2.setAccount(account);
                            num = bankAccountService.insert(bank2);
                        }
                        String step = params.get("step").toString();
                        if (org.springframework.util.StringUtils.hasText(step)) {
                            String str = step.substring(12, 16);
                            if (!str.equals("prog")) {
                                loginUserService.updateIdentityFlag(account, step);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error(e);
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                    result.setMsg(e.getMessage());
                }
            }
        }
        result.setData(num);
        return result;
    }

    /**
     * 应用设置
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/setapp", method = RequestMethod.POST)
    public Result setApp(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = null;
        try {
            int level = MapUtils.getInteger(params,"level");
            int num = 0;
            result = new Result();
			/*if (!appname.isEmpty()) {
				List<MyApp> list = myAppService.selectByLevel(level);
				if (list.size() != 0) {
					for (MyApp m : list) {
						names.add(m.getAppid());
					}
					results = listToStr(appname, names);
				} else {
					results = appname;
				}
			}*/
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            //List<String> appname = (List<String>) params.get("appname");
            List<Integer> appname = (List<Integer>) params.get("appname");
            Map<String, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("level", level);
            myAppService.deleteAll(map);
            List<MyApp> myApps = new ArrayList<>();
            if (appname.size() > 0) {
                for (Integer s : appname) {
                    MyApp app2 = new MyApp();
                    app2.setAppid(String.valueOf(s));
                    app2.setCreateTime(new Date());
                    app2.setLevel(level);
                    app2.setAccount(account);
                    myApps.add(app2);
                }
                num = myAppService.insert(myApps);
            }
            Map<String,Object> app = new HashMap<>();
            app.put("account",account);
            app.put("level",level);
            Map<String,Object> appMap = myAppService.queryAppUseStatusCountByAccount(app);
            if (MapUtils.isNotEmpty(appMap) && "0".equals(MapUtils.getString(appMap,"appStatus"))) {
                myAppService.updateByAccount(app);
            } else if (MapUtils.isEmpty(appMap)){
                myAppService.saveAppStatus(app);
            }
            result.setData(num);
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

   /**
     * 初始化获得应用数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/findapp")
    public Result findApp(HttpServletRequest request) {
        Result result = new Result();
        try {
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
            String account = loginUser.getLoginAccount();
            List<MyApp> list = myAppService.selectByAccount(account);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 获得所有的应用数据
     * @return
     */
    @RequestMapping(value = "/findAllappInfo", method = RequestMethod.POST)
    public Result findAllappInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            int level = (Integer) params.get("level");
            List<AllAppInfo> allAppInfoList = allAppInfoService.selectAllAppInfo(level);
            result.setData(allAppInfoList);
        } catch (Exception e) {
            logger.error("exception info: {}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 获得用户拥有的应用数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPersonApp", method = RequestMethod.POST)
    public Result findPersonApp(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            int level = MapUtils.getInteger(params, "level");
            String account = MapUtils.getString(params, "account");
            List<Map<String, Object>> personAppInfoList = new ArrayList<>();
            Map<String, Object> appSettingInfo = appSettingsService.findAppSettingsByAccount(account);
            List<AllAppInfo> allAppInfoList = allAppInfoService.selectAllAppInfo(level);
            if (MapUtils.isNotEmpty(appSettingInfo)) {
                JSONObject appJson = JSON.parseObject(MapUtils.getString(appSettingInfo, "app_info"));
                if (level == 0) {
                    //基础应用
                    personAppInfoList = (List<Map<String, Object>>) appJson.get("basicAppData");

                } else if (level == 1) {
                    //高级应用
                    personAppInfoList = (List<Map<String, Object>>) appJson.get("advancedAppData");

                } else if (level == 3) {
                    //第三方应用
                    personAppInfoList = (List<Map<String, Object>>) appJson.get("thirdAppData");

                }
                for (Map<String, Object> app : personAppInfoList) {
                    String appName = MapUtils.getString(app, "name");
                    for (AllAppInfo all : allAppInfoList) {
                        if (appName.equals(all.getAppName())) {
                            app.put("url", all.getUrl());
                            break;
                        }
                    }
                }
                result.setMsg("查询应用信息成功");
            } else {
                result.setMsg("暂无数据");
            }
            result.setData(personAppInfoList);
        } catch (Exception e) {
            logger.error("exception info: {}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
