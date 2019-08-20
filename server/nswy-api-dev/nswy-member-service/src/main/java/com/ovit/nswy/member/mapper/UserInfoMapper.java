package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserInfo;

public interface UserInfoMapper {

    void insert(UserInfo record);

    UserInfo findUserInfo(String account);

    void updateUserInfo(UserInfo record);
}