package com.ovit.shop.pushTemplate.service;

import java.util.List;
import java.util.Map;

public interface CommodityMarketingService {

    /**
     * 拉取完善信息中的营业网点信息
     * @param params
     * @return
     */
    List<Map<String,Object>> networkStationList(Map<String,Object> params);

}
