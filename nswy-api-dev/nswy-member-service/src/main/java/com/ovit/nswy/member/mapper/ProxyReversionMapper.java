package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface ProxyReversionMapper {

    void insertProxy(Map<String,Object> params);

    List<Map<String,Object>> queryAccount(Map<String, Object> params);

    Map<String,Object> queryByAccount(Map<String, Object> params);

    void updateProxy(Map<String,Object> params);

    Map<String,Object> proxyTemplate(Map<String,Object> params);

    Map<String,Object> queryProxyRecord(Map<String, Object> params);

    void saveProxyRecord(Map<String, Object> params);

    List<Map<String,Object>> waitProxy(Map<String, Object> params);

    List<Map<String,Object>> findMemberName(Map<String, Object> params);

    Map<String,Object> isCompleting(Map<String, Object> params);

    List<String> findMultiAccount(Map<String, Object> params);

    List<Map<String,Object>> findProxyInfo(String account);

}