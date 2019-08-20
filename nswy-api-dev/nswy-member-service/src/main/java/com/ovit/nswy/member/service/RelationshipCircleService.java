package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;

public interface RelationshipCircleService {

    List<Map<String,Object>> findGroupList(Map<String,Object> params);

    PageInfo<Map<String,Object>> findLoginByCircle(Map<String,Object> params);

    void saveOrUpdateGroup(Map<String,Object> params);

    void deleteGroup(Map<String,Object> params);

    List<Map<String,Object>> findGroupFriendOfIm(Map<String,Object> params);

    void insertGroupFriendInfo(Map<String,Object> params);

    PageList findGroupFriendList(Map<String,Object> params);

    void deleteGroupFriendInfo(Map<String,Object> params);

    void deleteFriendInfo(Map<String,Object> params);

    void moveGroupFriendInfo(Map<String,Object> params);

    void insertStaff(Map<String,Object> params);
}
