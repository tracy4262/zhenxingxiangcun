package com.ovit.nswy.member.userManage.service.impl;

import com.ovit.nswy.member.certification.mapper.AppSettingsMapper;
import com.ovit.nswy.member.certification.service.AppSettingsService;
import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.userManage.service.UserAppSettingsService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAppSettingsServiceImpl implements UserAppSettingsService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AppSettingsMapper appSettingsMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * 通过用户名查询用户的应用设置信息
     * @return
     */
    @Override
    public List<Map<String, Object>> findAppSettingsByAccount(Map<String, Object> appInfo) {
        List<Map<String,Object>> appSettingsInfo = appSettingsMapper.findAppSettingsByAccount(appInfo);
        logger.info("根据用户名{}，查询的应用设置信息内容为：{}", appInfo, appSettingsInfo);
        return appSettingsInfo;
    }

    /**
     * 查询用户的应用设置信息
     * @return
     */
    @Override
    public List<Map<String,Object>> findAllAppInfo(List<Map<String,Object>> appInfo) {

        //appInfo 用户选择完善信息基础应用，adminInfo 管理员
        List<Map<String,Object>> allAppInfo = appSettingsMapper.findAllAppInfo();

        if (appInfo.isEmpty()) {
            for (Map<String,Object> app : allAppInfo) {
                app.put("checked", false);
            }
        } else {
            for (Map<String,Object> app : allAppInfo) {
                app.put("checked", false);
                String appId = MapUtils.getString(app, "id");
                for (Map<String,Object> userApp : appInfo) {
                    String userAppId = MapUtils.getString(userApp, "appId");
                    if (userAppId.equals(appId)) {
                        app.put("checked", true);
                        app.put("appName", MapUtils.getString(userApp, "appName"));
                        break;
                    }
                }
            }
        }



        logger.info("用户应用设置数据为：{}", allAppInfo);

        return allAppInfo;
    }


    /**
     * 保存基础应用
     * @param params
     */
    @Override
    public void saveBaseAppSettinds(Map<String,Object> params){

        //0：取消，1：保存
        String account = MapUtils.getString(params,"account");
        String templateId = MapUtils.getString(params,"templateId");

        Map<String, Object> historyInfo = new HashMap<>();
        historyInfo.put("account",account);
        historyInfo.put("templateId", templateId);

        List<Map<String,Object>> baseList = (List<Map<String, Object>>) params.get("baseApp");


        List<Map<String,Object>> commonList = (List<Map<String, Object>>) params.get("commonApp");



        List<Map<String,Object>> highList = (List<Map<String, Object>>) params.get("highApp");

        List<Map<String,Object>> serviceList = (List<Map<String, Object>>) params.get("serviceApp");

        //先查询有无数据，有数据， 先删除，在保存
        List<Map<String,Object>> appSettingsInfo = appSettingsMapper.findAppSettingsByAccount(historyInfo);

        /**
         * 先删除已有数据，在reversion_full_app_setting表中-1，在重新保存数据 ， +1
         */
        if(!appSettingsInfo.isEmpty()){
            for(Map<String,Object> appInfos : appSettingsInfo){

                appInfos.put("account",account);

                appInfos.put("templateId",templateId);

                appSettingsMapper.cancelNumber(MapUtils.getString(appInfos,"appId"));

                appSettingsMapper.deleteAppSettings(appInfos);
            }
        }


        saveOrUpdateAppSetting(baseList,historyInfo);
        saveOrUpdateAppSetting(commonList,historyInfo);
        saveOrUpdateAppSetting(highList,historyInfo);
        saveOrUpdateAppSetting(serviceList,historyInfo);

        Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

        //保存用户步骤
        loginUserService.saveOrUpdateStep(loginMap);



    }


    private void saveOrUpdateAppSetting( List<Map<String,Object>> params,Map<String,Object> appInfo){

        if(!params.isEmpty()){
            for(Map<String,Object> map : params){

                if(MapUtils.getBoolean(map,"isAdd")){
                    map.put("account",MapUtils.getString(appInfo,"account"));

                    map.put("templateId",MapUtils.getString(appInfo,"templateId"));

                    appSettingsMapper.addNumber(MapUtils.getString(map,"appId"));

                    appSettingsMapper.saveAppSettings(map);
                }

            }

        }


    }



    /**
     * 保存应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void saveAppSettings(Map<String, Object> appSettingsMap) {
        logger.info("需保存的应用设置信息内容为：{}", appSettingsMap);
        appSettingsMapper.saveAppSettings(appSettingsMap);
    }

    /**
     * 更新应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void cancelAppSettings(Map<String, Object> appSettingsMap) {
        logger.info("需取消的应用设置信息内容为：{}", appSettingsMap);
        appSettingsMapper.cancelAppSettings(appSettingsMap);
    }

    @Override
    public Map<String, Object> findAppSetting(Map<String, Object> appSettingsMap) {
        Map<String,Object>  appSetting =  appSettingsMapper.findAppSetting(appSettingsMap);
        if(MapUtils.isEmpty(appSetting)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(appSettingsMap,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
            } else{
                historyInfo.put("account",MapUtils.getString(appSettingsMap,"account"));
            }
            historyInfo.put("templateId", MapUtils.getString(appSettingsMap,"templateId"));
            historyInfo.put("appId", MapUtils.getString(appSettingsMap,"appId"));
            appSetting = appSettingsMapper.findAppSetting(historyInfo);
        }


        return appSetting;
    }

    /**
     * 取消应用，使用人数减1
     * @param appId
     */
    @Override
    public void cancelNumber(String appId) {
        appSettingsMapper.cancelNumber(appId);
    }

    /**
     * 添加应用，使用人数加1
     * @param appId
     */
    @Override
    public void addNumber(String appId) {
        appSettingsMapper.addNumber(appId);
    }

    /**
     * 查询应用标题
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findAppTitle(Map<String, Object> params) {
        return appSettingsMapper.findAppTitle(params);
    }

}
