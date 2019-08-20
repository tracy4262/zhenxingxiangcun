package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.Town;

import java.util.List;
import java.util.Map;

public interface SystemDictMapper {
    List<SysDict> querySystemDict(Map<String, Object> params);

    void setPinyin(Map<String, Object> map);

    List<Map<String,Object>> queryName();

    void insert(Map<String, Object> fMap);

    void saveSystemDictType(Map<String, Object> map);

    List<Map<String,Object>> queryByDictValue(List<String> dictValueList);


    List<Town> findAllSystemDict();

    List<Town> querySystemDictNextByParentId(String parentId);

    void insertServise(Map<String,Object> params);
}
