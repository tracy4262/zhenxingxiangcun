package com.ovit.shop.pushTemplate.mapper;


import java.util.List;
import java.util.Map;

public interface ShopOrderOperateMapper  {

    List<Map<String,Object>> findList(Map params);

    Map<String,Object> findByOrderCode(Map<String,Object> params);

    void saveOrderOperate(Map<String,Object> params);

}