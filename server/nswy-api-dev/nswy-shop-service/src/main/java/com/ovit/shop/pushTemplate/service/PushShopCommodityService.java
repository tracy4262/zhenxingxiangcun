package com.ovit.shop.pushTemplate.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 商品列表信息
 */
public interface PushShopCommodityService {

    /**
     * 获取定价商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findPricing(Map<String, Object> params);

    /**
     * 获取定价商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findBidding(Map<String, Object> params);

    /**
     * 获取定价商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findGroupBuying(Map<String, Object> params);

    /**
     * 获取预售商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findPresale(Map<String, Object> params);

    /**
     * 获取面议商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findFace(Map<String, Object> params);

    /**
     * 获取可追溯商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findRetrospect(Map<String, Object> params);

    /**
     * 查询全部商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findProduct(Map<String, Object> params);

    /**
     *后台-内容控制-商品
     * @param params
     * @return
     */
    List<Map<String,Object>> findProductList(Map<String, Object> params);

    /**
     * 更新是否显示到首页、导航、商城
     * @param params
     * @return
     */
    void updateHomeDisplay(Map<String, Object> params);
}
