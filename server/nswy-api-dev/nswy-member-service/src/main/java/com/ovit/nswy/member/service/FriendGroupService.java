package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.FriendGroup;

import java.util.List;
import java.util.Map;

public interface FriendGroupService {

    int insert(List<FriendGroup> record);

    List<FriendGroup> selectByAccount(String account);

    int deleteByAccount(String account);

    int addFriendGroup(List<Map<String,Object>> record, String account);

}