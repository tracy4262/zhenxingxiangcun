package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FavoriteSpecies;
import com.ovit.nswy.member.model.Species;

import javax.xml.soap.SAAJResult;
import java.util.List;
import java.util.Map;

public interface SpeciesMapper {

    int insert(FavoriteSpecies favoriteSpecies);

    List<Species> findFollowedSpecies(String accout);

    void empty(String accout);

    List<String> findByAccount(String account);

    FavoriteSpecies findSpecByAccount(Map<String,Object> params);

    void updateByPrimaryKey(FavoriteSpecies favoriteSpecies);

    List<Map<String,Object>> relevantProduct(String speciesName);

    List<Map<String,Object>> relevantExpert(String speciesName);

    Map<String,Object> relevantCorp(String account);

    List<Map<String,Object>> findExpert(String speciesName);

}