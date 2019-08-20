package com.ovit.shop.pushTemplate.service;


import com.ovit.shop.util.PageList;

import java.util.List;
import java.util.Map;

public interface ShopOrderService {


    void add(Map<String,Object> params);
    PageList list(Map<String, Object> map, Map<String, Object> page, String account);



    Map<String,Object> selectOneDetail(Map<String,Object> params);

    void updateState(Map<String,Object> params);

    /*

    Integer update(ShopOrderDetailDTO entity, String account);
    Integer updateState(String orderCode, OrderStatusType state, String account);

    ShopOrderDetailDTO selectOne(String code);*/
}
