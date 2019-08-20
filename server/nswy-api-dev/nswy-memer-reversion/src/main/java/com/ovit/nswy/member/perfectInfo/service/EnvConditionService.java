package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface EnvConditionService {

    /**
     * 空气质量状况判断标准表
     * @return
     */
    List<Map<String,Object>> airQuaStandard();

    /**
     * 通过用户名查询用户的空气质量信息
     * @param params
     * @return
     */
    Map<String,Object> findAirQuaByAccount(Map<String, Object> params);

    Map<String, Object> findAirQuaByAccountList(Map<String, Object> params);

    /**
     * 修改空气质量信息的属性名称
     * @param params
     */
    void updateAirQuaName(Map<String,Object> params);

    /**
     * 修改用户的空气质量信息
     * @param params
     * @return
     */
    void modifyAirQuaByAccount(Map<String, Object> params);

    /**
     * 新增用户的空气质量信息
     * @param params
     * @return
     */
    void insertAirQuaByAccount(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地表水质量信息
     * @param params
     * @return
     */
    Map<String,Object> findSurfaceWaterQua(Map<String, Object> params);

    Map<String,Object> findSurfaceWaterQuaList(Map<String, Object> params);

    /**
     * 修改用户的地表水质量信息
     * @param params
     * @return
     */
    void modifySurfaceWaterQua(Map<String, Object> params);

    /**
     * 新增用户的地表水质量信息
     * @param params
     * @return
     */
    void insertSurfaceWaterQua(Map<String, Object> params);

    /**
     * 水质标准数据
     * @return
     */
    List<Map<String,Object>> findWaterStandard();

    /**
     * 环境质量标准数据
     * @return
     */
    List<Map<String,Object>> findEnvStandard();

    /**
     * 通过用户名查询用户的生态环境质量信息
     * @param params
     * @return
     */
    Map<String,Object> findEcologyEnvQua(Map<String, Object> params);


    Map<String, Object> findEcologyEnvQuaList(Map<String, Object> params);

    /**
     * 修改用户的生态环境质量信息
     * @param params
     * @return
     */
    void modifyEcologyEnvQua(Map<String, Object> params);

    /**
     * 新增用户的生态环境质量信息
     * @param params
     * @return
     */
    void insertEcologyEnvQua(Map<String, Object> params);

}
