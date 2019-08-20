package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserContact;

public interface UserContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserContact record);

    int updateByAccount(UserContact record);

    UserContact selectByAccount(String account);

    void updateByPrimaryKeySelective(UserContact userContact);

    void updateMobileAndPostalCodeByAccount(UserContact userContact);

    void insertMobileAndPostalCode(UserContact userContact);
}