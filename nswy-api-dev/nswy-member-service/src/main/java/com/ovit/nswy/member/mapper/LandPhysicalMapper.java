package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandPhysicalMapper {
    Integer saveOrUpdateLandInfoPhysical(Map<String, Object> params);

    Map<String,Object> queryLandInfoPhysical(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
