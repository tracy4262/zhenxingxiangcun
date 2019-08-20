package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryQualificationDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryQualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryQualificationService extends CrudService<DictionaryQualificationDao, DictionaryQualification> {

    @Autowired
    private DictionaryQualificationDao dictionaryQualificationDao;

//    public Page<DictionaryQualification> findList(DictionaryQualification dictionaryQualification, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryQualification);
//    }

    public List<DictionaryQualification> findList(DictionaryQualification dictionaryQualification){
        return dictionaryQualificationDao.findList(dictionaryQualification);
    }

    public List<DictionaryQualification> findQualificationList(DictionaryQualification dictionaryQualification){

        List<DictionaryQualification> dictionaryQualifications = dictionaryQualificationDao.findList(dictionaryQualification);

        return dictionaryQualifications ;
    }

    public void saveQualification(DictionaryQualification dictionaryQualification){
            dictionaryQualificationDao.saveQualification(dictionaryQualification);
    }

    public void updateQualification(DictionaryQualification dictionaryQualification){
            dictionaryQualificationDao.updateQualification(dictionaryQualification);
    }

    public void deleteQualification(String id){
            dictionaryQualificationDao.deleteQualification(id);
    }


}
