package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FavoriteKnowledgeMapper;
import com.ovit.nswy.member.model.FavoriteKnowledge;
import com.ovit.nswy.member.service.FavoriteKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteKnowledgeServiceImpl implements FavoriteKnowledgeService {
    @Autowired
    private FavoriteKnowledgeMapper dao;

    @Override
    public int insert(FavoriteKnowledge record) {
        return dao.insert(record);
    }

    @Override
    public FavoriteKnowledge selectByAccount(String account) {
        return dao.selectByAccount(account);
    }

}
