package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRightType;


@MyBatisDao
public interface DictionaryRightTypeDao extends CrudDao<DictionaryRightType> {

    void updateRightType(DictionaryRightType dictionaryRightType);

    void saveRightType(DictionaryRightType dictionaryRightType);

    void deleteRightType(String id);

}
