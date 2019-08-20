package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserWebsite;

public interface UserWebsiteMapper {

    int deleteByAccount(String account);

    int insert(UserWebsite record);

    int updateByAccount(UserWebsite record);

    UserWebsite selectByAccount(String account);


}