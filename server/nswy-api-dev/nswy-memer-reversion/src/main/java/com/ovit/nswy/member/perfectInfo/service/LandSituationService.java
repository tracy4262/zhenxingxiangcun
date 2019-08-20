package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface LandSituationService {


    /**
     * 通过用户名查询用户的土地利用现状信息
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 修改用户的土地利用现状信息
     * @param params
     * @return
     */
    void save(Map<String, Object> params);


    /**
     * 删除土地利用现状信息
     * @param params
     */
    void delete(Map<String, Object> params);

    /**
     * 土地利用现状分类字典
     * @param params
     * @return
     */
    List<Map<String,Object>> dict(Map<String, Object> params);


}
