package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.ReportManageService;
import com.ovit.nswy.member.perfectInfo.service.WantBuyService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务信息--报表管理
 */
@RestController
@RequestMapping("/reportManage")
public class ReportManageController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ReportManageService reportManageService;

    /**
     * 报表类型数据查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findReportType", method = RequestMethod.POST)
    public Result findReportType() {
        Result result = new Result();
        try {
            List<Map<String, Object>> reportType = reportManageService.findReportType();
            if (CollectionUtils.isEmpty(reportType)) {
                reportType = new ArrayList<>();
            }
            result.setMsg("报表类型数据查询成功");
            result.setData(reportType);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 通过报表类型、用户类型筛选报表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/filterReport", method = RequestMethod.POST)
    public Result filterReport(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> reportType = reportManageService.filterReport(params);
            if (CollectionUtils.isEmpty(reportType)) {
                reportType = new ArrayList<>();
            }
            result.setMsg("报表数据筛选成功");
            result.setData(reportType);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 报表查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> report = reportManageService.find(params);
            result.setMsg("报表数据查询成功");
            result.setData(report);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-报表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            reportManageService.save(params);
            result.setMsg("报表数据保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
