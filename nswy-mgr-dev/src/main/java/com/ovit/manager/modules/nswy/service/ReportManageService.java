package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.PictureDao;
import com.ovit.manager.modules.nswy.dao.ReportManangeDao;
import com.ovit.manager.modules.nswy.entity.PictureInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ReportManageService extends CrudService<ReportManangeDao, ReportManageInfo> {

    @Autowired
    private ReportManangeDao reportManangeDao;

    public Page<ReportManageInfo> findKnowledgeList(ReportManageInfo reportManageInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),reportManageInfo);
    }

    public void savePicture(Map<String, Object> map) {
        reportManangeDao.savePicture(map);
    }

    public List<Map<String,Object>> findReportType(Map<String, Object> params){
        return reportManangeDao.findReportType(params);
    }

    public void delete(int id){
        reportManangeDao.deleteReport(id);
    }

    public ReportManageInfo findReport(Integer id){
        return reportManangeDao.findReport(id);
    }

    public void updateReport(Map<String, Object> map){
        reportManangeDao.updateReport(map);
    }

}
