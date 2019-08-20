package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryBank;


@MyBatisDao
public interface DictionaryBankDao extends CrudDao<DictionaryBank> {

    void updateBank(DictionaryBank dictionaryBank);

    void saveBank(DictionaryBank dictionaryBank);

    void deleteBank(String id);

}
