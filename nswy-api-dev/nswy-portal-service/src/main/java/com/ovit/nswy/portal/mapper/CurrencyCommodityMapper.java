package com.ovit.nswy.portal.mapper;


import java.util.Map;

public interface CurrencyCommodityMapper {

    Map<String,Object> findCommodityById(Map<String, Object> params);

    void deleteCommodityById(Map<String, Object> params);

    void insertCommodityInfo(Map<String, Object> commodityInfo);

    Map<String,Object> findMallClass(Map<String, Object> params);


}
