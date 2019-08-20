package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.ShopOrderProduct;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import com.ovit.nswy.portal.model.response.ShopOrderProductDTO;

import java.util.List;
import java.util.Map;

public interface ShopOrderProductService {

    List<ShopOrderProduct> findByOrder(String code);

}
