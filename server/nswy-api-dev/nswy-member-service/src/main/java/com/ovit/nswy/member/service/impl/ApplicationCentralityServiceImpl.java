package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ApplicationCentralityMapper;
import com.ovit.nswy.member.service.ApplicationCentralityService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApplicationCentralityServiceImpl implements ApplicationCentralityService {

    @Autowired
    private ApplicationCentralityMapper applicationCentralityMapper;

    @Override
    public List<Map<String,Object>> findList(Map<String,Object> params){

        //全部应用
        List<Map<String,Object>> allAppInfo = applicationCentralityMapper.findList(params);

        //用户应用
        List<Map<String,Object>> userAppInfo = applicationCentralityMapper.findAppSettingsByAccount(params);


        if (userAppInfo.isEmpty()) {
            for (Map<String,Object> app : allAppInfo) {
                app.put("checked", false);
            }
        } else {
            for (Map<String,Object> app : allAppInfo) {
                app.put("checked", false);
                String appId = MapUtils.getString(app, "appSettingId");
                for (Map<String,Object> userApp : userAppInfo) {
                    String userAppId = MapUtils.getString(userApp, "appId");
                    if (userAppId.equals(appId)) {
                        app.put("checked", true);
                        app.put("appName", MapUtils.getString(userApp, "appName"));
                        break;
                    }
                }
            }
        }

        return allAppInfo;
    }

    @Override
    public List<Map<String,Object>> findUserTypeList(Map<String,Object> params){

        return applicationCentralityMapper.findUserTypeList(params);
    }

    @Override
    public List<Map<String,Object>> findServiceTypeList(Map<String,Object> params){

        return applicationCentralityMapper.findServiceTypeList(params);
    }

    @Override
    public List<Map<String,Object>> findAppAbstract(Map<String,Object> params){

        return applicationCentralityMapper.findAppAbstract(params);
    }

    /**
     * 保存应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void saveAppSettings(Map<String, Object> appSettingsMap) {

        applicationCentralityMapper.saveAppSettings(appSettingsMap);
    }

    /**
     * 更新应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void cancelAppSettings(Map<String, Object> appSettingsMap) {

        applicationCentralityMapper.cancelAppSettings(appSettingsMap);
    }


    /**
     * 取消应用，使用人数减1
     * @param appId
     */
    @Override
    public void cancelNumber(String appId) {
        applicationCentralityMapper.cancelNumber(appId);
    }

    /**
     * 添加应用，使用人数加1
     * @param appId
     */
    @Override
    public void addNumber(String appId) {
        applicationCentralityMapper.addNumber(appId);
    }

}
