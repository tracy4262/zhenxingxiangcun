package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandSoilMicroelementMapper {
    Integer saveOrUpdateLandInfoMicroelements(Map<String, Object> params);

    Map<String,Object> queryLandInfoMicroelements(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
