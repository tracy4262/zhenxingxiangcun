package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEducation;


@MyBatisDao
public interface DictionaryEducationDao extends CrudDao<DictionaryEducation> {

    void updateEducation(DictionaryEducation dictionaryEducation);

    void saveEducation(DictionaryEducation dictionaryEducation);

    void deleteEducation(String id);

}
