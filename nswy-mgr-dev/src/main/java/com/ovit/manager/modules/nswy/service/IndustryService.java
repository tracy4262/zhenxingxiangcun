package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.IndustryDao;
import com.ovit.manager.modules.nswy.entity.IndustryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndustryService extends CrudService<IndustryDao,IndustryInfo> {

    @Autowired
    private IndustryDao industryDao;

    public List<IndustryInfo> findList(IndustryInfo industryInfo) {
        return industryDao.findList(industryInfo);
    }

    public List<IndustryInfo> findByPidDesc(IndustryInfo industryInfo){
        return industryDao.findByPidDesc(industryInfo);
    }

    public List<IndustryInfo> findByIdDesc(IndustryInfo industryInfo){
        return industryDao.findByIdDesc(industryInfo);
    }

    public void updateIndustry(IndustryInfo industryInfo){
        industryDao.updateIndustry(industryInfo);
    }

    public void saveIndustry(IndustryInfo industryInfo){
        industryDao.saveIndustry(industryInfo);
    }

    public void deleteIndustry(String id){industryDao.deleteIndustry(id);}
}
