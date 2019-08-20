package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface EcoSocialDevelopService {


    /**
     * 通过用户名查询用户的产业信息
     * @param params
     * @return
     */
    Map<String,Object> findIndustry(Map<String, Object> params);

    /**
     * 修改用户的产业信息
     * @param params
     * @return
     */
    void saveIndustry(Map<String, Object> params);


    /**
     * 删除产业信息
     * @param params
     */
    void deleteIndustry(Map<String, Object> params);

    /**
     * 通过用户名查询用户的种养信息
     * @param params
     * @return
     */
    Map<String,Object> findBreed(Map<String, Object> params);

    /**
     * 修改用户的种养信息
     * @param params
     * @return
     */
    void saveBreed(Map<String, Object> params);


    /**
     * 删除种养信息
     * @param params
     */
    void deleteBreed(Map<String, Object> params);

    /**
     * 通过用户名查询用户的农产品信息
     * @param params
     * @return
     */
    Map<String,Object> findFarmProduct(Map<String, Object> params);

    /**
     * 修改用户的农产品信息
     * @param params
     * @return
     */
    void saveFarmProduct(Map<String, Object> params);


    /**
     * 删除农产品信息
     * @param params
     */
    void deleteFarmProduct(Map<String, Object> params);

    /**
     * 通过用户名查询用户的工业产品信息
     * @param params
     * @return
     */
    Map<String,Object> findTechnicalProduct(Map<String, Object> params);

    /**
     * 修改用户的工业产品信息
     * @param params
     * @return
     */
    void saveTechnicalProduct(Map<String, Object> params);


    /**
     * 删除工业产品信息
     * @param params
     */
    void deleteTechnicalProduct(Map<String, Object> params);

    /**
     * 通过用户名查询用户的服务产品信息
     * @param params
     * @return
     */
    Map<String,Object> findServiceProduct(Map<String, Object> params);

    /**
     * 修改用户的服务产品信息
     * @param params
     * @return
     */
    void saveServiceProduct(Map<String, Object> params);


    /**
     * 删除服务产品信息
     * @param params
     */
    void deleteServiceProduct(Map<String, Object> params);

    /**
     * 获取国民经济行业分类信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findEco(Map<String, Object> params);

    /**
     * 根据上级获取下级数据
     * @param parentId
     * @return
     */
    List<Map<String,Object>> findByParentId(String parentId);


}
