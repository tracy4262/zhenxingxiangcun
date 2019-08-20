package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandEnvironmentalMapper {
    Map<String,Object> queryLandInfoEnvironmental(Map<String, Object> params);

    Integer saveOrUpdateLandInfoEnvironmental(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
