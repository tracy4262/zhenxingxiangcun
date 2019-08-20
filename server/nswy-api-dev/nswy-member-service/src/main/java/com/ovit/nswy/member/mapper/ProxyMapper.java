package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface ProxyMapper {

    List<Map<String,Object>> queryList(Map<String,Object> params);

    void delProxy(Map<String,Object> map);

    void delLoginUser(Map<String,Object> map);

    void updateInfo(Map<String,Object> map);

    List<Map<String,Object>> queryStatus(Map<String,Object> params);

    List<Map<String,Object>> queryInfo(Map<String,Object> params);

    Map<String,Object> queryStatusCorpDetail(Map<String,Object> params);

    Map<String,Object> queryStatusGovDetail(Map<String,Object> params);

    Map<String,Object> queryInfoCorpDetail(Map<String,Object> params);

    Map<String,Object> queryInfoGovDetail(Map<String,Object> params);

    Map<String,Object> queryStatusById(Map<String,Object> params);

    void delProxyById(Integer id);

    void addProxy(Map<String,Object> params);

    Map<String,Object> queryPassWord(Map<String,Object> params);

    Map<String,Object> queryNameFromCorpOrGov(Map<String, Object> params);

    Map<String,Object> queryInfoById(Map<String, Object> params);

    void updateInfos(Map<String,Object> map);

    Map<String,Object> findInfoAccount(Map<String,Object> map);

    void updateLoginPassWd(Map<String,Object> map);
}