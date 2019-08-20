package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.SignIn;

public interface SignInMapper {
    int deleteByPrimaryKey(int id);

    int insert(SignIn record);

    SignIn selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);

    int findCountSignIn(String userId);
}