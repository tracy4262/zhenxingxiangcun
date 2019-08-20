package com.ovit.nswy.member.perfectInfo.service.impl;

import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.ReportManageMapper;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.perfectInfo.service.ReportManageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportManageServiceImpl implements ReportManageService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private ReportManageMapper reportManageMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Override
    public List<Map<String, Object>> findReportType() {
        return reportManageMapper.findReportType();
    }

    @Override
    public List<Map<String, Object>> filterReport(Map<String, Object> params) {

        //先查询历史保存数据
        List<Map<String,Object>> historyReport = reportManageMapper.find(params);
        if (CollectionUtils.isEmpty(historyReport)) {
            historyReport = new ArrayList<>();
        }
        List<String> idList = new ArrayList<>();
        for (Map<String,Object> history : historyReport) {
            idList.add(MapUtils.getString(history, "id"));
        }

        if (CollectionUtils.isEmpty(historyReport)) {
            //查询管理员模板配置中的报表
            String templateId = MapUtils.getString(params,"templateId");
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", templateId);
            List<Map<String, Object>> list = templateConfigService.find(historyInfo);
            if (CollectionUtils.isNotEmpty(list)) {
                historyInfo.put("templateId", templateId);
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
                List<Map<String,Object>> manageList = reportManageMapper.find(historyInfo);
                if (CollectionUtils.isNotEmpty(manageList)) {
                    for (Map<String,Object> manage : manageList) {
                        idList.add(MapUtils.getString(manage, "id"));
                        manage.put("reports", "");
                        manage.put("reportsName", "");
                        manage.put("uploadPreview", "");
                        historyReport.add(manage);
                    }
                }
            }
        }

        List<Map<String,Object>> filterReport = reportManageMapper.filterReport(params);
        for (Map<String,Object> filter : filterReport) {
            String id = MapUtils.getString(filter, "id");
            if (!idList.contains(id)) {
                filter.put("reports", "");
                filter.put("reportsName", "");
                filter.put("uploadPreview", "");
                historyReport.add(filter);
            }
        }

        return historyReport;
    }

    /**
     * 报表信息查询
     * @param params
     * @returnrefreshDocument
     */
    @Override
    public Map<String, Object> find(Map<String, Object> params) {
        String templateId = MapUtils.getString(params,"templateId");
        Map<String, Object> historyInfo = new HashMap<>();
        historyInfo.put("id", templateId);
        List<Map<String, Object>> list = templateConfigService.find(historyInfo);
        if(list.size()>0){
            historyInfo.put("account",MapUtils.getString(list.get(0),"account"));
        }else{
            historyInfo.put("account",MapUtils.getString(params,"account"));
        }
        historyInfo.put("templateId", templateId);
        historyInfo.put("dictId", MapUtils.getString(params, "dictId"));
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> reportList = reportManageMapper.find(params);
        if (reportList.isEmpty()) {
            reportList = reportManageMapper.find(historyInfo);

        }
        if (CollectionUtils.isEmpty(reportList)) {
            reportList = new ArrayList<>();
            resultMap.put("reportName", new String[0]);
        } else {
            Integer[] reportArray = new Integer[reportList.size()];
            for (int i = 0; i < reportList.size(); i++) {
                Map<String,Object> map = reportList.get(i);
                int id = MapUtils.getInteger(map, "id");
                reportArray[i] = id;
            }
            resultMap.put("reportName", reportArray);

        }
        resultMap.put("list", reportList);
        //查询公用部分数据
        Map<String, Object> commonMap = reportManageMapper.findCommon(params);
        if (MapUtils.isEmpty(commonMap)) {
            commonMap = reportManageMapper.findCommon(historyInfo);
        }

        if (MapUtils.isEmpty(commonMap)) {
            resultMap.put("status", true);
        } else {
            resultMap.put("status", "1".equals(MapUtils.getString(commonMap, "status")) ? true : false);
        }
        resultMap.put("reportType", MapUtils.getString(commonMap, "reportType"));
        resultMap.put("suitable", MapUtils.getString(commonMap, "suitable"));
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {

            perfectBasicService.findPropertyInfo(historyInfo);
        }

        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "报表管理");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }

        //获取预览信息
        getPreviewInfo(resultMap, params);

        return resultMap;
    }


    /**
     * 修改报表数据
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        String templateId = MapUtils.getString(params, "templateId");
        boolean status = MapUtils.getBoolean(params, "status");
        //报表类型
        String reportType = MapUtils.getString(params, "reportType");
        //适用对象
        String suitable = MapUtils.getString(params, "suitable");
        //年度文件ID
        String yearId = MapUtils.getString(params, "yearId");

        Map<String,Object> commonMap = new HashMap<>();
        commonMap.put("account", account);
        commonMap.put("templateId", templateId);
        commonMap.put("yearId", yearId);

        //先删除原有数据
        reportManageMapper.delete(commonMap);
        List<Map<String,Object>> reportList = (List<Map<String, Object>>) params.get("list");
        if (CollectionUtils.isNotEmpty(reportList)) {
            for (Map<String,Object> map : reportList) {
                map.putAll(commonMap);
                //然后新增数据
                reportManageMapper.save(map);
            }
        }
        //先删除公用部分数据
        reportManageMapper.deleteCommon(commonMap);
        //保存公用部分数据
        Map<String,Object> saveCommon = new HashMap<>();
        saveCommon.putAll(commonMap);
        saveCommon.put("reportType", reportType);
        saveCommon.put("suitable", suitable);
        saveCommon.put("status", status);
        reportManageMapper.saveCommon(saveCommon);

        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }

    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        Map<String,Object> textPreview = perfectBasicMapper.findTextPreview(params);

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account", MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
        } else {
            result.put("preview", "");
        }

        return result;
    }




}
