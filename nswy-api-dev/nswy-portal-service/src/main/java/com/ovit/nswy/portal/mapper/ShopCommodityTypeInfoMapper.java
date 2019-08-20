package com.ovit.nswy.portal.mapper;


import java.util.List;
import java.util.Map;

public interface ShopCommodityTypeInfoMapper {

    List<Map<String,Object>> findCommodityInfo(Map<String,Object> params);

    void insertCommodityInfo(Map<String,Object> params);

    String findMaxId();

    List<Map<String,Object>> findCommodityImage(Map<String,Object> params);
}
