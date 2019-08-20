package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface AppSettingsService {

    /**
     * 通过用户名查询用户的应用设置信息
     * @param account
     * @return
     */
    Map<String, Object> findAppSettingsByAccount(String account);

    /**
     * 通过所有的应用设置信息
     * @return
     */
    Map<String,List<Map<String,Object>>> findAllAppInfo();

    /**
     * 保存应用设置数据
     * @param appSettingsMap
     */
    void saveAppSettings(Map<String, Object> appSettingsMap);

    /**
     * 更新应用设置数据
     * @param appSettingsMap
     */
    void updateAppSettings(Map<String, Object> appSettingsMap);
}