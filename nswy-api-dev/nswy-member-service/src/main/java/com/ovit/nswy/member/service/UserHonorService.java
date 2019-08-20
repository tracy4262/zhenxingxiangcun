package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserHonor;

import java.util.List;

public interface UserHonorService {
    int deleteByAccount(String account);

    int insert(List<UserHonor> record);

    List<UserHonor> selectByAccount(String account);

}