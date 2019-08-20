package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionarySpecialPopulationDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionarySpecialPopulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionarySpecialPopulationService extends CrudService<DictionarySpecialPopulationDao, DictionarySpecialPopulation> {

    @Autowired
    private DictionarySpecialPopulationDao dictionarySpecialPopulationDao;

//    public Page<DictionarySpecialPopulation> findList(DictionarySpecialPopulation dictionarySpecialPopulation, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionarySpecialPopulation);
//    }

    public List<DictionarySpecialPopulation> findList(DictionarySpecialPopulation dictionarySpecialPopulation){
        return dictionarySpecialPopulationDao.findList(dictionarySpecialPopulation);
    }

    public List<DictionarySpecialPopulation> findSpecialPopulationList(DictionarySpecialPopulation dictionarySpecialPopulation){

        List<DictionarySpecialPopulation> dictionarySpecialPopulations = dictionarySpecialPopulationDao.findList(dictionarySpecialPopulation);

        return dictionarySpecialPopulations ;
    }

    public void saveSpecialPopulation(DictionarySpecialPopulation dictionarySpecialPopulation){
            dictionarySpecialPopulationDao.saveSpecialPopulation(dictionarySpecialPopulation);
    }

    public void updateSpecialPopulation(DictionarySpecialPopulation dictionarySpecialPopulation){
            dictionarySpecialPopulationDao.updateSpecialPopulation(dictionarySpecialPopulation);
    }

    public void deleteSpecialPopulation(String id){
            dictionarySpecialPopulationDao.deleteSpecialPopulation(id);
    }


}
