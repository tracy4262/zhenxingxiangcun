package com.ovit.nswy.member.mapper;


import com.ovit.nswy.member.model.Investment;

public interface InvestmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Investment record);

    int insertSelective(Investment record);

    Investment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Investment record);

    int updateByPrimaryKeyWithBLOBs(Investment record);

    int updateByPrimaryKey(Investment record);

    Investment selectByAccount(String account);
}