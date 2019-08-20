package com.ovit.nswy.portal.service;

import java.util.Map;

/**
 * 上传商品营销基础信息
 */
public interface UpMarketingBasicInfoService {

    /**
     * 保存商品销售信息
     * @param marketingInfo
     */
    void insertCommodityMarketingInfo(Map<String, Object> marketingInfo);

    /**
     * 获取商品销售信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getCommodityMarketingInfo(Map<String, Object> queryInfo);

    /**
     * 保存商品质保信息
     * @param assuranceInfo
     */
    void insertAssuranceInfo(Map<String, Object> assuranceInfo);

    /**
     * 获取商品质保信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getAssuranceInfo(Map<String, Object> queryInfo);

    /**
     * 保存商品定价信息
     * @param pricingInfo
     */
    void insertPricingInfo(Map<String, Object> pricingInfo);

    /**
     * 获取商品定价信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getPricingInfo(Map<String, Object> queryInfo);

    /**
     * 保存产品发货信息
     * @param deliveryInfo
     */
    void insertDeliveryInfo(Map<String, Object> deliveryInfo);

    /**
     * 获取产品发货信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getDeliveryInfo(Map<String, Object> queryInfo);

    /**
     * 保存产品售后信息
     * @param afterSalesInfo
     */
    void insertAfterSalesInfo(Map<String, Object> afterSalesInfo);

    /**
     * 获取产品售后信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getAfterSalesInfo(Map<String, Object> queryInfo);

    /**
     * 第四步---保存产品追溯信息
     * @param traceableInfo
     */
    void insertTraceableInfo(Map<String, Object> traceableInfo);

    /**
     * 第四步---获取产品追溯信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getTraceableInfo(Map<String, Object> queryInfo);

    /**
     * 第五步---保存商品承诺信息
     * @param commitmentInfo
     */
    void insertCommitmentInfo(Map<String, Object> commitmentInfo);

    /**
     * 第五步---获取商品承诺信息
     * @param queryInfo
     * @return
     */
    Map<String,Object> getCommitmentInfo(Map<String, Object> queryInfo);

}
