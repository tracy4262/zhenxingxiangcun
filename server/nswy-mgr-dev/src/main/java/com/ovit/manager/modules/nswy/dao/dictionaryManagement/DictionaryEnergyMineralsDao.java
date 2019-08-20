package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnergyMinerals;


@MyBatisDao
public interface DictionaryEnergyMineralsDao extends CrudDao<DictionaryEnergyMinerals> {

    void updateEnergyMinerals(DictionaryEnergyMinerals dictionaryEnergyMinerals);

    void saveEnergyMinerals(DictionaryEnergyMinerals dictionaryEnergyMinerals);

    void deleteEnergyMinerals(String id);

}
