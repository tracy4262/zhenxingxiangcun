package com.ovit.nswy.portal.service;

import java.util.Map;

/**
 * 上传商品基础信息
 */
public interface UpCommodityBasicInfoService {

    /**
     * 保存商品信息
     * @param commodityInfo
     */
    void insertCommodityInfo(Map<String,Object> commodityInfo);

    /**
     * 获取商品信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getCommodityInfo(Map<String,Object> queryInfo);

    /**
     * 保存商品生产信息
     * @param productionInfo
     */
    void insertProductionInfo(Map<String,Object> productionInfo);

    /**
     * 获取商品生产信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getProductionInfo(Map<String,Object> queryInfo);

    /**
     * 保存商品产地信息
     * @param placeInfo
     */
    void insertPlaceInfo(Map<String,Object> placeInfo);

    /**
     * 获取商品产地信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getPlaceInfo(Map<String,Object> queryInfo);

    /**
     * 保存商品资质信息
     * @param qualificationInfo
     */
    void insertQualificationInfo(Map<String,Object> qualificationInfo);

    /**
     * 获取商品资质信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getQualificationInfo(Map<String,Object> queryInfo);

    /**
     * 保存商品荣誉信息
     * @param honorInfo
     */
    void insertHonorInfo(Map<String,Object> honorInfo);

    /**
     * 获取商品荣誉信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getHonorInfo(Map<String,Object> queryInfo);

    /**
     * 保存自定义表单内容
     * @param customInfo
     */
    void insertCustomContentInfo(Map<String,Object> customInfo);

    /**
     * 获取自定义表单内容
     * @param queryInfo
     * @return
     */
    Map<String, Object> getCustomContentInfo(Map<String,Object> queryInfo);

    Map<String,Object> findSafety(Map<String, Object> queryInfo);

    String getDiseaseList(String diseaseId);

    String getPestList(String pestId);

}
