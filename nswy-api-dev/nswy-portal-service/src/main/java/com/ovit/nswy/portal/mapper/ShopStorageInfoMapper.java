package com.ovit.nswy.portal.mapper;


import java.util.List;
import java.util.Map;

public interface ShopStorageInfoMapper {

    List<Map<String,Object>> getShopStorageList(Map<String, Object> queryInfo);

    void batchUpdateStorage(List<Map<String, Object>> params);

    void updateStoragePrice(Map<String, Object> params);

    void updateAuditStatus(Map<String, Object> params);

    void updateStorageStatus(Map<String, Object> params);

    //删除商品发布第一步表数据
    void delTypeInfo(List<Map<String, Object>> params);

    //删除商品发布第二步表数据
    void delCommodityInfo(List<Map<String, Object>> params);

    void delCommodityProduction(List<Map<String, Object>> params);

    void delPlaceInfo(List<Map<String, Object>> params);

    void delCommodityQualification(List<Map<String, Object>> params);

    void delCommodityHonor(List<Map<String, Object>> params);

    void delQualitySafety(List<Map<String, Object>> params);

    //删除商品发布第三步表数据
    void delSupplyInfo(List<Map<String, Object>> params);

    void delAssuranceInfo(List<Map<String, Object>> params);

    void delPriceInfo(List<Map<String, Object>> params);

    void delDeliveryInfo(List<Map<String, Object>> params);

    void delAfterInfo(List<Map<String, Object>> params);

    //删除商品发布第四步表数据
    void delTraceableInfo(List<Map<String, Object>> params);

    //删除商品发布第五步表数据
    void delCommitmentInfo(List<Map<String, Object>> params);


}
