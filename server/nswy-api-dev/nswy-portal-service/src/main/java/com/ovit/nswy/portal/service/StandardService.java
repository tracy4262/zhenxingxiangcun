package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface StandardService {
    PageInfo<Map<String,Object>> queryStandardList(Map<String, Object> params);

    /**
     * 查询农药残留标准
     * @return
     */
    List<Map<String, Object>> findResidueStandard(Map<String, Object> params);

    /**
     * 查询通用商品名库
     * @param params
     * @return
     */
    List<Map<String, Object>> findCurrencyCommodity(Map<String, Object> params);

    /**
     * 查询单位标准库
     * @param params
     * @return
     */
    List<Map<String, Object>> findUnitStandard(String params);

    List<Map<String, Object>> findUnit(Map<String, Object> params);

    /**
     * 查询产品分类数据
     * @param params
     * @return
     */
    List<Map<String, Object>> findProductClassification(Map<String, Object> params);

    /**
     * 查询商城首页九大分类数据
     * @return
     */
    List<Map<String, Object>> findMallClassification();

    /**
     * 查看通用商品/服务是否重名
     * @param params
     * @return
     */
    Boolean isExist(Map<String,Object> params);

}
