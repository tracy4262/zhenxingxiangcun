package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface FullAppSettingDao extends CrudDao<FullAppSettingInfo> {



   void updateFullAppSetting(Map<String, Object> map);

   FullAppSettingInfo findByAppSetting(String appSettingId);





}
