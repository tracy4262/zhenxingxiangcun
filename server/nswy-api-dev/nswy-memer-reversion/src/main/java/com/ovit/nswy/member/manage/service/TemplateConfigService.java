package com.ovit.nswy.member.manage.service;

import java.util.List;
import java.util.Map;

public interface TemplateConfigService {

    void save(Map<String,Object> params);

    List<Map<String,Object>> find(Map<String,Object> params);

    void delete(Map<String, Object> params);

    List<Map<String,Object>> getUserType();

    List<Map<String,Object>> isExist(Map<String,Object> params);

}
