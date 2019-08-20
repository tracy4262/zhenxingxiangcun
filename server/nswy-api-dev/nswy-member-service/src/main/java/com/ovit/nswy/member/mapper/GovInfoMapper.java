package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.GovInfo;
import java.util.Map;

import java.util.List;

public interface GovInfoMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(Map<String, Object> params);

    int insertSelective(GovInfo record);

    GovInfo selectByPrimaryKey(Integer id);

    List<Map<String, Object>> findAll();

    GovInfo findByAccount(String Account);

    int updateByPrimaryKeySelective(GovInfo record);

    int updateByPrimaryKey(GovInfo record);

    Integer getApproveStatus(String Account);

    List<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String,Object>> queryGovNotProxy(Map<String, Object> params);
}