package com.ovit.manager.modules.nswy.dao;


import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.IndustryInfo;

import java.util.List;

@MyBatisDao
public interface IndustryDao extends CrudDao<IndustryInfo> {

    List<IndustryInfo> findByPidDesc(IndustryInfo industryInfo);

    List<IndustryInfo> findByIdDesc(IndustryInfo industryInfo);

    void updateIndustry(IndustryInfo industryInfo);

    void saveIndustry(IndustryInfo industryInfo);

    void deleteIndustry(String Id);
}
