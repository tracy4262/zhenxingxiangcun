package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryReportTypeDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryReportTypeService extends CrudService<DictionaryReportTypeDao, DictionaryReportType> {

    @Autowired
    private DictionaryReportTypeDao dictionaryReportTypeDao;

    /*public Page<DictionaryReportType> findList(DictionaryReportType dictionaryReportType, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryReportType);
    }*/

    public List<DictionaryReportType> findList(DictionaryReportType dictionaryReportType){
        return dictionaryReportTypeDao.findList(dictionaryReportType);
    }
    
    public List<DictionaryReportType> findReportTypeList(DictionaryReportType dictionaryReportType){

        List<DictionaryReportType> dictionaryReportTypes = dictionaryReportTypeDao.findList(dictionaryReportType);

        return dictionaryReportTypes ;
    }

    public void saveReportType(DictionaryReportType dictionaryReportType){
            dictionaryReportTypeDao.saveReportType(dictionaryReportType);
    }

    public void updateReportType(DictionaryReportType dictionaryReportType){
            dictionaryReportTypeDao.updateReportType(dictionaryReportType);
    }

    public void deleteReportType(String id){
            dictionaryReportTypeDao.deleteReportType(id);
    }


}
