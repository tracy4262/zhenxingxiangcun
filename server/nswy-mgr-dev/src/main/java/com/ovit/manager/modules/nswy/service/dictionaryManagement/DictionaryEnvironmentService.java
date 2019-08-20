package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryEnvironmentDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DictionaryEnvironmentService extends CrudService<DictionaryEnvironmentDao, DictionaryEnvironment> {

    @Autowired
    private DictionaryEnvironmentDao dictionaryEnvironmentDao;

//    public Page<DictionaryEnvironment> findList(DictionaryEnvironment dictionaryEnvironment, HttpServletRequest request, HttpServletResponse response) {
//        return this.findPage(new Page<>(request, response), dictionaryEnvironment);
//    }

    public List<DictionaryEnvironment> findList(DictionaryEnvironment dictionaryEnvironment){
        return dictionaryEnvironmentDao.findList(dictionaryEnvironment);
    }

    public List<DictionaryEnvironment> findEnvironmentList(DictionaryEnvironment dictionaryEnvironment){

        List<DictionaryEnvironment> dictionaryEnvironments = dictionaryEnvironmentDao.findList(dictionaryEnvironment);

        return dictionaryEnvironments ;
    }

    public void saveEnvironment(DictionaryEnvironment dictionaryEnvironment){
            dictionaryEnvironmentDao.saveEnvironment(dictionaryEnvironment);
    }

    public void updateEnvironment(DictionaryEnvironment dictionaryEnvironment){
            dictionaryEnvironmentDao.updateEnvironment(dictionaryEnvironment);
    }

    public void deleteEnvironment(String id){
            dictionaryEnvironmentDao.deleteEnvironment(id);
    }


}
