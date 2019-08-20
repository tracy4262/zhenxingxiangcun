package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.ExpertDetail;

public interface ExpertDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExpertDetail record);

    int insertSelective(ExpertDetail record);

    ExpertDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExpertDetail record);

    int updateByPrimaryKey(ExpertDetail record);
}