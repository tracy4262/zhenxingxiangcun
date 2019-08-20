package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserContactMapper;
import com.ovit.nswy.member.model.UserContact;
import com.ovit.nswy.member.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserContactServiceImpl implements UserContactService {
    @Autowired
    private UserContactMapper userContactMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userContactMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserContact record) {
        return userContactMapper.insert(record);
    }

    @Override
    public int updateByAccount(UserContact record) {
        return userContactMapper.updateByAccount(record);
    }

    @Override
    public UserContact selectByAccount(String account) {
        return userContactMapper.selectByAccount(account);
    }

    @Override
    public void updateByPrimaryKeySelective(UserContact userContact) {
        userContactMapper.updateByPrimaryKeySelective(userContact);
    }

    @Override
    public void updateMobileAndPostalCodeByAccount(UserContact userContact) {
        userContactMapper.updateMobileAndPostalCodeByAccount(userContact);
    }

    @Override
    public void insertMobileAndPostalCode(UserContact userContact) {
        userContactMapper.insertMobileAndPostalCode(userContact);
    }
}
