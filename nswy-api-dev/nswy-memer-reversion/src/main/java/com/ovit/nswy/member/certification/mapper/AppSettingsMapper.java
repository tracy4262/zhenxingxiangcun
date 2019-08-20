package com.ovit.nswy.member.certification.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppSettingsMapper {

    List<Map<String, Object>> findAppSettingsByAccount(Map<String, Object> appInfo);

    List<Map<String, Object>> findAllAppInfo();

    void saveAppSettings(Map<String, Object> appSettingsMap);

    void cancelAppSettings(Map<String, Object> appSettingsMap);

    void deleteAppSettings(Map<String, Object> appSettingsMap);

    Map<String, Object> findAppSetting(Map<String, Object> appSettingsMap);

    void cancelNumber(String appId);

    void addNumber(String appId);

    List<Map<String, Object>> findAppTitle(Map<String, Object> params);

    void saveAppTitle(Map<String, Object> params);

    void updateAppTitle(Map<String, Object> params);

}