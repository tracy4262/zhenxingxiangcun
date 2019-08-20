package com.ovit.nswy.member.service;

import java.util.Map;

public interface ElectricPowerService {
    int save(Map<String, Object> params);

    Map<String,Object> query(Map<String, Object> params);
}
