package com.ovit.nswy.member.service;

import java.util.Map;

public interface SoilFertilityService {
    Map<String,Object> query(Map<String, Object> params);

    int save(Map<String, Object> params);
}
