package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryLandUseDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryLandUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryLandUseService extends CrudService<DictionaryLandUseDao, DictionaryLandUse> {

    @Autowired
    private DictionaryLandUseDao dictionaryLandUseDao;

    /*public Page<DictionaryLandUse> findList(DictionaryLandUse dictionaryLandUse, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryLandUse);
    }*/

    public List<DictionaryLandUse> findList(DictionaryLandUse dictionaryLandUse){
        return dictionaryLandUseDao.findList(dictionaryLandUse);
    }
    
    public List<DictionaryLandUse> findLandUseList(DictionaryLandUse dictionaryLandUse){

        List<DictionaryLandUse> dictionaryLandUses = dictionaryLandUseDao.findList(dictionaryLandUse);

        return dictionaryLandUses ;
    }

    public void saveLandUse(DictionaryLandUse dictionaryLandUse){
            dictionaryLandUseDao.saveLandUse(dictionaryLandUse);
    }

    public void updateLandUse(DictionaryLandUse dictionaryLandUse){
            dictionaryLandUseDao.updateLandUse(dictionaryLandUse);
    }

    public void deleteLandUse(String id){
            dictionaryLandUseDao.deleteLandUse(id);
    }


}
