package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.UserFarmInfoMapper;
import com.ovit.nswy.member.model.UserFarmInfo;
import com.ovit.nswy.member.service.UserFarmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFarmInfoServiceImpl implements UserFarmInfoService {

    @Autowired
    private UserFarmInfoMapper userFarmInfoMapper;

    @Override
    public UserFarmInfo findUserFarmInfo(UserFarmInfo info) {
        UserFarmInfo userFarmInfo = userFarmInfoMapper.findUserFarmInfo(info);
        return userFarmInfo;
    }

    @Override
    public void updateUserFarmInfo(UserFarmInfo info) {
        userFarmInfoMapper.updateUserFarmInfo(info);
    }

    @Override
    public void deleteByAccount(String account) {
        userFarmInfoMapper.deleteByAccount(account);
    }

    @Override
    public void insert(List<UserFarmInfo> record) {
        userFarmInfoMapper.insert(record);
    }
}
