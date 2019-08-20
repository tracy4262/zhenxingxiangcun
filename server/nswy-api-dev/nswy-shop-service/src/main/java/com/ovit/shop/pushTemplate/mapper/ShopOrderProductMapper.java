package com.ovit.shop.pushTemplate.mapper;


import java.util.List;
import java.util.Map;


public interface ShopOrderProductMapper {
    List<Map<String,Object>> findByOrderCode(String code);
    List<Map<String,Object>> selectExtendByOrderCode(Map params);

    List<Map<String,Object>> selectByOrderCodeBuy(Map params);


    void saveShopOrderProduct(Map<String,Object> params);
}