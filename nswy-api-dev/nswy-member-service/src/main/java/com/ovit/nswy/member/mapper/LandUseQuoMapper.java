package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface LandUseQuoMapper {
    Integer saveLandPlot(Map<String, Object> params);

    Integer deleteLandPlot(Map<String, Object> params);

    List<Map<String,Object>> queryLandPlot(Map<String, Object> params);

    void updateWaterQualityInfo(Map<String, Object> params);
}
