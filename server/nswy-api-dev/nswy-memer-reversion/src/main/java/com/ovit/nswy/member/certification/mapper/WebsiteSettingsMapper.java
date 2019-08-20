package com.ovit.nswy.member.certification.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebsiteSettingsMapper {

    Map<String, Object> findWebsiteSettingsByAccount(Map<String, Object> accountInfo);

    List<Map<String, Object>> findAllWebsiteInfo(String userType);

    void saveWebsiteSettings(Map<String, Object> appSettingsMap);

    void updateWebsiteSettings(Map<String, Object> websiteSettingsMap);

    void updateIdentityFlag(Map<String, Object> param);

}