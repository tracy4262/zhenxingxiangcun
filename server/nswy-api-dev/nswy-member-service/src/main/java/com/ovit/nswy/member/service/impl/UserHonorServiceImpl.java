package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserHonorMapper;
import com.ovit.nswy.member.model.UserHonor;
import com.ovit.nswy.member.service.UserHonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHonorServiceImpl implements UserHonorService {
    @Autowired
    private UserHonorMapper IDao;

    @Override
    public int deleteByAccount(String account) {
        return IDao.deleteByAccount(account);
    }

    @Override
    public int insert(List<UserHonor> record) {
        return IDao.insert(record);
    }

    @Override
    public List<UserHonor> selectByAccount(String account) {
        return IDao.selectByAccount(account);
    }
}
