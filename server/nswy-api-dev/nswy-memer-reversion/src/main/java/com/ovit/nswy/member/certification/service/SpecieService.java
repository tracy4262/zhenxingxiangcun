package com.ovit.nswy.member.certification.service;


import com.ovit.nswy.member.certification.model.FavoriteSpecies;

import java.util.List;
import java.util.Map;

public interface SpecieService {

    int insert(FavoriteSpecies favoriteSpecies);

    FavoriteSpecies findSpecByAccount(Map<String,Object> accountInfo);

    void deleteSpecByAccount(Map<String,Object> accountInfo);

    void updateByPrimaryKey(FavoriteSpecies favoriteSpecies);

    List<Map<String,Object>> listAllSpeciesClass();

}
