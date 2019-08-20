package com.ovit.nswy.member.certification.mapper;


import com.ovit.nswy.member.certification.model.FollowInfo;
import com.ovit.nswy.member.certification.model.SysFollowDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysFollowDictMapper {

    void insert(SysFollowDict record);

    SysFollowDict findQuery(Map<String, Object> map);

    List<Map<String,Object>> findList(Map<String, Object> map);

    List<Map<String,Object>> findSysFollowDict(Map<String, Object> map);

    void insertFollow(FollowInfo followInfo);

    List<Map<String,Object>> findFllowDict(Map<String, Object> map);

    void deleteFollowDict(Map<String, Object> map);

    Map<String,Object> findPushStatus(Map<String,Object> accountInfo);

    void addPushStatus(Map<String,Object> accountInfo);

    void updatePushStatus(Map<String,Object> accountInfo);

    void deletePushStatus(Map<String,Object> accountInfo);

}