package com.ovit.nswy.member.certification.service;

import java.util.List;
import java.util.Map;

public interface ColumnSettingService {

    List<Map<String, Object>> findColumnSettingByAccount(Map<String,Object> params);

    void saveOrUpdateSettingInfo(Map<String,Object> params);

    Map<String,Object> columnDict();
}
