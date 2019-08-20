package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandSoilMicroelementService {
    Integer saveOrUpdateLandInfoMicroelements(Map<String, Object> params);

    Map<String,Object> queryLandInfoMicroelements(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
