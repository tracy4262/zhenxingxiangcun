package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserCertificationMapper;
import com.ovit.nswy.member.model.UserCertification;
import com.ovit.nswy.member.service.UserCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCertificationServiceImpl implements UserCertificationService {

    @Autowired
    private UserCertificationMapper userCertificationMapper;

    @Override
    public UserCertification findUserCertification(UserCertification info) {
        return userCertificationMapper.findUserCertification(info);
    }

    @Override
    public int updateUserCertificaiton(UserCertification info) {
        return userCertificationMapper.updateUserCertification(info);
    }

    @Override
    public int save(UserCertification info) {
        return userCertificationMapper.insert(info);
    }
}
