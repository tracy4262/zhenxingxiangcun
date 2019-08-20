package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserFarmInfo;

import java.util.List;

public interface UserFarmInfoService {

	UserFarmInfo findUserFarmInfo(UserFarmInfo info);

	void updateUserFarmInfo(UserFarmInfo info);

	void deleteByAccount(String account);

	void insert(List<UserFarmInfo> record);

}
