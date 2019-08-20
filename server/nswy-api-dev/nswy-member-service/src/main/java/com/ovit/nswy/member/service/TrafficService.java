package com.ovit.nswy.member.service;


import java.util.Map;

public interface TrafficService {
    Map<String,Object> query(Map<String, Object> params);

    Map<String,Object> detail(Map<String, Object> params);

}