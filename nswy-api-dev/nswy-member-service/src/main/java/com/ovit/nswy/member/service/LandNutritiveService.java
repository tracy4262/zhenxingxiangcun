package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandNutritiveService {
    Integer saveOrUpdateLandInfoNutritive(Map<String, Object> params);

    Map<String,Object> queryLandInfoNutritive(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
