package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandPhysicalService {
    Integer saveOrUpdateLandInfoPhysical(Map<String, Object> params);

    Map<String,Object> queryLandInfoPhysical(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
