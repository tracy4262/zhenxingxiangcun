package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserEducationExperienceMapper;
import com.ovit.nswy.member.model.UserEducationExperience;
import com.ovit.nswy.member.service.UserEducationExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEducationExperienceServiceImpl implements UserEducationExperienceService {

    @Autowired
    private UserEducationExperienceMapper userEducationExperienceMapper;

    @Override
    public UserEducationExperience findUserEducation(UserEducationExperience info) {
        UserEducationExperience user = userEducationExperienceMapper.findUserEducation(info);
        return user;
    }

    @Override
    public void updateUserEducation(UserEducationExperience info) {
        userEducationExperienceMapper.updateUserEducation(info);
    }

    @Override
    public void insert(List<UserEducationExperience> record) {
        userEducationExperienceMapper.insert(record);
    }

    @Override
    public void deleteByAccount(String account) {
        userEducationExperienceMapper.deleteByAccount(account);
    }
}
