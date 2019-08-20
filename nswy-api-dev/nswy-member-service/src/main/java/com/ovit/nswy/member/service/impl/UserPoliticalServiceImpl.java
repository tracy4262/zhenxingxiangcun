package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserPoliticalMapper;
import com.ovit.nswy.member.model.UserPolitical;
import com.ovit.nswy.member.service.UserPoliticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPoliticalServiceImpl implements UserPoliticalService {
    @Autowired
    private UserPoliticalMapper IDao;

    @Override
    public int deleteByAccount(String account) {
        return IDao.deleteByAccount(account);
    }

    @Override
    public int insert(UserPolitical record) {
        return IDao.insert(record);
    }

    @Override
    public UserPolitical findByAccount(String account) {
        return IDao.findByAccount(account);
    }
}
