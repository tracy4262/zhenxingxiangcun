package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryTechnicalDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTechnical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryTechnicalService extends CrudService<DictionaryTechnicalDao, DictionaryTechnical> {

    @Autowired
    private DictionaryTechnicalDao dictionaryTechnicalDao;

    /*public Page<DictionaryTechnical> findList(DictionaryTechnical dictionaryTechnical, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryTechnical);
    }*/

    public List<DictionaryTechnical> findList(DictionaryTechnical dictionaryTechnical){
        return dictionaryTechnicalDao.findList(dictionaryTechnical);
    }
    
    public List<DictionaryTechnical> findTechnicalList(DictionaryTechnical dictionaryTechnical){

        List<DictionaryTechnical> dictionaryTechnicals = dictionaryTechnicalDao.findList(dictionaryTechnical);

        return dictionaryTechnicals ;
    }

    public void saveTechnical(DictionaryTechnical dictionaryTechnical){
        dictionaryTechnicalDao.saveTechnical(dictionaryTechnical);
    }

    public void updateTechnical(DictionaryTechnical dictionaryTechnical){
        dictionaryTechnicalDao.updateTechnical(dictionaryTechnical);
    }

    public void deleteTechnical(String id){
        dictionaryTechnicalDao.deleteTechnical(id);
    }


}
