package com.ovit.nswy.member.mapper;


import java.util.Map;

public interface ProcessingWaterQualityMapper {
    Map<String,Object> query(Map<String, Object> params);

    void update(Map<String, Object> params);

    void add(Map<String, Object> params);
}