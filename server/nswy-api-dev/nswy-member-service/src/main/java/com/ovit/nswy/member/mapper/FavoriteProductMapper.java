package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoriteProduct;

public interface FavoriteProductMapper {


    int insert(FavoriteProduct record);


    FavoriteProduct selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(FavoriteProduct record);
}