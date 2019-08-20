package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface WebsiteSettingsMapper {

    Map<String, Object> findWebsiteSettingsByAccount(Map<String, Object> accountInfo);

    List<Map<String, Object>> findAllWebsiteInfo(String userType);

    void saveWebsiteSettings(Map<String, Object> appSettingsMap);

    void updateWebsiteSettings(Map<String, Object> websiteSettingsMap);

}