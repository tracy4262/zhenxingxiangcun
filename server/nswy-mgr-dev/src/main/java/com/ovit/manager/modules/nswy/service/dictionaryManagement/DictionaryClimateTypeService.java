package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryClimateTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryClimateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryClimateTypeService extends CrudService<DictionaryClimateTypeDao, DictionaryClimateType> {

    @Autowired
    private DictionaryClimateTypeDao dictionaryClimateTypeDao;

//    public Page<DictionaryClimateType> findList(DictionaryClimateType dictionaryClimateType, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryClimateType);
//    }

    public List<DictionaryClimateType> findList(DictionaryClimateType dictionaryClimateType){
        return dictionaryClimateTypeDao.findList(dictionaryClimateType);
    }

    public List<DictionaryClimateType> findClimateTypeList(DictionaryClimateType dictionaryClimateType){

        List<DictionaryClimateType> dictionaryClimateTypes = dictionaryClimateTypeDao.findList(dictionaryClimateType);

        return dictionaryClimateTypes ;
    }

    public void saveClimateType(DictionaryClimateType dictionaryClimateType){
            dictionaryClimateTypeDao.saveClimateType(dictionaryClimateType);
    }

    public void updateClimateType(DictionaryClimateType dictionaryClimateType){
            dictionaryClimateTypeDao.updateClimateType(dictionaryClimateType);
    }

    public void deleteClimateType(String id){
            dictionaryClimateTypeDao.deleteClimateType(id);
    }


}
