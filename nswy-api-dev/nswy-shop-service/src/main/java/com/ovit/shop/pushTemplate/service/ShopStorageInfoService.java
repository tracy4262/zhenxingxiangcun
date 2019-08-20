package com.ovit.shop.pushTemplate.service;

import java.util.List;
import java.util.Map;

/**
 * 上传商品营销基础信息
 */
public interface ShopStorageInfoService {

    /**
     * 获取商品货架信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getShopStorageList(Map<String, Object> params);

    /**
     * 批量更新商品库存
     * @param params
     * @return
     */
    void batchUpdateStorage(List<Map<String, Object>> params);

    /**
     * 更新货架商品价格
     * @param params
     * @return
     */
    void updateStoragePrice(Map<String, Object> params);

    /**
     * 批量永久删除商品
     * @param params
     * @return
     */
    void batchDeleteCommodity(List<Map<String, Object>> params);


}
