package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.model.response.ShopOrderAddDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDetailDTO;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;
import java.util.List;
import java.util.Map;

public interface ShopOrderService {


    Integer add(ShopOrderAddDTO entity, String account);

    PageInfo<ShopOrderDTO> list(Map<String, Object> map,Map<String, Object> page,String account);

    Integer update(ShopOrderDetailDTO entity,String account);
    Integer updateState(String orderCode, OrderStatusType state, String account);
    ShopOrderDetailDTO selectOne(Integer id);
    ShopOrderDetailDTO selectOne(String code);
}
