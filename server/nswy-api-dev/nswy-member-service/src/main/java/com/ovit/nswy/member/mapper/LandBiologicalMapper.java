package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface LandBiologicalMapper {
    Integer saveOrUpdateLandInfoBiological(Map<String, Object> params);

    Map<String,Object> queryLandInfoBiological(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
