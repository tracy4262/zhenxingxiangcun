package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryProtectionLevelDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryProtectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryProtectionLevelService extends CrudService<DictionaryProtectionLevelDao, DictionaryProtectionLevel> {

    @Autowired
    private DictionaryProtectionLevelDao dictionaryProtectionLevelDao;

//    public Page<DictionaryProtectionLevel> findList(DictionaryProtectionLevel dictionaryProtectionLevel, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryProtectionLevel);
//    }

    public List<DictionaryProtectionLevel> findList(DictionaryProtectionLevel dictionaryProtectionLevel){
        return dictionaryProtectionLevelDao.findList(dictionaryProtectionLevel);
    }

    public List<DictionaryProtectionLevel> findProtectionLevelList(DictionaryProtectionLevel dictionaryProtectionLevel){

        List<DictionaryProtectionLevel> dictionaryProtectionLevels = dictionaryProtectionLevelDao.findList(dictionaryProtectionLevel);

        return dictionaryProtectionLevels ;
    }

    public void saveProtectionLevel(DictionaryProtectionLevel dictionaryProtectionLevel){
            dictionaryProtectionLevelDao.saveProtectionLevel(dictionaryProtectionLevel);
    }

    public void updateProtectionLevel(DictionaryProtectionLevel dictionaryProtectionLevel){
            dictionaryProtectionLevelDao.updateProtectionLevel(dictionaryProtectionLevel);
    }

    public void deleteProtectionLevel(String id){
            dictionaryProtectionLevelDao.deleteProtectionLevel(id);
    }


}
