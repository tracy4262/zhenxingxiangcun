package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryMemberTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryMemberTypeService extends CrudService<DictionaryMemberTypeDao, DictionaryMemberType> {

    @Autowired
    private DictionaryMemberTypeDao dictionaryMemberTypeDao;

//    public Page<DictionaryMemberType> findList(DictionaryMemberType dictionaryMemberType, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryMemberType);
//    }

    public List<DictionaryMemberType> findList(DictionaryMemberType dictionaryMemberType){
        return dictionaryMemberTypeDao.findList(dictionaryMemberType);
    }

    public List<DictionaryMemberType> findMemberTypeList(DictionaryMemberType dictionaryMemberType){

        List<DictionaryMemberType> dictionaryMemberTypes = dictionaryMemberTypeDao.findList(dictionaryMemberType);

        return dictionaryMemberTypes ;
    }

    public void saveMemberType(DictionaryMemberType dictionaryMemberType){
            dictionaryMemberTypeDao.saveMemberType(dictionaryMemberType);
    }

    public void updateMemberType(DictionaryMemberType dictionaryMemberType){
            dictionaryMemberTypeDao.updateMemberType(dictionaryMemberType);
    }

    public void deleteMemberType(String id){
            dictionaryMemberTypeDao.deleteMemberType(id);
    }


}
