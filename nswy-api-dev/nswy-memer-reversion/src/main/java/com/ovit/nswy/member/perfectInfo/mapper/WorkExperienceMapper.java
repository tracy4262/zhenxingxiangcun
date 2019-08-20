package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkExperienceMapper {

    List<Map<String,Object>> findWorkExperience(Map<String, Object> params);

    void updateWorkExperienceName(Map<String, Object> params);

    void saveWorkExperience(Map<String, Object> params);

    void deleteWorkExperience(Map<String, Object> params);

}
