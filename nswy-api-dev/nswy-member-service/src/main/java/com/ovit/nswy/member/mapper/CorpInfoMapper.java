package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CorpInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CorpInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CorpInfo record);

    int insertSelective(CorpInfo record);

    CorpInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CorpInfo record);

    int updateByPrimaryKey(CorpInfo record);

    CorpInfo findByAccount(CorpInfo record);

    List<Map<String, Object>> findAll();

    Integer getApproveStatus(String Account);

    List<Map<String, Object>> queryCorpByLocation(String location);

    List<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String,Object>> selectCorpBySpecies(@Param(value = "speciesIds")String speciesIds);

    List<Map<String,Object>> queryCorpNotProxy(Map<String, Object> params);
}