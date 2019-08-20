package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserFullInfo;

public interface UserFullInfoMapper {
    UserFullInfo findUserFullInfo(UserFullInfo record);

    int updateUserFullInfo(UserFullInfo record);

    int insert(UserFullInfo record);

}