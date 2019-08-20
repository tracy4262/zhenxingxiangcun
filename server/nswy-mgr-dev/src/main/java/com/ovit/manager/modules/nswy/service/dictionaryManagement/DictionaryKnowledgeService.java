package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryKnowledgeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryKnowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryKnowledgeService extends CrudService<DictionaryKnowledgeDao, DictionaryKnowledge> {

    @Autowired
    private DictionaryKnowledgeDao dictionaryKnowledgeDao;

    public Page<DictionaryKnowledge> findList(DictionaryKnowledge dictionaryKnowledge, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryKnowledge);
    }

    public List<DictionaryKnowledge> findKnowledgeList(DictionaryKnowledge dictionaryKnowledge){

        List<DictionaryKnowledge> dictionaryKnowledges = dictionaryKnowledgeDao.findList(dictionaryKnowledge);

        return dictionaryKnowledges ;
    }

    public void saveKnowledge(DictionaryKnowledge dictionaryKnowledge){
            dictionaryKnowledgeDao.saveKnowledge(dictionaryKnowledge);
    }

    public void updateKnowledge(DictionaryKnowledge dictionaryKnowledge){
            dictionaryKnowledgeDao.updateKnowledge(dictionaryKnowledge);
    }

    public void deleteKnowledge(String id){
            dictionaryKnowledgeDao.deleteKnowledge(id);
    }


}
