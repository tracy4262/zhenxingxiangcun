package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPartisan;


@MyBatisDao
public interface DictionaryPartisanDao extends CrudDao<DictionaryPartisan> {

    void updatePartisan(DictionaryPartisan dictionaryPartisan);

    void savePartisan(DictionaryPartisan dictionaryPartisan);

    void deletePartisan(String id);

}
