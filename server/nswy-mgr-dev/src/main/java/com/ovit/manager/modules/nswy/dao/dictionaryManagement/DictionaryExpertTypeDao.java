package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryExpertType;


@MyBatisDao
public interface DictionaryExpertTypeDao extends CrudDao<DictionaryExpertType> {

    void updateExpertType(DictionaryExpertType dictionaryExpertType);

    void saveExpertType(DictionaryExpertType dictionaryExpertType);

    void deleteExpertType(String id);

}
