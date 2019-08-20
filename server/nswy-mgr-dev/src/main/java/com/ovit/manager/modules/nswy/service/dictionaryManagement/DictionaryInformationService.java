package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryInformationDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryInformationService extends CrudService<DictionaryInformationDao, DictionaryInformation> {

    @Autowired
    private DictionaryInformationDao dictionaryInformationDao;

    /*public Page<DictionaryInformation> findList(DictionaryInformation dictionaryInformation, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryInformation);
    }*/

    public List<DictionaryInformation> findList(DictionaryInformation dictionaryInformation){
        return dictionaryInformationDao.findList(dictionaryInformation);
    }
    
    public List<DictionaryInformation> findInformationList(DictionaryInformation dictionaryInformation){

        List<DictionaryInformation> dictionaryInformations = dictionaryInformationDao.findList(dictionaryInformation);

        return dictionaryInformations ;
    }

    public void saveInformation(DictionaryInformation dictionaryInformation){
            dictionaryInformationDao.saveInformation(dictionaryInformation);
    }

    public void updateInformation(DictionaryInformation dictionaryInformation){
            dictionaryInformationDao.updateInformation(dictionaryInformation);
    }

    public void deleteInformation(String id){
            dictionaryInformationDao.deleteInformation(id);
    }


}
