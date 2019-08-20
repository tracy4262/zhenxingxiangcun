package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.GeneralServiceInfo;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface GeneralServiceInfoDao extends CrudDao<GeneralServiceInfo> {
    List<Map<String,Object>> findGeneralService();

    void updateGeneralService(GeneralServiceInfo generalServiceInfo);

    void saveGeneralService(GeneralServiceInfo generalServiceInfo);

    void deleteService(Integer id);



}
