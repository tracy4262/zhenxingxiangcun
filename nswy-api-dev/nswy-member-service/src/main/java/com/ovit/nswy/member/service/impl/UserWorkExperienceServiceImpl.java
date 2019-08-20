package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserWorkExperienceMapper;
import com.ovit.nswy.member.model.UserWorkExperience;
import com.ovit.nswy.member.service.UserWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWorkExperienceServiceImpl implements UserWorkExperienceService {

    @Autowired
    private UserWorkExperienceMapper userWorkExperienceMapper;

    @Override
    public UserWorkExperience findUserWork(UserWorkExperience info) {
        UserWorkExperience userWork = userWorkExperienceMapper.findUserWork(info);
        return userWork;
    }

    @Override
    public void updateUserWork(UserWorkExperience info) {
        userWorkExperienceMapper.updateUserWork(info);
    }

    @Override
    public void deleteByAccount(String account) {
        userWorkExperienceMapper.deleteByAccount(account);
    }

    @Override
    public void insert(List<UserWorkExperience> record) {
        userWorkExperienceMapper.insert(record);
    }
}
