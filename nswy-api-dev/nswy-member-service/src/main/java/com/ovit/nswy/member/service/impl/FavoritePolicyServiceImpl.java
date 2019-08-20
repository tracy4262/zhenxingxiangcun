package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FavoritePolicyMapper;
import com.ovit.nswy.member.model.FavoritePolicy;
import com.ovit.nswy.member.service.FavoritePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritePolicyServiceImpl implements FavoritePolicyService {
    @Autowired
    private FavoritePolicyMapper dao;

    @Override
    public int insert(FavoritePolicy record) {
        return dao.insert(record);
    }

    @Override
    public FavoritePolicy selectByAccount(String account) {
        return dao.selectByAccount(account);
    }

}
