package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionarySpecialPopulation;


@MyBatisDao
public interface DictionarySpecialPopulationDao extends CrudDao<DictionarySpecialPopulation> {

    void updateSpecialPopulation(DictionarySpecialPopulation dictionarySpecialPopulation);

    void saveSpecialPopulation(DictionarySpecialPopulation dictionarySpecialPopulation);

    void deleteSpecialPopulation(String id);

}
