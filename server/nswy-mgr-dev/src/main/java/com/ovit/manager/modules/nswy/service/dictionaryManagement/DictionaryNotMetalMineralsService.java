package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryNotMetalMineralsDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryNotMetalMinerals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryNotMetalMineralsService extends CrudService<DictionaryNotMetalMineralsDao, DictionaryNotMetalMinerals> {

    @Autowired
    private DictionaryNotMetalMineralsDao dictionaryNotMetalMineralsDao;

    public Page<DictionaryNotMetalMinerals> findList(DictionaryNotMetalMinerals dictionaryNotMetalMinerals, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryNotMetalMinerals);
    }

    public List<DictionaryNotMetalMinerals> findNotMetalMineralsList(DictionaryNotMetalMinerals dictionaryNotMetalMinerals){

        List<DictionaryNotMetalMinerals> dictionaryNotMetalMineralss = dictionaryNotMetalMineralsDao.findList(dictionaryNotMetalMinerals);

        return dictionaryNotMetalMineralss ;
    }

    public void saveNotMetalMinerals(DictionaryNotMetalMinerals dictionaryNotMetalMinerals){
            dictionaryNotMetalMineralsDao.saveNotMetalMinerals(dictionaryNotMetalMinerals);
    }

    public void updateNotMetalMinerals(DictionaryNotMetalMinerals dictionaryNotMetalMinerals){
            dictionaryNotMetalMineralsDao.updateNotMetalMinerals(dictionaryNotMetalMinerals);
    }

    public void deleteNotMetalMinerals(String id){
            dictionaryNotMetalMineralsDao.deleteNotMetalMinerals(id);
    }


}
