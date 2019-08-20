package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FavoriteInformationMapper;
import com.ovit.nswy.member.model.FavoriteInformation;
import com.ovit.nswy.member.service.FavoriteInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteInformationServiceImpl implements FavoriteInformationService {
    @Autowired
    private FavoriteInformationMapper dao;

    @Override
    public int insert(FavoriteInformation record) {
        return dao.insert(record);
    }

    @Override
    public FavoriteInformation selectByAccount(String account) {
        return dao.selectByAccount(account);
    }

}
