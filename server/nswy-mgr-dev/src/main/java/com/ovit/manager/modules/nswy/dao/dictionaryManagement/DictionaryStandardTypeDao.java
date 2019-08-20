package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryStandardType;


@MyBatisDao
public interface DictionaryStandardTypeDao extends CrudDao<DictionaryStandardType> {

    void updateStandardType(DictionaryStandardType dictionaryStandardType);

    void saveStandardType(DictionaryStandardType dictionaryStandardType);

    void deleteStandardType(String id);

}
