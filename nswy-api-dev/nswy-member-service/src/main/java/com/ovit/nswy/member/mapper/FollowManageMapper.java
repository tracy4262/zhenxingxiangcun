package com.ovit.nswy.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FollowManageMapper {

    Map<String,Object> findSpecByAccount(Map<String,Object> params);

    List<Map<String,Object>> findFollowList(Map<String,Object> params);

    List<Map<String,Object>> findList(Map<String,Object> params);

    List<Map<String,Object>> findLoginByMember(Map<String,Object> params);

    List<Map<String,Object>> findExpertInfo(Map<String,Object> params);



    List<Map<String,Object>> findFollowMemberInfo(Map<String,Object> params);

    List<Map<String,Object>> findLoginByMemberList(Map<String,Object> params);

    Integer findCountFollowManageInfo(Map<String,Object> params);

    Map<String,Object> findFollowInfoByParentId(Map<String,Object> params);


    List<Map<String,Object>> findFllowDict(Map<String,Object> params);

     void  saveFollowInfo(Map<String,Object> params);

     void deleteFollowInfo(Map<String,Object> params);

    void  insertFollowMemberInfo(Map<String,Object> params);

    void deleteFollowMemberInfo(Map<String,Object> params);

    void updateFollowMemberInfo(Map<String,Object> params);






}

