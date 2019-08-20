package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MyApp;

import java.util.List;
import java.util.Map;

public interface MyAppMapper {
    int insert(List<MyApp> record);

    List<MyApp> selectByAccount(String account);

    List<MyApp> selectByLevel(Integer level);

    void deleteAll(Map<String,Object> map);

    void deleteByLevel(Integer level);

    void saveAppStatus(Map<String, Object> account);

    String queryAppUseStatus(Map<String, Object> params);

    List<Map<String,Object>> queryMyAppByAccount(Map<String, Object> params);

    void updateByAccount(Map<String, Object> account);

    Map<String,Object> queryAppUseStatusCountByAccount(Map<String, Object> account);
}