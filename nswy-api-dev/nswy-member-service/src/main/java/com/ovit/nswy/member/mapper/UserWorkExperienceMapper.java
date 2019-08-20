package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.UserWorkExperience;

import java.util.List;

public interface UserWorkExperienceMapper {

    void insert(List<UserWorkExperience> record);

    UserWorkExperience findUserWork(UserWorkExperience record);

    int updateUserWork(UserWorkExperience record);

    void deleteByAccount(String account);
}