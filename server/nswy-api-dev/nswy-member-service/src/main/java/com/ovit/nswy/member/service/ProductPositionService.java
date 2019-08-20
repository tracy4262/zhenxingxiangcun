package com.ovit.nswy.member.service;

import java.util.Map;

public interface ProductPositionService {
    Map<String, Object> queryGeographicalPosition(Map<String, Object> params);

    Integer saveGeographicalPosition(Map<String, Object> params);
}
