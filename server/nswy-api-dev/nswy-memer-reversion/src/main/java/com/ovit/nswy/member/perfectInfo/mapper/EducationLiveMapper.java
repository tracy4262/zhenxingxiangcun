package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EducationLiveMapper {

  List<Map<String,Object>> findEducationLive(Map<String,Object> params);

  void insertEducational(Map<String,Object> params);

  void updateEducationalLiveName(Map<String,Object> params);

  void deleteEducationalExperience(Map<String,Object> params);


}
