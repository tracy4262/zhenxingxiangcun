package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryClimateType;


@MyBatisDao
public interface DictionaryClimateTypeDao extends CrudDao<DictionaryClimateType> {

    void updateClimateType(DictionaryClimateType dictionaryClimateType);

    void saveClimateType(DictionaryClimateType dictionaryClimateType);

    void deleteClimateType(String id);

}
