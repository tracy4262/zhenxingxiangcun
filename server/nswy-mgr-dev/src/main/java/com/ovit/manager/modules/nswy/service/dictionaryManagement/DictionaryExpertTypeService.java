package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryExpertTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryExpertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryExpertTypeService extends CrudService<DictionaryExpertTypeDao, DictionaryExpertType> {

    @Autowired
    private DictionaryExpertTypeDao dictionaryExpertTypeDao;

    /*public Page<DictionaryExpertType> findList(DictionaryExpertType dictionaryExpertType, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryExpertType);
    }*/

    public List<DictionaryExpertType> findList(DictionaryExpertType dictionaryExpertType){
        return dictionaryExpertTypeDao.findList(dictionaryExpertType);
    }
    
    public List<DictionaryExpertType> findExpertTypeList(DictionaryExpertType dictionaryExpertType){

        List<DictionaryExpertType> dictionaryExpertTypes = dictionaryExpertTypeDao.findList(dictionaryExpertType);

        return dictionaryExpertTypes ;
    }

    public void saveExpertType(DictionaryExpertType dictionaryExpertType){
            dictionaryExpertTypeDao.saveExpertType(dictionaryExpertType);
    }

    public void updateExpertType(DictionaryExpertType dictionaryExpertType){
            dictionaryExpertTypeDao.updateExpertType(dictionaryExpertType);
    }

    public void deleteExpertType(String id){
            dictionaryExpertTypeDao.deleteExpertType(id);
    }


}
