package com.ovit.nswy.portal.service;

import java.util.List;
import java.util.Map;

/**
 * 上传商品营销基础信息
 */
public interface CurrencyCommodityService {

    /**
     * 获取通用商品列表信息
     * @param params
     * @return
     */
    List<Map<String,Object>> list(Map<String, Object> params);

    /**
     * 通过id查询通用商品的信息
     * @param params
     * @return
     */
    Map<String,Object> findCommodityById(Map<String, Object> params);

    /**
     * 通过id删除通用商品
     * @param params
     * @return
     */
    void deleteCommodityById(Map<String, Object> params);

    /**
     * 保存通用商品信息
     * @param commodityInfo
     */
    void insertCommodityInfo(Map<String, Object> commodityInfo);


}
