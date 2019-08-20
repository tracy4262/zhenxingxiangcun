package com.ovit.nswy.member.certification.controller;

import com.ovit.nswy.member.certification.service.AppSettingsService;
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
@RequestMapping("/appSettings")
public class AppSettingsController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AppSettingsService appSettingsService;

    /**
     * 保存或取消应用
     * @param params
     * @return
     */
    /*@RequestMapping(value = "/saveOrCancelAppInfo", method = RequestMethod.POST)
    public Result saveOrCancelAppInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            //0：取消，1：保存
            String type = MapUtils.getString(params,"type");
            int userType = MapUtils.getInteger(params,"userType");
            String appId = MapUtils.getString(params,"appId");
            String templateId = MapUtils.getString(params,"templateId");
            //String account = MapUtils.getString(params,"account");
            //String appId = MapUtils.getString(params,"appId");
            if ("0".equals(type)) {
                //取消应用,应用的使用人数通过触发器更新,触发器名称为APP_CANCEL_AFTER_DELETE
                appSettingsService.cancelAppSettings(params);
                if (userType != 10) {
                    //用户类型为管理员，不需要减1，其余需要
                    appSettingsService.cancelNumber(appId);
                }
                logger.info("应用设置信息取消成功");

            } else if("1".equals(type)) {
                //保存应用,应用的使用人数通过触发器更新,触发器名称为APP_ADD_AFTER_INSERT
                appSettingsService.saveAppSettings(params);
                if (userType != 10) {
                    //用户类型为管理员，不需要加1，其余需要
                    appSettingsService.addNumber(appId);
                }
                logger.info("应用设置信息保存成功");
            }

        } catch (Exception e) {
            logger.error("应用设置信息保存或取消失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存或取消应用设置信息失败");
        }
        return result;
    }*/

    /**
     * 查询应用设置信息
     */
    @RequestMapping(value = "/findAppSettingsInfo", method = RequestMethod.POST)
    public Result findAppSettingsInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //String account = MapUtils.getString(params,"account");
            //用户选择的应用
            List<Map<String,Object>> appSettingsInfo = appSettingsService.findAppSettingsByAccount(params);
            //结合全量的应用数据与用户数据
            List<Map<String,Object>> userAppInfo =  appSettingsService.findAllAppInfo(appSettingsInfo);
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
     * 保存或取消应用
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrCancelAppInfo", method = RequestMethod.POST)
    public Result saveOrCancelAppInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            appSettingsService.saveOrCancel(params);
        } catch (Exception e) {
            logger.error("应用设置信息保存或取消失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存或取消应用设置信息失败");
        }
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
            List<Map<String,Object>> appTitle = appSettingsService.findAppTitle(params);
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

    /**
     * 编辑应用标题
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAppTitle", method = RequestMethod.POST)
    public Result updateAppTitle(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            appSettingsService.updateAppTitle(params);
            result.setMsg("标题编辑成功");
        } catch (Exception e) {
            logger.error("编辑应用标题失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("编辑应用标题信息失败");
        }
        return result;
    }

}
