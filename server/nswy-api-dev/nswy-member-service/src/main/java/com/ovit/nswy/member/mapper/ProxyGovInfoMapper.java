package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.ProxyGovInfo;

import java.util.List;
import java.util.Map;

public interface ProxyGovInfoMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(Map<String, Object> params);

    int insertSelective(ProxyGovInfo record);

    ProxyGovInfo selectByPrimaryKey(Integer id);

    List<Map<String, Object>> findAll();

    ProxyGovInfo findByAccount(String Account);

    int updateByPrimaryKeySelective(ProxyGovInfo record);

    int updateByPrimaryKey(ProxyGovInfo record);

    Integer getApproveStatus(String Account);

    List<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String,Object>> queryGovNotProxy(Map<String, Object> params);
}