package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.entity.NswyServiceInfo;

import java.util.Map;

@MyBatisDao
public interface NswyServiceDao extends CrudDao<NswyServiceInfo> {


    void serviceDel(Map<String, Object> map);

    void serviceUpdateNavDisplay(Map<String, Object> map);

    void serviceSetOrderNum(Map<String, Object> map);

    void serviceUpdateHome(Map<String, Object> map);
}
