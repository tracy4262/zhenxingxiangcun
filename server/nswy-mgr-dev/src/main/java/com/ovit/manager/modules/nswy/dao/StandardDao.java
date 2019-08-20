package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.StandardInfo;

import java.util.Map;

@MyBatisDao
public interface StandardDao extends CrudDao<StandardInfo> {
    void deleteStandard(Integer standardId);

    void standardRefuse(Integer standardId);

    void standardAdopt(Integer standardId);

    void standardUpdateHome(Map<String, Object> map);

    void standardUpdateNavDisplay(Map<String, Object> map);

    void standardSetOrderNum(Map<String, Object> map);
}
