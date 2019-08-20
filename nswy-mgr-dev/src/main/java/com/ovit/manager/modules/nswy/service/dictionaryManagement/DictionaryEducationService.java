package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryEducationDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEducation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryEducationService extends CrudService<DictionaryEducationDao, DictionaryEducation> {

    @Autowired
    private DictionaryEducationDao dictionaryEducationDao;

//    public Page<DictionaryEducation> findList(DictionaryEducation dictionaryEducation, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryEducation);
//    }

    public List<DictionaryEducation> findList(DictionaryEducation dictionaryEducation){
        return dictionaryEducationDao.findList(dictionaryEducation);
    }

    public List<DictionaryEducation> findEducationList(DictionaryEducation dictionaryEducation){

        List<DictionaryEducation> dictionaryEducations = dictionaryEducationDao.findList(dictionaryEducation);

        return dictionaryEducations ;
    }

    public void saveEducation(DictionaryEducation dictionaryEducation){
            dictionaryEducationDao.saveEducation(dictionaryEducation);
    }

    public void updateEducation(DictionaryEducation dictionaryEducation){
            dictionaryEducationDao.updateEducation(dictionaryEducation);
    }

    public void deleteEducation(String id){
            dictionaryEducationDao.deleteEducation(id);
    }


}
