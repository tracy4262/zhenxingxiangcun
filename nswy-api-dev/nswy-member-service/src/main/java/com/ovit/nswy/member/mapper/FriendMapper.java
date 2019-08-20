package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Friend;

import java.util.List;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    List<Friend> findByName(String name);

    Friend selectByPrimaryKey(Integer id);

    List<Friend> findAll();

    int updateByPrimaryKey(Friend record);
}