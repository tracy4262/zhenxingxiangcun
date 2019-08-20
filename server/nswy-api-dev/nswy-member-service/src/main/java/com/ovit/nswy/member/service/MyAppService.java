package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.MyApp;

import java.util.List;
import java.util.Map;

public interface MyAppService {
    int insert(List<MyApp> record);

    List<MyApp> selectByAccount(String account);

    List<MyApp> selectByLevel(Integer level);

    void deleteByLevel(Integer level);

    void deleteAll(Map<String, Object> map);

    int queryAppUseStatus(Map<String, Object> params);

    void saveAppStatus(Map<String, Object> params);

    List<Map<String,Object>> queryMyAppByAccount(Map<String, Object> params);

    Map<String,Object> queryAppUseStatusCountByAccount(Map<String, Object> params);

    void updateByAccount(Map<String, Object> params);
}