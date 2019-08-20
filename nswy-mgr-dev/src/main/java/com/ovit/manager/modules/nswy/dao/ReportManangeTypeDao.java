package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageTypeInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ReportManangeTypeDao extends CrudDao<ReportManageTypeInfo> {

    void savePicture(Map<String, Object> map);

    Map<String, Object>  findReportTypeMax(Map<String, Object> params);

    void deleteReportType(Integer id);

    void saveReportType(Map<String, Object> map);

    ReportManageTypeInfo  findReportType(Integer reportId);


    void updateType(ReportManageTypeInfo reportManageTypeInfo);




}
