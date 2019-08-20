package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRoughingWay;


@MyBatisDao
public interface DictionaryRoughingWayDao extends CrudDao<DictionaryRoughingWay> {

    void updateRoughingWay(DictionaryRoughingWay dictionaryRoughingWay);

    void saveRoughingWay(DictionaryRoughingWay dictionaryRoughingWay);

    void deleteRoughingWay(String id);

}
