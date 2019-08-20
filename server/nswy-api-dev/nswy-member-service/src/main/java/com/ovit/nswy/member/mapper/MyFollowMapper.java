package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MyFollow;

import java.util.List;

public interface MyFollowMapper {

    int insertMyFollow(MyFollow record);

    List<MyFollow> findMyFollow(MyFollow record);

    List<MyFollow> findFollowInformation(String account);

    List<MyFollow> findFollowKnowledge(String account);

    List<MyFollow> findFollowPolicy(String account);

    int updateMyFollow(MyFollow record);

}