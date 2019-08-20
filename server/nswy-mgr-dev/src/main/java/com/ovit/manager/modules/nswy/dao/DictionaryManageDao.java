package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.DictionaryManageInfo;

@MyBatisDao
public interface DictionaryManageDao  extends CrudDao<DictionaryManageInfo> {
    void updateDictionaryManage(DictionaryManageInfo dictionaryManageInfo);

    void saveDictionaryManage(DictionaryManageInfo dictionaryManageInfo);

    void deleteDictionaryManage(String id);

}
