package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyServiceInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ShopCurrencyServiceDao extends CrudDao<ShopCurrencyServiceInfo> {
    void adopt(Map<String, Object> params);

    ShopCurrencyServiceInfo findList(Map<String, Object> params);

    List<ShopCurrencyServiceInfo> findListCache();

    Map<String,Object> findShopCurrencyService(Map<String,Object> params);

    Map<String,Object> findCurrencyServiceList(Map<String, Object> params);

    void updateCurrencyServiceInfo(Map<String, Object> params);

    void saveCurrencyServiceInfo(Map<String, Object> params);





}
