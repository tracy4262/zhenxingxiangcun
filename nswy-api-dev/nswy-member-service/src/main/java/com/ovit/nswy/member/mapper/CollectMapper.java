package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface CollectMapper {

    List<Map<String, Object>> queryList(Map<String, Object> params);

    List<Map<String, Object>> queryNext(Map<String, Object> params);

    void rename(Map<String, Object> params);

    void renameRemark(Map<String, Object> params);

    void del(Map<String, Object> params);

    Integer being(Map<String, Object> params);

    int  add(Map<String, Object> params);

    List<Map<String, Object>> queryListByPidAndAccount(String account);

    List<Map<String, Object>> queryChildNode(int id);

    List<Map<String, Object>> selectByAccountAndPidAndTitle(Map<String, Object> params);

    void updata(Map<String, Object> params);

    List<Map<String, Object>> findByName(Map<String, Object> params);
}