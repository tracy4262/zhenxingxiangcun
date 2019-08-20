package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyType;


@MyBatisDao
public interface DictionaryAgencyTypeDao extends CrudDao<DictionaryAgencyType> {

    void updateAgencyType(DictionaryAgencyType dictionaryAgencyType);

    void saveAgencyType(DictionaryAgencyType dictionaryAgencyType);

    void deleteAgencyType(String id);

}
