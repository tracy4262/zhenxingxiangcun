package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductionBaseService {
    int insertProductionBase(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> selectAll(Map<String, Object> params);

    Map<String,Object> selectDetailByProductId(Map<String, Object> params);

    Integer updateProductBase(Map<String, Object> params);

    Map<String,Object> queryByProductId(Map<String, Object> params);

    void delete(Map<String, Object> params);

    List<Map<String, Object>> queryByAccount(Map<String, Object> params);

    void update(Map<String, Object> params);

    List<Map<String, Object>> queryIsExist(Map<String, Object> params);
}
