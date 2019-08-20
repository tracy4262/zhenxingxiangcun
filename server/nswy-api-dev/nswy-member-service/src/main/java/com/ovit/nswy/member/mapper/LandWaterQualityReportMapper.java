package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface LandWaterQualityReportMapper {
    int uploadReport(Map<String, Object> map);

    List<Map<String,Object>> query(Map<String, Object> params);

    void delete(Map<String, Object> map);
}
