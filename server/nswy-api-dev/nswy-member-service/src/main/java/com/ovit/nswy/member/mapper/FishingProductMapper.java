package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface FishingProductMapper {

    List<Map<String,Object>> findFishingProduct(Map<String,Object> params);

    void insertFishingProduct(Map<String,Object> params);

    void updateFishingProduct(Map<String,Object> params);

    void deleteFishingProduct(Map<String,Object> params);

    List<Map<String, Object>> findProductInfoBySpeciesId(Map<String, Object> speciesInfo);
}
