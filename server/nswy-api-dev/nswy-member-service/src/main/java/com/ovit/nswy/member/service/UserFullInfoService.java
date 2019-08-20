package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserFullInfo;

public interface UserFullInfoService {

    UserFullInfo findUserFullInfo(UserFullInfo user);

    int updateUserFullInfo(UserFullInfo user);

    int insert(UserFullInfo user);

}
