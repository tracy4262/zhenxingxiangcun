package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryLandformDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryLandformService extends CrudService<DictionaryLandformDao, DictionaryLandform> {

    @Autowired
    private DictionaryLandformDao dictionaryLandformDao;

//    public Page<DictionaryLandform> findList(DictionaryLandform dictionaryLandform, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryLandform);
//    }

    public List<DictionaryLandform> findList(DictionaryLandform dictionaryLandform){
        return dictionaryLandformDao.findList(dictionaryLandform);
    }

    public List<DictionaryLandform> findLandformList(DictionaryLandform dictionaryLandform){

        List<DictionaryLandform> dictionaryLandforms = dictionaryLandformDao.findList(dictionaryLandform);

        return dictionaryLandforms ;
    }

    public void saveLandform(DictionaryLandform dictionaryLandform){
            dictionaryLandformDao.saveLandform(dictionaryLandform);
    }

    public void updateLandform(DictionaryLandform dictionaryLandform){
            dictionaryLandformDao.updateLandform(dictionaryLandform);
    }

    public void deleteLandform(String id){
            dictionaryLandformDao.deleteLandform(id);
    }


}
