package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryTownDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryTown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryTownService extends CrudService<DictionaryTownDao, DictionaryTown> {

    @Autowired
    private DictionaryTownDao dictionaryTownDao;

    /*public Page<DictionaryTown> findList(DictionaryTown dictionaryTown, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryTown);
    }*/

    public List<DictionaryTown> findList(DictionaryTown dictionaryTown){
        return dictionaryTownDao.findList(dictionaryTown);
    }
    
    public List<DictionaryTown> findTownList(DictionaryTown dictionaryTown){

        List<DictionaryTown> dictionaryTowns = dictionaryTownDao.findList(dictionaryTown);

        return dictionaryTowns ;
    }

    public void saveTown(DictionaryTown dictionaryTown){
            dictionaryTownDao.saveTown(dictionaryTown);
    }

    public void updateTown(DictionaryTown dictionaryTown){
            dictionaryTownDao.updateTown(dictionaryTown);
    }

    public void deleteTown(String id){
            dictionaryTownDao.deleteTown(id);
    }


}
