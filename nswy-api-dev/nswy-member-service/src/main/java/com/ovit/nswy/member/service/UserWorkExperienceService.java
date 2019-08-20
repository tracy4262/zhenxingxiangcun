package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserWorkExperience;

import java.util.List;

public interface UserWorkExperienceService {

    UserWorkExperience findUserWork(UserWorkExperience info);

    void updateUserWork(UserWorkExperience info);

    void deleteByAccount(String account);

    void insert(List<UserWorkExperience> record);

}
