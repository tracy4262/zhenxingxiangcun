package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.model.ProxyCorpInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProxyCorpInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProxyCorpInfo record);

    int insertSelective(ProxyCorpInfo record);

    ProxyCorpInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProxyCorpInfo record);

    int updateByPrimaryKey(ProxyCorpInfo record);

    ProxyCorpInfo findByAccount(ProxyCorpInfo record);

    List<Map<String, Object>> findAll();

    Integer getApproveStatus(String Account);

    List<Map<String, Object>> queryCorpByLocation(String location);

    List<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String,Object>> selectCorpBySpecies(@Param(value = "speciesIds") String speciesIds);

    List<Map<String,Object>> queryCorpNotProxy(Map<String, Object> params);
}