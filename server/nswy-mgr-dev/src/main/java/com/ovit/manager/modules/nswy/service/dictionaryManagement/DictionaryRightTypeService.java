package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryRightTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryRightType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryRightTypeService extends CrudService<DictionaryRightTypeDao, DictionaryRightType> {

    @Autowired
    private DictionaryRightTypeDao dictionaryRightTypeDao;

//    public Page<DictionaryRightType> findList(DictionaryRightType dictionaryRightType, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryRightType);
//    }

    public List<DictionaryRightType> findList(DictionaryRightType dictionaryRightType){
        return dictionaryRightTypeDao.findList(dictionaryRightType);
    }

    public List<DictionaryRightType> findRightTypeList(DictionaryRightType dictionaryRightType){

        List<DictionaryRightType> dictionaryRightTypes = dictionaryRightTypeDao.findList(dictionaryRightType);

        return dictionaryRightTypes ;
    }

    public void saveRightType(DictionaryRightType dictionaryRightType){
            dictionaryRightTypeDao.saveRightType(dictionaryRightType);
    }

    public void updateRightType(DictionaryRightType dictionaryRightType){
            dictionaryRightTypeDao.updateRightType(dictionaryRightType);
    }

    public void deleteRightType(String id){
            dictionaryRightTypeDao.deleteRightType(id);
    }


}
