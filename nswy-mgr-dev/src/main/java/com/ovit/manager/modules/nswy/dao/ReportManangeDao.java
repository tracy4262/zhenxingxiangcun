package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ReportManangeDao extends CrudDao<ReportManageInfo> {

    void savePicture(Map<String, Object> map);

    List<Map<String,Object>> findReportType(Map<String,Object> params);

    void deleteReport(Integer id);

    void saveReportType(Map<String, Object> map);

   ReportManageInfo findReport(Integer reportId);

   void updateReport(Map<String, Object> map);





}
