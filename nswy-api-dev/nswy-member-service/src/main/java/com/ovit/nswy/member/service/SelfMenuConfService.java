package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.SelfMenuConf;

public interface SelfMenuConfService {
    int deleteByPrimaryKey(Integer id);

    void deleteByAccount(String account);

    SelfMenuConf findByAccount(String account);

    int insert(SelfMenuConf record);

    SelfMenuConf selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(SelfMenuConf record);
}