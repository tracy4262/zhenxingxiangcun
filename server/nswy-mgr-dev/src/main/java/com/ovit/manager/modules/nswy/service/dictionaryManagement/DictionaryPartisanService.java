package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryPartisanDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPartisan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryPartisanService extends CrudService<DictionaryPartisanDao, DictionaryPartisan> {

    @Autowired
    private DictionaryPartisanDao dictionaryPartisanDao;

//    public Page<DictionaryPartisan> findList(DictionaryPartisan dictionaryPartisan, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryPartisan);
//    }

    public List<DictionaryPartisan> findList(DictionaryPartisan dictionaryPartisan){
        return dictionaryPartisanDao.findList(dictionaryPartisan);
    }

    public List<DictionaryPartisan> findPartisanList(DictionaryPartisan dictionaryPartisan){

        List<DictionaryPartisan> dictionaryPartisans = dictionaryPartisanDao.findList(dictionaryPartisan);

        return dictionaryPartisans ;
    }

    public void savePartisan(DictionaryPartisan dictionaryPartisan){
            dictionaryPartisanDao.savePartisan(dictionaryPartisan);
    }

    public void updatePartisan(DictionaryPartisan dictionaryPartisan){
            dictionaryPartisanDao.updatePartisan(dictionaryPartisan);
    }

    public void deletePartisan(String id){
            dictionaryPartisanDao.deletePartisan(id);
    }


}
