package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ReportManangeDao;
import com.ovit.manager.modules.nswy.dao.ReportManangeTypeDao;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageTypeInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author haoWen
 * @create 2018-02-09 10:35
 **/
@Service
@Transactional
public class ReportManageTypeService extends CrudService<ReportManangeTypeDao, ReportManageTypeInfo> {

    @Autowired
    private ReportManangeTypeDao reportManangeTypeDao;

    public Page<ReportManageTypeInfo> findKnowledgeList(ReportManageTypeInfo reportManageTypeInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),reportManageTypeInfo);
    }

    public void savePicture(Map<String, Object> map) {
        Map<String,Object> reportTypeMap  = reportManangeTypeDao.findReportTypeMax(new HashMap<>());
        Integer reportType = 0;
        if(MapUtils.isNotEmpty(reportTypeMap)){
            String typeMax =  MapUtils.getString(reportTypeMap,"reportType");
            reportType = Integer.valueOf(typeMax)+1;
        }
        map.put("reportType",reportType);
        reportManangeTypeDao.saveReportType(map);
    }

    public void updateType(ReportManageTypeInfo reportManageTypeInfo) {

        reportManangeTypeDao.updateType(reportManageTypeInfo);
    }

    public ReportManageTypeInfo findReportType(Integer reportId){
        return reportManangeTypeDao.findReportType(reportId);
    }



    public void delete(int id){
        reportManangeTypeDao.deleteReportType(id);
    }

}
