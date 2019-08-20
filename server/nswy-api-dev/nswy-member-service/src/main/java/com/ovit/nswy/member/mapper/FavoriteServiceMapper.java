package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoriteService;

public interface FavoriteServiceMapper {

    int insert(FavoriteService record);


    FavoriteService selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(FavoriteService record);
}