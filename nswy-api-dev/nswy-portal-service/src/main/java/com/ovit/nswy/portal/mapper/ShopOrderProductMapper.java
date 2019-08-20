package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopOrderProduct;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import com.ovit.nswy.portal.model.response.ShopOrderProductDTO;
import org.springframework.stereotype.Repository;
import com.ovit.nswy.frame.base.BaseMapper;

import java.util.List;
import java.util.Map;

@Repository
    public interface ShopOrderProductMapper  extends BaseMapper<ShopOrderProduct> {
    List<ShopOrderProduct> findByOrderCode(String code);
    List<ShopOrderProductDTO> selectExtendByOrderCode(Map params);
}