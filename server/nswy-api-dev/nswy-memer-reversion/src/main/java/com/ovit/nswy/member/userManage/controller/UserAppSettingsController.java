package com.ovit.nswy.member.userManage.controller;

import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.userManage.service.UserAppSettingsService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user/appSettings")
public class UserAppSettingsController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserAppSettingsService userAppSettingsService;

    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 保存或取消应用
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrCancelAppInfo", method = RequestMethod.POST)
    public Result saveOrCancelAppInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            userAppSettingsService.saveBaseAppSettinds(params);
        } catch (Exception e) {
            logger.error("应用设置信息保存或取消失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存或取消应用设置信息失败");
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
            //用户选择的应用
            List<Map<String,Object>> appSettingsInfo = userAppSettingsService.findAppSettingsByAccount(params);
            if(appSettingsInfo.isEmpty()) {
                //获取管理员创建的模版
                String templateId = MapUtils.getString(params,"templateId");
                Map<String, Object> historyInfo = new HashMap<>();
                historyInfo.put("id", templateId);
                List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
                if(list.size()>0){
                    historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
                } else{
                    historyInfo.put("account",MapUtils.getString(params,"account"));
                }

                historyInfo.put("templateId", templateId);
                appSettingsInfo = userAppSettingsService.findAppSettingsByAccount(historyInfo);
            }
            //结合全量的应用数据与用户数据
            List<Map<String,Object>> userAppInfo =  userAppSettingsService.findAllAppInfo(appSettingsInfo);
            result.setData(userAppInfo);

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询应用设置信息失败");
        }
        logger.info("查询应用设置信息为： {}", result.getData());
        return result;
    }

    /**
     * 查询应用标题
     */
    @RequestMapping(value = "/findAppTitle", method = RequestMethod.POST)
    public Result findAppTitle(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //应用的标题
            List<Map<String,Object>> appTitle = userAppSettingsService.findAppTitle(params);
            if(CollectionUtils.isEmpty(appTitle)){
                //获取管理员创建的模版
                String templateId = MapUtils.getString(params,"templateId");
                Map<String, Object> historyInfo = new HashMap<>();
                historyInfo.put("id", templateId);
                List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
                if(list.size()>0){
                    historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
                } else{
                    historyInfo.put("account",MapUtils.getString(params,"account"));
                }
                historyInfo.put("templateId", templateId);
                appTitle = userAppSettingsService.findAppTitle(historyInfo);
            }

            Map<String,Object> appMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(appTitle)) {
                for (Map<String,Object> map : appTitle) {
                    int level = MapUtils.getInteger(map, "level");
                    if (level == 0) {
                        //基础应用名称
                        appMap.put("baseName", MapUtils.getString(map, "title"));
                    } else if (level == 1) {
                        //通用应用名称
                        appMap.put("commonName", MapUtils.getString(map, "title"));
                    } else if (level == 2) {
                        //高级应用名称
                        appMap.put("highName", MapUtils.getString(map, "title"));
                    }
                }
            }
            if (!appMap.containsKey("baseName")) {
                appMap.put("baseName", "基础应用");
            }
            if (!appMap.containsKey("commonName")) {
                appMap.put("commonName", "通用应用");
            }
            if (!appMap.containsKey("highName")) {
                appMap.put("highName", "高级应用");
            }

            result.setData(appMap);
            result.setMsg("应用标题查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询应用标题信息失败");
        }
        logger.info("查询应用标题信息为： {}", result.getData());
        return result;
    }


}
