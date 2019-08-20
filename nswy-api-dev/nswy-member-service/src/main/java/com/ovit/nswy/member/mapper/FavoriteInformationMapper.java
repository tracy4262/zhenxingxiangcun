package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoriteInformation;

public interface FavoriteInformationMapper {

    int insert(FavoriteInformation record);

    FavoriteInformation selectByAccount(String account);

}