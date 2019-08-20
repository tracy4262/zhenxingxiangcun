package com.ovit.nswy.portal.mapper;


import java.util.Map;

public interface UpCommodityBasicInfoMapper {

    void insertCommodityInfo(Map<String, Object> commodityInfo);

    Map<String,Object> getCommodityInfo(Map<String,Object> queryInfo);

    void insertProductionInfo(Map<String,Object> productionInfo);

    Map<String,Object> getProductionInfo(Map<String,Object> queryInfo);

    void insertPlaceInfo(Map<String,Object> placeInfo);

    Map<String,Object> getPlaceInfo(Map<String,Object> queryInfo);

    void insertQualificationInfo(Map<String,Object> qualificationInfo);

    Map<String,Object> getQualificationInfo(Map<String,Object> queryInfo);

    void insertHonorInfo(Map<String,Object> honorInfo);

    Map<String,Object> getHonorInfo(Map<String,Object> queryInfo);

    void insertCustomContentInfo(Map<String,Object> customInfo);

    Map<String, Object> getCustomContentInfo(Map<String,Object> queryInfo);

    Map<String, Object> findSafety(Map<String,Object> queryInfo);


}
