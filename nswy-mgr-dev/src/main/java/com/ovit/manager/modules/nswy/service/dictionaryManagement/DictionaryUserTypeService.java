package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryUserTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryUserTypeService extends CrudService<DictionaryUserTypeDao, DictionaryUserType> {

    @Autowired
    private DictionaryUserTypeDao dictionaryUserTypeDao;

    /*public Page<DictionaryUserType> findList(DictionaryUserType dictionaryUserType, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryUserType);
    }*/

    public List<DictionaryUserType> findList(DictionaryUserType dictionaryUserType){
        return dictionaryUserTypeDao.findList(dictionaryUserType);
    }
    
    public List<DictionaryUserType> findUserTypeList(DictionaryUserType dictionaryUserType){

        List<DictionaryUserType> dictionaryUserTypes = dictionaryUserTypeDao.findList(dictionaryUserType);

        return dictionaryUserTypes ;
    }

    public void saveUserType(DictionaryUserType dictionaryUserType){
            dictionaryUserTypeDao.saveUserType(dictionaryUserType);
    }

    public void updateUserType(DictionaryUserType dictionaryUserType){
            dictionaryUserTypeDao.updateUserType(dictionaryUserType);
    }

    public void deleteUserType(String id){
            dictionaryUserTypeDao.deleteUserType(id);
    }


}
