package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryAgencyLevelDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryAgencyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryAgencyLevelService extends CrudService<DictionaryAgencyLevelDao, DictionaryAgencyLevel> {

    @Autowired
    private DictionaryAgencyLevelDao dictionaryAgencyLevelDao;

//    public Page<DictionaryAgencyLevel> findList(DictionaryAgencyLevel dictionaryAgencyLevel, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryAgencyLevel);
//    }

    public List<DictionaryAgencyLevel> findList(DictionaryAgencyLevel dictionaryAgencyLevel){
        return dictionaryAgencyLevelDao.findList(dictionaryAgencyLevel);
    }

    public List<DictionaryAgencyLevel> findAgencyLevelList(DictionaryAgencyLevel dictionaryAgencyLevel){

        List<DictionaryAgencyLevel> dictionaryAgencyLevels = dictionaryAgencyLevelDao.findList(dictionaryAgencyLevel);

        return dictionaryAgencyLevels ;
    }

    public void saveAgencyLevel(DictionaryAgencyLevel dictionaryAgencyLevel){
            dictionaryAgencyLevelDao.saveAgencyLevel(dictionaryAgencyLevel);
    }

    public void updateAgencyLevel(DictionaryAgencyLevel dictionaryAgencyLevel){
            dictionaryAgencyLevelDao.updateAgencyLevel(dictionaryAgencyLevel);
    }

    public void deleteAgencyLevel(String id){
            dictionaryAgencyLevelDao.deleteAgencyLevel(id);
    }


}
