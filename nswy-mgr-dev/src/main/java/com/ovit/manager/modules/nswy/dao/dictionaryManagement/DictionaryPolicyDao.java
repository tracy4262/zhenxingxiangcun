package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPolicy;


@MyBatisDao
public interface DictionaryPolicyDao extends CrudDao<DictionaryPolicy> {

    void updatePolicy(DictionaryPolicy dictionaryPolicy);

    void savePolicy(DictionaryPolicy dictionaryPolicy);

    void deletePolicy(String id);

}
