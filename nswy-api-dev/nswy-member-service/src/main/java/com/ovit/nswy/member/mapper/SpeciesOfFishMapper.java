package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface SpeciesOfFishMapper {

    List<Map<String, Object>> getSpeciesOfFish(Map<String, Object> speciesInfo);

    void insertSpeciesOfFish(List<Map<String, Object>> speciesInfo);

    void deleteSpeciesOfFish(Map<String, Object> speciesInfo);

}