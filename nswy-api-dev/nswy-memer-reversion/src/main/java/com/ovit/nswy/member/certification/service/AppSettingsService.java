package com.ovit.nswy.member.certification.service;

import java.util.List;
import java.util.Map;

public interface AppSettingsService {

    /**
     * 通过用户名查询用户的应用设置信息
     * @return
     */
    List<Map<String, Object>> findAppSettingsByAccount(Map<String, Object> appInfo);

    /**
     * 查询用户的应用设置信息
     * @return
     */
    List<Map<String,Object>> findAllAppInfo(List<Map<String,Object>> appInfo);

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
     * 通过account、appId查出对应应用
     * @param appSettingsMap
     * @return
     */
    Map<String, Object> findAppSetting(Map<String, Object> appSettingsMap);

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

    /**
     * 保存或取消应用数据
     * @param params
     */
    void saveOrCancel(Map<String,Object> params);

    /**
     * 查询应用标题
     * @param params
     * @return
     */
    List<Map<String,Object>> findAppTitle(Map<String,Object> params);

    /**
     * 编辑应用标题
     * @param params
     */
    void updateAppTitle(Map<String,Object> params);
}