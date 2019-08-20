package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryInformation;


@MyBatisDao
public interface DictionaryInformationDao extends CrudDao<DictionaryInformation> {

    void updateInformation(DictionaryInformation dictionaryInformation);

    void saveInformation(DictionaryInformation dictionaryInformation);

    void deleteInformation(String id);

}
