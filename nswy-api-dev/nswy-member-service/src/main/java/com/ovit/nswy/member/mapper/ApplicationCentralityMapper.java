package com.ovit.nswy.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApplicationCentralityMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    List<Map<String,Object>> findUserTypeList(Map<String,Object> params);

    List<Map<String,Object>> findServiceTypeList(Map<String,Object> params);

    List<Map<String,Object>> findAppAbstract(Map<String,Object> params);

    List<Map<String,Object>> findAppSettingsByAccount(Map<String,Object> params);

    void saveAppSettings(Map<String, Object> appSettingsMap);

    void cancelAppSettings(Map<String, Object> appSettingsMap);

    void cancelNumber(String appId);

    void addNumber(String appId);




}
