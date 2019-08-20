package com.ovit.shop.pushTemplate.mapper;

import java.util.List;
import java.util.Map;


public interface ShopCardMapper  {


    Map<String, Object> findList(Map<String, Object> param);

    List<Map<String, Object>> findListByAccount(Map<String, Object> param);

    List<Map<String, Object>> findListByAccountGroup(Map<String, Object> param);




    Integer findProductCount(Integer pid);

    void saveShopCard(Map<String, Object> param);

    int findCount(Map<String, Object> param);

    void updateShopCard(Map<String, Object> param);

    void deleteShopCard(Map<String, Object> param);

    void deleteShopCardByAccount(Map<String, Object> param);

    Map<String, Object> findAvatar(Map<String, Object> param);





}