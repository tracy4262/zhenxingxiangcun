package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyLevel;


@MyBatisDao
public interface DictionaryAgencyLevelDao extends CrudDao<DictionaryAgencyLevel> {

    void updateAgencyLevel(DictionaryAgencyLevel dictionaryAgencyLevel);

    void saveAgencyLevel(DictionaryAgencyLevel dictionaryAgencyLevel);

    void deleteAgencyLevel(String id);

}
