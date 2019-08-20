package com.ovit.nswy.member.certification.service;

import java.util.Map;

public interface WebsiteSettingsService {

    /**
     * 通过用户名查询用户的网站设置信息
     * @param accountInfo
     * @return
     */
    Map<String, Object> findWebsiteSettingsByAccount(Map<String, Object> accountInfo);

    /**
     * 查询对应角色下所有的网站模块、模板信息
     * @param userType
     * @return
     */
    Map<String,Object> findAllWebsiteInfo(String userType);

    /**
     * 保存网站设置数据
     * @param websiteSettingsMap
     */
    void saveWebsiteSettings(Map<String, Object> websiteSettingsMap);

    /**
     * 更新网站设置数据
     * @param websiteSettingsMap
     */
    void updateWebsiteSettings(Map<String, Object> websiteSettingsMap);

    /**
     * 更新认证步骤
     * @param params
     */
    void updateIdentityFlag(Map<String, Object> params);
}