package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandWaterQualityService {
    Integer saveOrUpdateLandWaterQuality(Map<String, Object> params);

    Map<String,Object> queryLandWaterQuality(Map<String, Object> params);
}
