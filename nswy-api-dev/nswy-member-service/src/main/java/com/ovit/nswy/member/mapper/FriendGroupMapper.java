package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FriendGroup;

import java.util.List;

public interface FriendGroupMapper {
    int deleteByAccount(String account);

    int insert(List<FriendGroup> record);


    List<FriendGroup> selectByAccount(String account);


    int updateByPrimaryKey(FriendGroup record);
}