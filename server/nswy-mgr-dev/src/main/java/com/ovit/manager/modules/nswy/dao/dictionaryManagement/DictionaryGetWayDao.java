package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryGetWay;


@MyBatisDao
public interface DictionaryGetWayDao extends CrudDao<DictionaryGetWay> {

    void updateGetWay(DictionaryGetWay dictionaryGetWay);

    void saveGetWay(DictionaryGetWay dictionaryGetWay);

    void deleteGetWay(String id);

}
