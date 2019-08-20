package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnvironment;


@MyBatisDao
public interface DictionaryEnvironmentDao extends CrudDao<DictionaryEnvironment> {

    void updateEnvironment(DictionaryEnvironment dictionaryEnvironment);

    void saveEnvironment(DictionaryEnvironment dictionaryEnvironment);

    void deleteEnvironment(String id);

}
