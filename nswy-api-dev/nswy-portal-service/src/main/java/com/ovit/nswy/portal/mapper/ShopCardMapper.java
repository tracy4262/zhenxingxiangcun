package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.model.ShopCard;
import com.ovit.nswy.frame.base.BaseMapper;
import com.ovit.nswy.portal.model.response.ShopCardDTO;
import com.ovit.nswy.portal.model.response.ShopProductDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShopCardMapper extends BaseMapper<ShopCard> {

    List<ShopCardDTO> findAllList(String account);
    ShopCard findList(Map<String, Object> param);
    Integer findProductCount(Integer pid);
    ShopProductDTO findProductById(Integer commodityTypeInfoId);
}