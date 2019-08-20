package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.Friend;

import java.util.List;

public interface FriendService {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    List<Friend> findByName(String name);

    Friend selectByPrimaryKey(Integer id);

    List<Friend> findAll();

    int updateByPrimaryKey(Friend record);

}