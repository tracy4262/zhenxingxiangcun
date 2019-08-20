package com.ovit.shop.pushTemplate.service;



import java.util.List;
import java.util.Map;

public interface ShopOrderOperateService {


    void add(Map<String,Object> params);

    Map<String,Object> findById(Map<String,Object> params);

    /*

    List<ShopOrderOperateDTO> list(Map<String, Object> map);

    Integer update(ShopOrderOperate entity);*/

}
