package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryAgencyTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryAgencyTypeService extends CrudService<DictionaryAgencyTypeDao, DictionaryAgencyType> {

    @Autowired
    private DictionaryAgencyTypeDao dictionaryAgencyTypeDao;

//    public Page<DictionaryAgencyType> findList(DictionaryAgencyType dictionaryAgencyType, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryAgencyType);
//    }

    public List<DictionaryAgencyType> findList(DictionaryAgencyType dictionaryAgencyType){
        return dictionaryAgencyTypeDao.findList(dictionaryAgencyType);
    }

    public List<DictionaryAgencyType> findAgencyTypeList(DictionaryAgencyType dictionaryAgencyType){

        List<DictionaryAgencyType> dictionaryAgencyTypes = dictionaryAgencyTypeDao.findList(dictionaryAgencyType);

        return dictionaryAgencyTypes ;
    }

    public void saveAgencyType(DictionaryAgencyType dictionaryAgencyType){
            dictionaryAgencyTypeDao.saveAgencyType(dictionaryAgencyType);
    }

    public void updateAgencyType(DictionaryAgencyType dictionaryAgencyType){
            dictionaryAgencyTypeDao.updateAgencyType(dictionaryAgencyType);
    }

    public void deleteAgencyType(String id){
            dictionaryAgencyTypeDao.deleteAgencyType(id);
    }


}
