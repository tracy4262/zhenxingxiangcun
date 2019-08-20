package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserReligionMapper;
import com.ovit.nswy.member.model.UserReligion;
import com.ovit.nswy.member.service.UserReligionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserReligionServiceImpl implements UserReligionService {
    @Autowired
    private UserReligionMapper IDao;

    @Override
    public int deleteByAccount(String account) {
        return IDao.deleteByAccount(account);
    }

    @Override
    public int insert(UserReligion record) {
        return IDao.insert(record);
    }

    @Override
    public UserReligion findByAccount(String account) {
        return IDao.findByAccount(account);
    }
}
