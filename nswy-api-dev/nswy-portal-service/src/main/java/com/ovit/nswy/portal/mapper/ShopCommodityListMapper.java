package com.ovit.nswy.portal.mapper;


import java.util.List;
import java.util.Map;

public interface ShopCommodityListMapper {

    List<Map<String,Object>> findCommodityAbstractDetail(Map<String, Object> params);

    List<Map<String,Object>> findCommodityTypeInfoDetail(Map<String, Object> params);

    List<Map<String,Object>> findCommoditySupplyInfoDetail(Map<String, Object> params);

    List<Map<String,Object>> findShopCommodityList(Map<String, Object> params);

    Map<String,Object> findCommodityTypeInfo(Map<String, Object> params);

    Map<String,Object> findCommodityInfo(Map<String, Object> params);

    Map<String,Object> findProductionInfo(Map<String, Object> params);

    Map<String,Object> findPlaceInfo(Map<String, Object> params);

    Map<String,Object> findQualificationInfo(Map<String, Object> params);

    Map<String,Object> findHonorInfo(Map<String, Object> params);

    Map<String,Object> findCustomContentInfo(Map<String, Object> params);

    Map<String,Object> findCommentCount(Map<String, Object> params);

    List<Map<String,Object>> findCommentList(Map<String, Object> params);

    List<Map<String,Object>> findMonthList(Map<String, Object> params);

    Map<String,Object> findLoginUser(Map<String, Object> params);

    Map<String,Object> findAvatar(Map<String, Object> params);

    void updateStorage(Map<String, Object> params);

    Map<String,Object> findProxyMode(Map<String, Object> params);




}
