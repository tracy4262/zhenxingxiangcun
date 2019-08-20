package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopCard;
import com.ovit.nswy.portal.model.response.ShopCardDTO;
import com.ovit.nswy.portal.model.response.ShopProductDTO;

import java.util.List;

public interface ShopCardService {


    Integer add(ShopCard entity);

    List<ShopCardDTO> list(String account);

    Integer delete(List<Object> idList);

    Integer clear(String account);

    Integer update(List<ShopCard> entity);

    ShopProductDTO findProduct(Integer commodityTypeInfoId);

}
