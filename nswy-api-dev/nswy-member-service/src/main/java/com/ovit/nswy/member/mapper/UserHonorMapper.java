package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserHonor;

import java.util.List;

public interface UserHonorMapper {
    int deleteByAccount(String account);

    int insert(List<UserHonor> record);
    List<UserHonor> selectByAccount(String account);


}