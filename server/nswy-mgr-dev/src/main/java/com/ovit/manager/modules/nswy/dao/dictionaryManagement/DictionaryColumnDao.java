package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryColumn;


@MyBatisDao
public interface DictionaryColumnDao extends CrudDao<DictionaryColumn> {

    void updateColumn(DictionaryColumn dictionaryColumn);

    void saveColumn(DictionaryColumn dictionaryColumn);

    void deleteColumn(String id);

}
