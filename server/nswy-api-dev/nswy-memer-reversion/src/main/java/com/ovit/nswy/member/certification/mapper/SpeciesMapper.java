package com.ovit.nswy.member.certification.mapper;


import com.ovit.nswy.member.certification.model.FavoriteSpecies;
import com.ovit.nswy.member.certification.model.Species;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SpeciesMapper {

    int insert(FavoriteSpecies favoriteSpecies);

    List<Species> findFollowedSpecies(String accout);

    void empty(String accout);

    List<String> findByAccount(String account);

    FavoriteSpecies findSpecByAccount(Map<String,Object> accountInfo);

    void deleteSpecByAccount(Map<String,Object> accountInfo);

    void updateByPrimaryKey(FavoriteSpecies favoriteSpecies);

    List<Map<String,Object>> relevantProduct(String speciesName);

    List<Map<String,Object>> relevantExpert(String speciesName);

    Map<String,Object> relevantCorp(String account);
}