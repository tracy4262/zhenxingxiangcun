package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.Town;

import java.util.List;
import java.util.Map;

public interface SystemDictService {
    List<SysDict> querySystemDict(Map<String, Object> params);

    List<Map<String,Object>> queryName();

    void setPinyin(Map<String, Object> map);

    void insert(Map<String, Object> fMap);

    void saveSystemDictType(Map<String, Object> map);

    List<Map<String,Object>> queryByDictValue(List<String> strings);

    List<Town> findAllSystemDict();

    List<Town> querySystemDictNextByParentId(String parentId);

    void insertServise(Map<String, Object> map);
}
