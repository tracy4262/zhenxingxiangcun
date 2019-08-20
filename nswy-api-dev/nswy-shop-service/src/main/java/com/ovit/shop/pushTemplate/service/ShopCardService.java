package com.ovit.shop.pushTemplate.service;



import java.util.List;
import java.util.Map;

public interface ShopCardService {


    Integer add(Map<String,Object> params);

    List<Map<String, Object>> list (Map<String,Object> params);

    void update(Map<String,Object> params);

    void delete(Map<String, Object> list);

    void clear(Map<String,Object> params);

    List<Map<String,Object>> product(Map<String,Object> params);

    List<Map<String,Object>> purchase(Map<String,Object> params);


    //ShopProductDTO findProduct(Integer commodityTypeInfoId);

}
