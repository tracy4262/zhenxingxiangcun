/*
package com.ovit.manager.modules.nswy.dao;*/
package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ServiceTypeInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ServiceTypeDao extends CrudDao<ServiceTypeInfo> {
    List<Map<String, Object>> findServiceType();

    void updateServiceType(ServiceTypeInfo serviceTypeInfo);

    void saveServiceType(ServiceTypeInfo serviceTypeInfo);

    void deleteServiceType(String id);
}


