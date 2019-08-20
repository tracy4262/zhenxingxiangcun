package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoriteKnowledge;

public interface FavoriteKnowledgeMapper {

    int insert(FavoriteKnowledge record);


    FavoriteKnowledge selectByPrimaryKey(Integer id);

    FavoriteKnowledge selectByAccount(String account);


    int updateByPrimaryKey(FavoriteKnowledge record);
}