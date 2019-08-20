package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.SelfMenuConf;

public interface SelfMenuConfMapper {
    int deleteByPrimaryKey(Integer id);

    void deleteByAccount(String account);

    SelfMenuConf findByAccount(String account);

    int insert(SelfMenuConf record);

    SelfMenuConf selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SelfMenuConf record);
}