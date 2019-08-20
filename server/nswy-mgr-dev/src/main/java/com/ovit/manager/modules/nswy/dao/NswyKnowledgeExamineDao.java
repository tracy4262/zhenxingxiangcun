package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.KnowledgeInfo;

import java.util.Map;

@MyBatisDao
public interface NswyKnowledgeExamineDao extends CrudDao<KnowledgeInfo> {
    void knowledgeDelete(Integer knowledgeId);

    void knowledgeAdopt(Integer knowledgeId);

    void knowledgeRefuse(Integer knowledgeId);

    void knowledgeSetOrderNum(Map<String, Object> map);

    void knowledgeUpdateNavDisplay(Map<String, Object> map);

    void knowledgeUpdateHome(Map<String, Object> map);
}
