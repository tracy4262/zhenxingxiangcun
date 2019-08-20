package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface ProductPositionMapper {
    Map<String,Object> queryGeographicalPosition(Map<String, Object> params);

    Integer saveGeographicalPosition(Map<String, Object> params);
}
