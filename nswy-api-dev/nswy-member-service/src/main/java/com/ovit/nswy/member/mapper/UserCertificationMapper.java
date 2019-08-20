package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserCertification;

public interface UserCertificationMapper {

    int insert(UserCertification record);

    UserCertification findUserCertification(UserCertification record);

    int updateUserCertification(UserCertification record);
}