package com.ovit.nswy.member.mapper;


import java.util.Map;

public interface TrafficMapper {
    Map<String,Object> query(Map<String, Object> params);

    Map<String,Object> detail(Map<String, Object> params);
}