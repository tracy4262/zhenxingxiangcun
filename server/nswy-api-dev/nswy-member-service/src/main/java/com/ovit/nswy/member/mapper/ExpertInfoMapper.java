package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.ExpertInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

import java.util.List;

public interface ExpertInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExpertInfo record);

    int insertSelective(ExpertInfo record);

    ExpertInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExpertInfo record);

    int update(ExpertInfo record);

    Integer getApproveStatus(String Account);

    ExpertInfo getExpertInfo(String loginAccount);

    List<Map<String, Object>> findAll();

    List<Map<String,Object>> selectExpertBySpecies(@Param(value = "speciesIds") String speciesIds);

    List<Map<String, Object>> findExpertTitle(Map<String, Object> map);
}