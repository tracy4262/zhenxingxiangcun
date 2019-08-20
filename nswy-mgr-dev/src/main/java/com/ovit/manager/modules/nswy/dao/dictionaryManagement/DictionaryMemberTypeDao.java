package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMemberType;


@MyBatisDao
public interface DictionaryMemberTypeDao extends CrudDao<DictionaryMemberType> {

    void updateMemberType(DictionaryMemberType dictionaryMemberType);

    void saveMemberType(DictionaryMemberType dictionaryMemberType);

    void deleteMemberType(String id);

}
