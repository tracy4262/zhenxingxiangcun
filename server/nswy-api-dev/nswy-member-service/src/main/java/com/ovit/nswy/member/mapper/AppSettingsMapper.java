package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface AppSettingsMapper {

    Map<String, Object> findAppSettingsByAccount(String account);

    List<Map<String, Object>> findAllAppInfo();

    void saveAppSettings(Map<String, Object> appSettingsMap);

    void updateAppSettings(Map<String, Object> appSettingsMap);

}