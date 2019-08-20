package com.ovit.nswy.member.certification.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectMapper {

    List<Map<String,Object>> findCollectDictList(Map<String,Object> params);

    List<Map<String,Object>> findCollectList(Map<String,Object> params);

    List<Map<String, Object>> queryList(Map<String, Object> params);

    List<Map<String, Object>> queryNext(Map<String, Object> params);

    void rename(Map<String, Object> params);

    void renameRemark(Map<String, Object> params);

    void del(Map<String, Object> params);

    Integer being(Map<String, Object> params);

    int  add(Map<String, Object> params);

    List<Map<String, Object>> queryListByPidAndAccount(Map<String,Object> accountInfo);

    List<Map<String, Object>> queryChildNode(int id);

    List<Map<String, Object>> selectByAccountAndPidAndTitle(Map<String, Object> params);

    void update(Map<String, Object> params);

    List<Map<String, Object>> findByName(Map<String, Object> params);

    List<Map<String,Object>> findUserQueryList(Map<String, Object> params);

    String findMaxId();

    List<Map<String,Object>> isExistData(Map<String, Object> params);
}