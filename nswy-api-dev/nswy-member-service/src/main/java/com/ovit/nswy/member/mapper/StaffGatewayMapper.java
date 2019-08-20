package com.ovit.nswy.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffGatewayMapper {

    List<Map<String,Object>> findRelationshipDictInfo(Map<String, Object> params);

    List<Map<String,Object>> findGroupList(Map<String, Object> params);

    List<Map<String,Object>> findEmployeeRoster(Map<String, Object> params);

    List<Map<String,Object>> findGroupFriendList(Map<String, Object> params);

    Map<String,Object> findGroupByPid(Map<String, Object> params);

    List<Map<String,Object>> findGroupById(Map<String, Object> params);

    List<Map<String,Object>> findStaffGatewayGroup(Map<String, Object> params);

    Map<String,Object> findMemberIdentityInfo(Map<String, Object> params);

    Map<String,Object> findGroupByGroupName(Map<String, Object> params);




    Map<String,Object> findLogin(Map<String, Object> params);

    void updateGroup(Map<String, Object> params);

    void updateGroupStatus(Map<String, Object> params);


    void saveGroup(Map<String, Object> params);

    void insertGroupFriendInfo(Map<String, Object> params);

    void updateStaffInfo(Map<String, Object> params);

    void moveGroupFriendInfo(Map<String, Object> params);

    void insertLogin(Map<String, Object> params);


    void deleteGroup(Map<String, Object> params);

    void deleteGroupFriendInfo(Map<String, Object> params);



}
