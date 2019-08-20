package com.ovit.nswy.member.service;


import java.util.Map;

public interface ProcessingWaterQualityService {
    Map<String,Object> query(Map<String, Object> params);

    void update(Map<String, Object> params);

    void add(Map<String, Object> params);

}