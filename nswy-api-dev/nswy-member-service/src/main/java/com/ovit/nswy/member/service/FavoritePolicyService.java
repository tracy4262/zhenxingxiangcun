package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.FavoritePolicy;

public interface FavoritePolicyService {
    int insert(FavoritePolicy record);

    FavoritePolicy selectByAccount(String account);
}
