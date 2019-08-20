package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPoliticalStatus;


@MyBatisDao
public interface DictionaryPoliticalStatusDao extends CrudDao<DictionaryPoliticalStatus> {

    void updatePoliticalStatus(DictionaryPoliticalStatus dictionaryPoliticalStatus);

    void savePoliticalStatus(DictionaryPoliticalStatus dictionaryPoliticalStatus);

    void deletePoliticalStatus(String id);

}
