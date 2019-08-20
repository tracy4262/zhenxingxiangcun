package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.PostDao;
import com.ovit.manager.modules.nswy.dao.TechnicalTitleDao;
import com.ovit.manager.modules.nswy.entity.PostInfo;
import com.ovit.manager.modules.nswy.entity.TechnicalTitleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 * @author wangrongf
 * @create 2018-02-09 10:35
 **/
@Service
@Transactional
public class TechnicalTitleService extends CrudService<TechnicalTitleDao, TechnicalTitleInfo> {

    @Autowired
    private TechnicalTitleDao technicalTitleDao;


    public List<TechnicalTitleInfo> findTitleInfo(TechnicalTitleInfo technicalTitleInfo){
        return technicalTitleDao.findList(technicalTitleInfo);
    }

    public void saveTitle(TechnicalTitleInfo technicalTitleInfo){
        technicalTitleDao.saveTitle(technicalTitleInfo);
    }



    public void updateTitle(TechnicalTitleInfo technicalTitleInfo){
        technicalTitleDao.updateTitle(technicalTitleInfo);
    }



    public void deleteTitle(String id){
        technicalTitleDao.deleteTitle(id);
    }

}
