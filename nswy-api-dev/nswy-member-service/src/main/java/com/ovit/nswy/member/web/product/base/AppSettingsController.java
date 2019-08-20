package com.ovit.nswy.member.web.product.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.member.common.enums.CertificationStepEnum;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.MyApp;
import com.ovit.nswy.member.service.AppSettingsService;
import com.ovit.nswy.member.service.CorpCertificationService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.MyAppService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/appSettings")
public class AppSettingsController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AppSettingsService appSettingsService;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private MyAppService myAppService;


    /**
     * 保存应用设置信息，存在则更新
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateAppSettingsInfo", method = RequestMethod.POST)
    public Result saveOrUpdateAppSettingsInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String appSettingsInfo = JSON.toJSONString(params.get("data"));
            logger.info("应用设置信息转为json：{}", appSettingsInfo);
            String account = MapUtils.getString(params,"account");

            Map<String,Object> appSettingsMap = new HashMap<>();
            appSettingsMap.put("account",account);
            appSettingsMap.put("app_info",appSettingsInfo);

            Map<String,Object> historyMap = appSettingsService.findAppSettingsByAccount(account);
            if (MapUtils.isEmpty(historyMap)) {
                //无历史数据，则新增数据
                appSettingsService.saveAppSettings(appSettingsMap);

            } else {
                //有历史数据，则更新数据
                appSettingsService.updateAppSettings(appSettingsMap);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("level", 0);
            List<MyApp> myApps = new ArrayList<>();
            MyApp app2 = new MyApp();
            app2.setAppid("2");
            app2.setCreateTime(new Date());
            app2.setLevel(0);
            app2.setAccount(account);
            myApps.add(app2);
            myAppService.insert(myApps);
            //保存认证步骤到登陆表中
            LoginUser loginInfo = loginUserService.findByLoginName(account);
            int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
            if (step < 4) {
                loginUserService.updateIdentityFlag(account, String.valueOf(CertificationStepEnum.STEP_APPSETTINGS.getStepNum()));
            }
            logger.info("应用设置信息保存成功");

        } catch (Exception e) {
            logger.error("应用设置信息保存失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存应用设置信息失败");
        }
        return result;
    }

    /**
     * 查询应用设置信息
     */
    @RequestMapping(value = "/findAppSettingsInfo", method = RequestMethod.POST)
    public Result findAppSettingsInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params,"account");
            Map<String,Object> appSettingsInfo = appSettingsService.findAppSettingsByAccount(account);
            if (MapUtils.isNotEmpty(appSettingsInfo)) {
                Map<String,Object> jsonMap = JSON.parseObject(appSettingsInfo.get("app_info").toString());
                result.setData(jsonMap);
            } else {
                //无数据，初始化应用设置，显示所有应用
                Map<String,List<Map<String,Object>>> initializeAppInfo =  appSettingsService.findAllAppInfo();
                result.setData(initializeAppInfo);
            }

        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询应用设置信息失败");
        }
        logger.info("查询应用设置信息为： {}", result.getData());
        return result;
    }
}
