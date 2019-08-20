package com.ovit.nswy.member.service;

import java.util.Map;

public interface LandBiologicalService {
     Integer saveOrUpdateLandInfoBiological(Map<String, Object> params);

    Map<String,Object> queryLandInfoBiological(Map<String, Object> params);

    Map<String,Object> queryIsExit(Map<String, Object> map);
}
