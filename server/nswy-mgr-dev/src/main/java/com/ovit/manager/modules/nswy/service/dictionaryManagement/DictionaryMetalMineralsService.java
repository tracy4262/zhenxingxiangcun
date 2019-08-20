package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryMetalMineralsDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryMetalMinerals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryMetalMineralsService extends CrudService<DictionaryMetalMineralsDao, DictionaryMetalMinerals> {

    @Autowired
    private DictionaryMetalMineralsDao dictionaryMetalMineralsDao;

    /*public Page<DictionaryMetalMinerals> findList(DictionaryMetalMinerals dictionaryMetalMinerals, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryMetalMinerals);
    }*/

    public List<DictionaryMetalMinerals> findList(DictionaryMetalMinerals dictionaryMetalMinerals){
        return dictionaryMetalMineralsDao.findList(dictionaryMetalMinerals);
    }

    public List<DictionaryMetalMinerals> findMetalMineralsList(DictionaryMetalMinerals dictionaryMetalMinerals){

        List<DictionaryMetalMinerals> dictionaryMetalMineralss = dictionaryMetalMineralsDao.findList(dictionaryMetalMinerals);

        return dictionaryMetalMineralss ;
    }

    public void saveMetalMinerals(DictionaryMetalMinerals dictionaryMetalMinerals){
            dictionaryMetalMineralsDao.saveMetalMinerals(dictionaryMetalMinerals);
    }

    public void updateMetalMinerals(DictionaryMetalMinerals dictionaryMetalMinerals){
            dictionaryMetalMineralsDao.updateMetalMinerals(dictionaryMetalMinerals);
    }

    public void deleteMetalMinerals(String id){
            dictionaryMetalMineralsDao.deleteMetalMinerals(id);
    }


}
