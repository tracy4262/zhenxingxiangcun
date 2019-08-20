package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNationality;


@MyBatisDao
public interface DictionaryNationalityDao extends CrudDao<DictionaryNationality> {

    void updateNationality(DictionaryNationality dictionaryNationality);

    void saveNationality(DictionaryNationality dictionaryNationality);

    void deleteNationality(String id);

}
