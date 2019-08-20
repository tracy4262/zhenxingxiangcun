package com.ovit.nswy.member.certification.service;


import com.ovit.nswy.member.certification.model.FriendGroup;

import java.util.List;
import java.util.Map;

public interface FriendGroupService {

    int insert(List<FriendGroup> record);

    List<FriendGroup> selectByAccount(Map<String,Object> accountInfo);

    int deleteByAccount(Map<String,Object> accountInfo);

    int addFriendGroup(List<Map<String, Object>> record, Map<String,Object> accountInfo);

}