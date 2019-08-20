package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.FavoriteKnowledge;

public interface FavoriteKnowledgeService {
    int insert(FavoriteKnowledge record);

    FavoriteKnowledge selectByAccount(String account);
}
