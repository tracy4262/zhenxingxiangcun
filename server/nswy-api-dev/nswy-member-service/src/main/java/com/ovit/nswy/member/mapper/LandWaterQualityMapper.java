package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandWaterQualityMapper {
    Integer saveOrUpdateLandWaterQuality(Map<String, Object> params);

    Map<String,Object> queryLandWaterQuality(Map<String, Object> params);
}
