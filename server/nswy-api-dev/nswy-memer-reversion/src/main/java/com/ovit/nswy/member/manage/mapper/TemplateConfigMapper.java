package com.ovit.nswy.member.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateConfigMapper {

    List<Map<String, Object>> find(Map<String, Object> params);

    void save(Map<String, Object> params);

    void delete(Map<String, Object> params);

    List<Map<String, Object>> getUserType();

    List<Map<String,Object>> isExist(Map<String,Object> params);

}