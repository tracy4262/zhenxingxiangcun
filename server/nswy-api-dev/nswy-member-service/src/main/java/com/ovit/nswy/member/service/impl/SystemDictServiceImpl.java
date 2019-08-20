package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.SystemDictMapper;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.service.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author haoWen
 * @create 2018-01-31 15:50
 **/
@Service
public class SystemDictServiceImpl implements SystemDictService{
    @Autowired
    private SystemDictMapper systemDictMapper;
    @Override
    public List<SysDict> querySystemDict(Map<String, Object> params) {
        return systemDictMapper.querySystemDict(params);
    }

    @Override
    public List<Map<String, Object>> queryName() {
        return systemDictMapper.queryName();
    }

    @Override
    public void setPinyin(Map<String, Object> map) {
        systemDictMapper.setPinyin(map);
    }

    @Override
    public void insert(Map<String, Object> fMap) {
        systemDictMapper.insert(fMap);
    }

    @Override
    public void saveSystemDictType(Map<String, Object> map) {
        systemDictMapper.saveSystemDictType(map);
    }

    /**
     * 根据value反查名称
     * @param dictValueList
     * @return
     */
    @Override
    public List<Map<String, Object>> queryByDictValue(List<String> dictValueList) {
        return systemDictMapper.queryByDictValue(dictValueList);
    }
    @Override
    public List<Town> findAllSystemDict() {
        return systemDictMapper.findAllSystemDict();
    }

    @Override
    public List<Town> querySystemDictNextByParentId(String parentId) {
        return systemDictMapper.querySystemDictNextByParentId(parentId);
    }

    @Override
    public void insertServise(Map<String, Object> map){
        systemDictMapper.insertServise(map);
    }
}
