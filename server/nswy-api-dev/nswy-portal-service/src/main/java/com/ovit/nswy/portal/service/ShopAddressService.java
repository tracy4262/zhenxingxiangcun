package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.model.ShopAddress;

import java.util.Map;
import java.util.List;

public interface ShopAddressService {


    Integer add(ShopAddress entity);


    List<ShopAddress> list(String account,Integer order);

    Integer delete(Integer id);

    Integer update(ShopAddress entity);

    Integer updateDefault(ShopAddress entity);

    ShopAddress selectOne(Integer id);

    ShopAddress selectDefault(String account);
}
