package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.entity.ShopCommityInfo;

import java.util.Map;

@MyBatisDao
public interface ShopCommodityDao extends CrudDao<ShopCommityInfo> {

    void adopt(Map<String,Object> params);

    void updateHome(Map<String, Object> map);

    void updateNavDisplay(Map<String, Object> map);

    void updateShopDisplay(Map<String, Object> map);

    void updateOrderNum(Map<String, Object> map);


}
