package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUserType;


@MyBatisDao
public interface DictionaryUserTypeDao extends CrudDao<DictionaryUserType> {

    void updateUserType(DictionaryUserType dictionaryUserType);

    void saveUserType(DictionaryUserType dictionaryUserType);

    void deleteUserType(String id);

}
