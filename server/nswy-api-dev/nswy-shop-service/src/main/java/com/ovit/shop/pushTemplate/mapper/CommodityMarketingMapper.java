package com.ovit.shop.pushTemplate.mapper;

import java.util.List;
import java.util.Map;

public interface CommodityMarketingMapper {

    Map<String,Object> getYearIdAndTemplateId(Map<String, Object> params);

    List<Map<String,Object>> networkStationList(Map<String, Object> params);

    void deleteNetworkInfo(Map<String, Object> params);

    void batchSaveNetwork(List<Map<String,Object>> list);

    String getBaseName(String id);

}
