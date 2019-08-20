package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserReligion;

public interface UserReligionService {
    int deleteByAccount(String account);

    int insert(UserReligion record);

    UserReligion findByAccount(String account);

}