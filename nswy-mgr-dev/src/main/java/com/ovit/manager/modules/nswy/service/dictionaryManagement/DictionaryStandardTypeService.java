package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryStandardTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryStandardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryStandardTypeService extends CrudService<DictionaryStandardTypeDao, DictionaryStandardType> {

    @Autowired
    private DictionaryStandardTypeDao dictionaryStandardTypeDao;

//    public Page<DictionaryStandardType> findList(DictionaryStandardType dictionaryStandardType, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryStandardType);
//    }

    public List<DictionaryStandardType> findList(DictionaryStandardType dictionaryStandardType){
        return dictionaryStandardTypeDao.findList(dictionaryStandardType);
    }

    public List<DictionaryStandardType> findStandardTypeList(DictionaryStandardType dictionaryStandardType){

        List<DictionaryStandardType> dictionaryStandardTypes = dictionaryStandardTypeDao.findList(dictionaryStandardType);

        return dictionaryStandardTypes ;
    }

    public void saveStandardType(DictionaryStandardType dictionaryStandardType){
            dictionaryStandardTypeDao.saveStandardType(dictionaryStandardType);
    }

    public void updateStandardType(DictionaryStandardType dictionaryStandardType){
            dictionaryStandardTypeDao.updateStandardType(dictionaryStandardType);
    }

    public void deleteStandardType(String id){
            dictionaryStandardTypeDao.deleteStandardType(id);
    }


}
