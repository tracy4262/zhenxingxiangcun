package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;

import java.util.List;
import java.util.Map;

public interface ShopOrderOperateService {


    Integer add(ShopOrderOperate entity);


    List<ShopOrderOperateDTO> list(Map<String, Object> map);

    Integer update(ShopOrderOperate entity);

}
