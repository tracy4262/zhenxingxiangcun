package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface ElectricPowerMapper {
    Map<String,Object> query(Map<String, Object> params);

    int saveOrUpdate(Map<String, Object> params);
}
