package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface ApplicationCentralityService {

     List<Map<String,Object>> findList(Map<String,Object> params);

     List<Map<String,Object>> findUserTypeList(Map<String,Object> params);

    List<Map<String,Object>> findServiceTypeList(Map<String,Object> params);

    List<Map<String,Object>> findAppAbstract(Map<String,Object> params);


    /**
     * 保存应用设置数据
     * @param appSettingsMap
     */
    void saveAppSettings(Map<String, Object> appSettingsMap);

    /**
     * 更新应用设置数据
     * @param appSettingsMap
     */
    void cancelAppSettings(Map<String, Object> appSettingsMap);

    /**
     * 取消应用，使用人数减1
     * @param appId
     */
    void cancelNumber(String appId);

    /**
     * 添加应用，使用人数加1
     * @param appId
     */
    void addNumber(String appId);

}
