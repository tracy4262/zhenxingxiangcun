package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface NetworkCommMapper {
    int save(Map<String, Object> params);

    Map<String,Object> query(Map<String, Object> params);
}
