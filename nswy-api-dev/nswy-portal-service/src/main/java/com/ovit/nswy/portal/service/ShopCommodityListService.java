package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ShopCommodityListService {


    PageInfo<Map<String,Object>> findShopCommodityList(Map<String, Object> params);

    Map<String,Object> findCommodityDetail(Map<String,Object> params);

    Map<String,Object> findSupplyInfo(Map<String,Object> params);

    PageInfo<Map<String,Object>> findComment(Map<String,Object> params);

    Map<String,Object> findCommentNum(Map<String,Object> params);

    PageInfo<Map<String,Object>> findMonthList(Map<String,Object> params);

    Map<String,Object> findLoginUser(Map<String,Object> params);
}
