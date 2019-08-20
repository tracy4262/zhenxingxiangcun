package com.ovit.nswy.portal.mapper;


import java.util.Map;

public interface UpMarketingBasicInfoMapper {

    void insertCommodityMarketingInfo(Map<String, Object> marketingInfo);

    Map<String,Object> getCommodityMarketingInfo(Map<String, Object> queryInfo);

    void insertAssuranceInfo(Map<String, Object> assuranceInfo);

    Map<String,Object> getAssuranceInfo(Map<String, Object> queryInfo);

    void insertPricingInfo(Map<String, Object> pricingInfo);

    Map<String,Object> getPricingInfo(Map<String, Object> queryInfo);

    void insertDeliveryInfo(Map<String, Object> deliveryInfo);

    Map<String,Object> getDeliveryInfo(Map<String, Object> queryInfo);

    void insertAfterSalesInfo(Map<String, Object> afterSalesInfo);

    Map<String,Object> getAfterSalesInfo(Map<String, Object> queryInfo);

    void insertTraceableInfo(Map<String, Object> traceableInfo);

    Map<String,Object> getTraceableInfo(Map<String, Object> queryInfo);

    Map<String,Object> findTraceableInfo(Map<String, Object> queryInfo);

    void insertCommitmentInfo(Map<String, Object> commitmentInfo);

    Map<String,Object> getCommitmentInfo(Map<String, Object> queryInfo);

}
