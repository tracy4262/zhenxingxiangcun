package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface ReportManageService {

    /**
     * 报表类型数据查询
     * @return
     */
    List<Map<String,Object>> findReportType();

    /**
     * 通过报表类型、用户类型筛选报表数据
     * @return
     */
    List<Map<String,Object>> filterReport(Map<String, Object> params);

    /**
     * 报表信息查询
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 修改报表数据
     * @param params
     * @return
     */
    void save(Map<String, Object> params);

}
