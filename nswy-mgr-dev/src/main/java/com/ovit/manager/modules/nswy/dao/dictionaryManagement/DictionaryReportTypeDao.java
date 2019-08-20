package com.ovit.manager.modules.nswy.dao.dictionaryManagement;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryReportType;


@MyBatisDao
public interface DictionaryReportTypeDao extends CrudDao<DictionaryReportType> {

    void updateReportType(DictionaryReportType dictionaryReportType);

    void saveReportType(DictionaryReportType dictionaryReportType);

    void deleteReportType(String id);

}
