package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.RealName;

import java.util.List;



@MyBatisDao
public interface RealNameDao extends CrudDao<RealName> {
    List<RealName> findByUserId(String userId);

    List<RealName> findById(String realId);

    void saveRealName(RealName realName);

    void deleteRealName(String userId);

    void updateRealName(String userId);
}
