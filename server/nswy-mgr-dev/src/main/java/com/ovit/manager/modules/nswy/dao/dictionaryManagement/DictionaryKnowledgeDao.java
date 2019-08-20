package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryKnowledge;


@MyBatisDao
public interface DictionaryKnowledgeDao extends CrudDao<DictionaryKnowledge> {

    void updateKnowledge(DictionaryKnowledge dictionaryKnowledge);

    void saveKnowledge(DictionaryKnowledge dictionaryKnowledge);

    void deleteKnowledge(String id);

}
