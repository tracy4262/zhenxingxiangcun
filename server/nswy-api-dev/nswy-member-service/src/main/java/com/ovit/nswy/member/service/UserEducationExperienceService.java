package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.UserEducationExperience;

import java.util.List;

public interface UserEducationExperienceService {
    UserEducationExperience findUserEducation(UserEducationExperience info);

    void updateUserEducation(UserEducationExperience info);

    void insert(List<UserEducationExperience> record);

    void deleteByAccount(String account);
}
