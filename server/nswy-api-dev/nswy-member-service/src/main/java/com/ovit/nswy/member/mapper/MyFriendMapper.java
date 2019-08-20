package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MyFriend;

import java.util.List;

public interface MyFriendMapper {

    void insert(MyFriend record);

    MyFriend selectByPrimaryKey(Integer id);

    List<MyFriend> findMyFriend(MyFriend record);

    void updateByPrimaryKey(MyFriend record);
}