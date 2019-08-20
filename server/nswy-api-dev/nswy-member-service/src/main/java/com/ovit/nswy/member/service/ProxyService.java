package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProxyService {

    PageInfo<List<Map<String,Object>>> queryList(Map<String, Object> params);

    void delProxy(Map<String, Object> params);

    void delProxyInfo(Map<String, Object> params);

    PageInfo<Map<String, Object>> queryStatus(Map<String, Object> params);

    PageInfo<Map<String, Object>> queryInfo(Map<String, Object> params);

    Map<String, Object> queryStatusDetail(Map<String, Object> params);

    Map<String,Object> queryInfoDetail(Map<String,Object> params);

    Map<String, Object> queryStatusById(Map<String, Object> params);

    void delProxyById(Map<String, Object> params);

    void addProxy(Map<String, Object> params);

    Map<String, Object> queryPassWord(Map<String, Object> params);

    Map<String,Object> queryNameFromCorpOrGov(Map<String, Object> params);

    Map<String,Object> queryInfoById(Map<String, Object> params);

    List<Map<String,Object>> queryProxyList(Map<String, Object> params);
}