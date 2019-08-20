package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandUse;


@MyBatisDao
public interface DictionaryLandUseDao extends CrudDao<DictionaryLandUse> {

    void updateLandUse(DictionaryLandUse dictionaryLandUse);

    void saveLandUse(DictionaryLandUse dictionaryLandUse);

    void deleteLandUse(String id);

}
