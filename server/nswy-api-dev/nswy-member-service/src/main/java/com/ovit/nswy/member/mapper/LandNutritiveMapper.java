package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandNutritiveMapper {
    Integer saveOrUpdateLandInfoNutritive(Map<String, Object> params);

    Map<String,Object> queryLandInfoNutritive(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
