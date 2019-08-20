package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.mapper.ShopCommodityTypeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface ShopCommodityTypeInfoService {


    List<Map<String,Object>> findCommodityInfo(Map<String,Object> params);

    String insertCommodityInfo(Map<String,Object> params);

    List<Map<String,Object>> findCommodityImage(Map<String,Object> params);

    String getSpecies(String name);

}
