package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserReligion;

public interface UserReligionMapper {
    int deleteByAccount(String account);

    int insert(UserReligion record);
    UserReligion findByAccount(String account);


}