package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Policy;

import java.util.List;
import java.util.Map;

public interface PolicyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Policy record);

    int insertSelective(Policy record);

    Policy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Policy record);

    int updateByPrimaryKey(Policy record);

    List<Policy> findPolicy();

    List<Policy> findPolicyTitle(Policy record);

    List<Policy> findIndexPolicy(Policy record);

    List<Map<String, Object>> getBriefNews();

    Policy findPolicyByDetailId(int id );
}