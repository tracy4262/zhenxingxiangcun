package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTown;


@MyBatisDao
public interface DictionaryTownDao extends CrudDao<DictionaryTown> {

    void updateTown(DictionaryTown dictionaryTown);

    void saveTown(DictionaryTown dictionaryTown);

    void deleteTown(String id);

}
