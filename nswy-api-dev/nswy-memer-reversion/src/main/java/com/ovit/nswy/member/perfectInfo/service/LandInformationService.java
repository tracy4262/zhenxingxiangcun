package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface LandInformationService {


    /**
     * 更新农科院地块数据到平台
     * @param params
     */
    void updateNkyLand(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块信息
     * @param params
     * @return
     */
    Map<String,Object> findLandInfo(Map<String, Object> params);

    /**
     * 修改用户地块信息
     * @param params
     */
    void updateLandInfo(Map<String, Object> params);

    /**
     * 修改用户地块土壤含量信息
     * @param params
     */
    void updateSoilContent(Map<String, Object> params);

    /**
     * 修改用户地块土壤质量信息
     * @param params
     */
    void updateSoilQuality(Map<String, Object> params);

    /**
     * 修改用户地块水质信息
     * @param params
     */
    void updateWaterQuality(Map<String, Object> params);

    /**
     * 删除地块信息
     * @param params
     */
    void deleteLandInfo(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块土壤含量信息
     * @param params
     * @return
     */
    Map<String,Object> findSoilContent(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块土壤质量信息
     * @param params
     * @return
     */
    Map<String,Object> findSoilQuality(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块水质信息
     * @param params
     * @return
     */
    Map<String,Object> findWaterQuality(Map<String, Object> params);

    /**
     * 通过用户名查询用户的土地利用现状信息
     * @param params
     * @return
     */
    Map<String,Object> findLandUse(Map<String, Object> params);

    /**
     * 修改用户的土地利用现状信息
     * @param params
     * @return
     */
    void saveLandUse(Map<String, Object> params);

    /**
     * 删除土地利用现状信息
     * @param params
     */
    void deleteLandUse(Map<String, Object> params);

}
