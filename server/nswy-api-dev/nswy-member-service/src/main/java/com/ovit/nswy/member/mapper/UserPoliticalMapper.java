package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserPolitical;

public interface UserPoliticalMapper {
    int deleteByAccount(String account);

    int insert(UserPolitical record);

    UserPolitical findByAccount(String account);


}