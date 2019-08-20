package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProtectionLevel;


@MyBatisDao
public interface DictionaryProtectionLevelDao extends CrudDao<DictionaryProtectionLevel> {

    void updateProtectionLevel(DictionaryProtectionLevel dictionaryProtectionLevel);

    void saveProtectionLevel(DictionaryProtectionLevel dictionaryProtectionLevel);

    void deleteProtectionLevel(String id);

}
