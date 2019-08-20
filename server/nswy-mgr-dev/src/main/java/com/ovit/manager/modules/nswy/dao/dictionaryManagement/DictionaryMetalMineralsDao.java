package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMetalMinerals;


@MyBatisDao
public interface DictionaryMetalMineralsDao extends CrudDao<DictionaryMetalMinerals> {

    void updateMetalMinerals(DictionaryMetalMinerals dictionaryMetalMinerals);

    void saveMetalMinerals(DictionaryMetalMinerals dictionaryMetalMinerals);

    void deleteMetalMinerals(String id);

}
