package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryEnergyMineralsDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnergyMinerals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryEnergyMineralsService extends CrudService<DictionaryEnergyMineralsDao, DictionaryEnergyMinerals> {

    @Autowired
    private DictionaryEnergyMineralsDao dictionaryEnergyMineralsDao;

    public Page<DictionaryEnergyMinerals> findList(DictionaryEnergyMinerals dictionaryEnergyMinerals, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryEnergyMinerals);
    }

    public List<DictionaryEnergyMinerals> findEnergyMineralsList(DictionaryEnergyMinerals dictionaryEnergyMinerals){

        List<DictionaryEnergyMinerals> dictionaryEnergyMineralss = dictionaryEnergyMineralsDao.findList(dictionaryEnergyMinerals);

        return dictionaryEnergyMineralss ;
    }

    public void saveEnergyMinerals(DictionaryEnergyMinerals dictionaryEnergyMinerals){
            dictionaryEnergyMineralsDao.saveEnergyMinerals(dictionaryEnergyMinerals);
    }

    public void updateEnergyMinerals(DictionaryEnergyMinerals dictionaryEnergyMinerals){
            dictionaryEnergyMineralsDao.updateEnergyMinerals(dictionaryEnergyMinerals);
    }

    public void deleteEnergyMinerals(String id){
            dictionaryEnergyMineralsDao.deleteEnergyMinerals(id);
    }


}
