package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface CultureSightService {


    /**
     * 通过用户名查询用户的风景名胜设施信息, 初始化数据
     * @param params
     * @return
     */
    Map<String,Object> findSceneryByAccount(Map<String, Object> params);

    /**
     * 通过用户名查询用户的风景名胜设施信息
     * @param params
     * @return
     */
    Map<String,Object> findFamousScenery(Map<String, Object> params);

    /**
     * 修改属性名称
     * @param params
     */
    void updatePropertyInfo(Map<String,Object> params);

    /**
     * 修改用户的风景名胜设施信息
     * @param params
     * @return
     */
    void saveScenery(Map<String, Object> params);

    /**
     * 修改用户的风景名胜设施信息
     * @param params
     * @return
     */
    void saveCultural(Map<String, Object> params);

    /**
     * 通过用户名查询用户的文化遗产设施信息
     * @param params
     * @return
     */
    Map<String,Object> findCulturalHeritage(Map<String, Object> params);

    /**
     * 修改文化遗产设施信息的属性名称
     * @param params
     */
    void updateCulturalName(Map<String,Object> params);

    /**
     * 保存文字预览信息
     * @param params
     */
    void saveTextPreview(Map<String, Object> params);

    /**
     * 删除风景名胜设施信息
     * @param params
     */
    void deleteScenery(Map<String, Object> params);

    /**
     * 删除-文化遗产设施信息
     * @param params
     */
    void deleteCultural(Map<String, Object> params);

    /**
     * 查询属性名称
     * @param params
     * @return
     */
    Map<String,Object> findPropertyInfo(Map<String, Object> params);



}
