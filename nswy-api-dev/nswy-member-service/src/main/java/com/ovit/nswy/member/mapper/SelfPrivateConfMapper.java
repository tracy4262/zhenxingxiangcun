package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.SelfPrivateConf;

public interface SelfPrivateConfMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SelfPrivateConf record);

    int insertSelective(SelfPrivateConf record);

    SelfPrivateConf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SelfPrivateConf record);

    int updateByPrimaryKey(SelfPrivateConf record);
}