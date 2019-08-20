package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.CommodityInfo;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface CommodityInfoDao extends CrudDao<CommodityInfo> {
    List<Map<String,Object>> findCommodity();

    void deleteByPrimaryKey(Integer id);

    void saveCommodity(CommodityInfo commodityInfo);

    void updateCommodity(CommodityInfo commodityInfo);
}
