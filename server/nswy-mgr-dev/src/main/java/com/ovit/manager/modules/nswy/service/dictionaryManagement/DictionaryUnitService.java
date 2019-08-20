package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryUnitDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictionaryUnitService extends CrudService<DictionaryUnitDao, DictionaryUnit> {

    @Autowired
    private DictionaryUnitDao dictionaryUnitDao;

    /*public Page<DictionaryUnit> findList(DictionaryUnit dictionaryUnit, HttpServletRequest request, HttpServletResponse response){
        Page<DictionaryUnit> page = this.findPage(new Page<DictionaryUnit>(request,response),dictionaryUnit);
        return page;
    }*/

    public List<DictionaryUnit> findList(DictionaryUnit dictionaryUnit){
        return dictionaryUnitDao.findList(dictionaryUnit);
    }

    public List<DictionaryUnit> findUnit(DictionaryUnit dictionaryUnit){
        return dictionaryUnitDao.findUnit(dictionaryUnit);
    }

    public List<DictionaryUnit> findTopUnit(DictionaryUnit dictionaryUnit){
        return dictionaryUnitDao.findTopUnit(dictionaryUnit);
    }

    public List<Map<String,Object>> findUnitType(){
        return dictionaryUnitDao.findUnitType();
    }

    public void  updateUnit(DictionaryUnit dictionaryUnit){
        dictionaryUnitDao.updateUnit(dictionaryUnit);
    }

    public void  updateTopUnit(DictionaryUnit dictionaryUnit){
        dictionaryUnitDao.updateTopUnit(dictionaryUnit);
    }


    public void saveUnit(DictionaryUnit dictionaryUnit){
        dictionaryUnitDao.saveUnit(dictionaryUnit);
    }

    public void saveTopUnit(DictionaryUnit dictionaryUnit){
        dictionaryUnitDao.saveTopUnit(dictionaryUnit);
    }

    public void deleteUnit(String id){dictionaryUnitDao.deleteUnit(id);}

    public void deleteTopUnit(String id){dictionaryUnitDao.deleteTopUnit(id);}

    public Integer findMaxUnitType(){
        return  dictionaryUnitDao.findMaxUnitType();

    }

}
