package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;

public interface FollowManageService {

    List<Map<String,Object>> findSepciesList(Map<String,Object> params);

    PageInfo<Map<String,Object>> findSpecByAccount(Map<String,Object> params);

    void insertFollow(Map<String,Object> params);

    void deleteFollowInfo(Map<String,Object> params);

    PageInfo<Map<String,Object>> findLoginByMember(Map<String,Object> params);

    List<Map<String,Object>> findList(Map<String,Object> params);

    void insertFollowMemberInfo(Map<String,Object> params);

    void deleteFollowMemberInfo(Map<String,Object> params);

    PageInfo<Map<String,Object>> findLoginByMemberList(Map<String,Object> params);

    void updateFollowMemberInfo(Map<String,Object> params);
}
