package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface UnitDao extends CrudDao<UnitInfo> {

   List<Map<String,Object>> findUnitType();

   void updateUnit(UnitInfo unitInfo);

   void saveUnit(UnitInfo unitInfo);

   void deleteUnit(Integer unitId);






}
