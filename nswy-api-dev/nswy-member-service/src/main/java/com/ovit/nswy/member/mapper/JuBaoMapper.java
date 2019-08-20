package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.JuBao;

public interface JuBaoMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(JuBao record);

    int insertSelective(JuBao record);

    JuBao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JuBao record);

    int updateByPrimaryKey(JuBao record);

    JuBao selectByLinkAndAccount(JuBao report);
}