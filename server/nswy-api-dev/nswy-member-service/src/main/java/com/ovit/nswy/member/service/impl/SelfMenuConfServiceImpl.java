package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.SelfMenuConfMapper;
import com.ovit.nswy.member.model.SelfMenuConf;
import com.ovit.nswy.member.service.SelfMenuConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelfMenuConfServiceImpl implements SelfMenuConfService {

    @Autowired
    private SelfMenuConfMapper selfMCMDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return selfMCMDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SelfMenuConf record) {
        return selfMCMDao.insert(record);
    }

    @Override
    public SelfMenuConf selectByPrimaryKey(Integer id) {
        return selfMCMDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(SelfMenuConf record) {
        return selfMCMDao.updateByPrimaryKey(record);
    }

    @Override
    public void deleteByAccount(String account) {
        selfMCMDao.deleteByAccount(account);
    }

    @Override
    public SelfMenuConf findByAccount(String account) {
        return selfMCMDao.findByAccount(account);
    }

}
