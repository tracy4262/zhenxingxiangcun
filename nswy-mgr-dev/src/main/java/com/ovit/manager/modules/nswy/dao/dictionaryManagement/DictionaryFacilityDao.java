package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFacility;


@MyBatisDao
public interface DictionaryFacilityDao extends CrudDao<DictionaryFacility> {

    void updateFacility(DictionaryFacility dictionaryFacility);

    void saveFacility(DictionaryFacility dictionaryFacility);

    void deleteFacility(String id);

}
