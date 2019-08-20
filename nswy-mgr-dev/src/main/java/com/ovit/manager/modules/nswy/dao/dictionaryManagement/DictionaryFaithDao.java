package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFaith;


@MyBatisDao
public interface DictionaryFaithDao extends CrudDao<DictionaryFaith> {

    void updateFaith(DictionaryFaith dictionaryFaith);

    void saveFaith(DictionaryFaith dictionaryFaith);

    void deleteFaith(String id);

}
