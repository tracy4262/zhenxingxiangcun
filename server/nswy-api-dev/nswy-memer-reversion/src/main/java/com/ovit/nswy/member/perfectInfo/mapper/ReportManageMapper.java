package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportManageMapper {

    List<Map<String,Object>> findReportType();

    List<Map<String,Object>> filterReport(Map<String, Object> params);

    List<Map<String,Object>> find(Map<String, Object> params);

    Map<String,Object> findCommon(Map<String, Object> params);

    void save(Map<String, Object> params);

    void saveCommon(Map<String, Object> params);

    void deleteCommon(Map<String, Object> params);

    void delete(Map<String, Object> params);

}
