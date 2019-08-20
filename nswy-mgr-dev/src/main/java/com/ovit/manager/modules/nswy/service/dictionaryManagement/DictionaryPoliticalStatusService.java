package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryPoliticalStatusDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPoliticalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryPoliticalStatusService extends CrudService<DictionaryPoliticalStatusDao, DictionaryPoliticalStatus> {

    @Autowired
    private DictionaryPoliticalStatusDao dictionaryPoliticalStatusDao;

//    public Page<DictionaryPoliticalStatus> findList(DictionaryPoliticalStatus dictionaryPoliticalStatus, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryPoliticalStatus);
//    }

    public List<DictionaryPoliticalStatus> findList(DictionaryPoliticalStatus dictionaryPoliticalStatus){
        return dictionaryPoliticalStatusDao.findList(dictionaryPoliticalStatus);
    }

    public List<DictionaryPoliticalStatus> findPoliticalStatusList(DictionaryPoliticalStatus dictionaryPoliticalStatus){

        List<DictionaryPoliticalStatus> dictionaryPoliticalStatuss = dictionaryPoliticalStatusDao.findList(dictionaryPoliticalStatus);

        return dictionaryPoliticalStatuss ;
    }

    public void savePoliticalStatus(DictionaryPoliticalStatus dictionaryPoliticalStatus){
            dictionaryPoliticalStatusDao.savePoliticalStatus(dictionaryPoliticalStatus);
    }

    public void updatePoliticalStatus(DictionaryPoliticalStatus dictionaryPoliticalStatus){
            dictionaryPoliticalStatusDao.updatePoliticalStatus(dictionaryPoliticalStatus);
    }

    public void deletePoliticalStatus(String id){
            dictionaryPoliticalStatusDao.deletePoliticalStatus(id);
    }


}
