package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryQualification;


@MyBatisDao
public interface DictionaryQualificationDao extends CrudDao<DictionaryQualification> {

    void updateQualification(DictionaryQualification dictionaryQualification);

    void saveQualification(DictionaryQualification dictionaryQualification);

    void deleteQualification(String id);

}
