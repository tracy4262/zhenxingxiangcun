package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.FavoriteSpecies;
import com.ovit.nswy.member.model.Species;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

public interface SpecieService {
    int insert(FavoriteSpecies favoriteSpecies);

    List<Species> findFollowedSpecies(String account);

    void empty(String account);

    List<String> findByAccount(String account);

    FavoriteSpecies findSpecByAccount(String account);

    String getSpecies(String speciesId);

    void updateByPrimaryKey(FavoriteSpecies favoriteSpecies);

    List<Map<String,Object>> listAllSpeciesClass();

    Map<String,Object> listClassifySpeciesInfo();

    List<Map<String,Object>> speciesPageInfo(List<Map<String, Object>> speciesList, int pageNum, int pageSize);
}
