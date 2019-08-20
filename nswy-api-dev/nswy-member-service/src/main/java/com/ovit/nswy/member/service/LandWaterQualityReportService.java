package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface LandWaterQualityReportService {
    int uploadReport(Map<String, Object> map);

    List<Map<String,Object>> query(Map<String, Object> params);

    void delete(Map<String, Object> map);
}
