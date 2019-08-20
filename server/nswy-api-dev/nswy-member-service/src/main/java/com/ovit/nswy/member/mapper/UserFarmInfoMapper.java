package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserFarmInfo;

import java.util.List;

public interface UserFarmInfoMapper {

    void insert(List<UserFarmInfo> record);

    UserFarmInfo findUserFarmInfo(UserFarmInfo record);

    void updateUserFarmInfo(UserFarmInfo record);

    void deleteByAccount(String account);
}