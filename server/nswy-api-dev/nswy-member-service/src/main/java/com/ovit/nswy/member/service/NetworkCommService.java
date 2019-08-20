package com.ovit.nswy.member.service;

import java.util.Map;

public interface NetworkCommService {
    int save(Map<String, Object> params);

    Map<String,Object> query(Map<String, Object> params);
}
