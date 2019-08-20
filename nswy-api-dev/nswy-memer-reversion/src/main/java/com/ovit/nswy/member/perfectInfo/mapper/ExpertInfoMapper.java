package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpertInfoMapper {

    Map<String,Object> findExpertInfo(Map<String, Object> params);

    void updateExpertInfoName(Map<String, Object> params);

    void insertExpertInfo(Map<String, Object> params);

}
