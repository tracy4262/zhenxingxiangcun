package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserPolitical;

public interface UserPoliticalService {
    int deleteByAccount(String account);

    int insert(UserPolitical record);

    UserPolitical findByAccount(String account);
}