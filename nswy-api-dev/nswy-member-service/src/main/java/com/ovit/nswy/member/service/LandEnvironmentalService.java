package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandEnvironmentalService {
    Map<String,Object> queryLandInfoEnvironmental(Map<String, Object> params);

    Integer saveOrUpdateLandInfoEnvironmental(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
