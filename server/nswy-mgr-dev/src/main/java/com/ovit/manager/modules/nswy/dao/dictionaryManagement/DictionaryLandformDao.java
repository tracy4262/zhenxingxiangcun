package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandform;


@MyBatisDao
public interface DictionaryLandformDao extends CrudDao<DictionaryLandform> {

    void updateLandform(DictionaryLandform dictionaryLandform);

    void saveLandform(DictionaryLandform dictionaryLandform);

    void deleteLandform(String id);

}
