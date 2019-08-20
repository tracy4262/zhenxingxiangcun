package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryFaithDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryFaith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryFaithService extends CrudService<DictionaryFaithDao, DictionaryFaith> {

    @Autowired
    private DictionaryFaithDao dictionaryFaithDao;

//    public Page<DictionaryFaith> findList(DictionaryFaith dictionaryFaith, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryFaith);
//    }

    public List<DictionaryFaith> findList(DictionaryFaith dictionaryFaith){
        return dictionaryFaithDao.findList(dictionaryFaith);
    }

    public List<DictionaryFaith> findFaithList(DictionaryFaith dictionaryFaith){

        List<DictionaryFaith> dictionaryFaiths = dictionaryFaithDao.findList(dictionaryFaith);

        return dictionaryFaiths ;
    }

    public void saveFaith(DictionaryFaith dictionaryFaith){
            dictionaryFaithDao.saveFaith(dictionaryFaith);
    }

    public void updateFaith(DictionaryFaith dictionaryFaith){
            dictionaryFaithDao.updateFaith(dictionaryFaith);
    }

    public void deleteFaith(String id){
            dictionaryFaithDao.deleteFaith(id);
    }


}
