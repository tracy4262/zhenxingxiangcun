package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNotMetalMinerals;


@MyBatisDao
public interface DictionaryNotMetalMineralsDao extends CrudDao<DictionaryNotMetalMinerals> {

    void updateNotMetalMinerals(DictionaryNotMetalMinerals dictionaryNotMetalMinerals);

    void saveNotMetalMinerals(DictionaryNotMetalMinerals dictionaryNotMetalMinerals);

    void deleteNotMetalMinerals(String id);

}
