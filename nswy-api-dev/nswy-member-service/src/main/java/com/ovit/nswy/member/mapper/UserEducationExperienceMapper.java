package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserEducationExperience;

import java.util.List;

public interface UserEducationExperienceMapper {

    void insert(List<UserEducationExperience> record);

    UserEducationExperience findUserEducation(UserEducationExperience record);

    void updateUserEducation(UserEducationExperience record);

    void deleteByAccount(String account);
}