package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CoterieMember;

public interface CoterieMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoterieMember record);

    CoterieMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(CoterieMember record);
}