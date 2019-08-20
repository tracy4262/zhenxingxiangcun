package com.ovit.shop.pushTemplate.service.impl;

import com.ovit.shop.pushTemplate.mapper.CommodityMarketingMapper;
import com.ovit.shop.pushTemplate.service.CommodityMarketingService;
import com.ovit.shop.util.ConvertDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommodityMarketingServiceImpl implements CommodityMarketingService {

    @Autowired
    private CommodityMarketingMapper commodityMarketingMapper;

    @Override
    public List<Map<String, Object>> networkStationList(Map<String, Object> params) {
        //type: 1对应marketing_commodity_delivery_info, 2对应marketing_commodity_after_info
        commodityMarketingMapper.getYearIdAndTemplateId(params);
        List<Map<String, Object>> list = commodityMarketingMapper.networkStationList(params);
        for (Map<String,Object> map : list) {
            ConvertDataUtil.merge(map, "networkType");
            //map.put("outletsId", MapUtils.getString(map, "outletsId"));
        }
        return list;
    }

}
