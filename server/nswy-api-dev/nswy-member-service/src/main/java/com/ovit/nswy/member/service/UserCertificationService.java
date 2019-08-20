package com.ovit.nswy.member.service;


import com.ovit.nswy.member.model.UserCertification;

public interface UserCertificationService {

    UserCertification findUserCertification(UserCertification info);

    int updateUserCertificaiton(UserCertification info);

    int save(UserCertification info);

}
