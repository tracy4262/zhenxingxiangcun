package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTerrain;


@MyBatisDao
public interface DictionaryTerrainDao extends CrudDao<DictionaryTerrain> {

    void updateTerrain(DictionaryTerrain dictionaryTerrain);

    void saveTerrain(DictionaryTerrain dictionaryTerrain);

    void deleteTerrain(String id);

}
