package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

public interface FishingProductService {

    PageInfo<Map<String,Object>> findFishingProduct(Map<String,Object> params);

    void insertFishingProduct(Map<String,Object> params);

    void updateFishingProduct(Map<String,Object> params);

    void deleteFishingProduct(Map<String,Object> params);

    List<Map<String, Object>> findProductInfoBySpeciesId(Map<String, Object> speciesInfo);
}
