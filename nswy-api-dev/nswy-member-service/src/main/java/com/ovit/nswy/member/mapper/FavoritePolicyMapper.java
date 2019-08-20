package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoritePolicy;

public interface FavoritePolicyMapper {

    int insert(FavoritePolicy record);

    FavoritePolicy selectByAccount(String account);

    int updateByPrimaryKey(FavoritePolicy record);
}