package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ShopCurrencyCommodityDao extends CrudDao<ShopCurrencyCommityInfo> {
    void adopt(Map<String,Object> params);

    ShopCurrencyCommityInfo findList(Map<String,Object> params);

    List<ShopCurrencyCommityInfo> findListCache();

    Map<String,Object> findShopCurrencyCommodity(Map<String,Object> params);

    void saveCurrencyCommodityInfo(Map<String,Object> params);

    Map<String,Object> findCurrencyCommodityList(Map<String,Object> params);

    void updateCurrencyCommodityInfo(Map<String,Object> params);




}
