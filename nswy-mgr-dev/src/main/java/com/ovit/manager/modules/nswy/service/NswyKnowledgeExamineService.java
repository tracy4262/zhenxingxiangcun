package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NswyKnowledgeExamineDao;
import com.ovit.manager.modules.nswy.entity.KnowledgeInfo;
import com.ovit.manager.modules.nswy.entity.ShopCurrencyCommityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 知识审核
 *
 * @author haoWen
 * @create 2018-03-01 11:19
 **/

@Service
@Transactional
public class NswyKnowledgeExamineService extends CrudService<NswyKnowledgeExamineDao, KnowledgeInfo> {
    @Autowired
    private NswyKnowledgeExamineDao nswyKnowledgeExamineDao;
    public Page<KnowledgeInfo> findInfoList(KnowledgeInfo knowledgeInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<KnowledgeInfo> page = this.findPage(new Page<>(request,response),knowledgeInfo);
        return page;
    }

    public void knowledgeDelete(Integer knowledgeId) {
        nswyKnowledgeExamineDao.knowledgeDelete(knowledgeId);
    }

    public void knowledgeAdopt(Integer knowledgeId) {
        nswyKnowledgeExamineDao.knowledgeAdopt(knowledgeId);
    }

    public void knowledgeRefuse(Integer knowledgeId) {
        nswyKnowledgeExamineDao.knowledgeRefuse(knowledgeId);
    }

    public void knowledgeUpdateHome(Map<String, Object> map) {
        nswyKnowledgeExamineDao.knowledgeUpdateHome(map);
    }

    public void knowledgeUpdateNavDisplay(Map<String, Object> map) {
        nswyKnowledgeExamineDao.knowledgeUpdateNavDisplay(map);
    }

    public void knowledgeSetOrderNum(Map<String, Object> map) {
        nswyKnowledgeExamineDao.knowledgeSetOrderNum(map);
    }
}
