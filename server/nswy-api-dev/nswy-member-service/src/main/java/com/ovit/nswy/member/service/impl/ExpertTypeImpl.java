package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ExpertTypeMapper;
import com.ovit.nswy.member.mapper.SystemDictMapper;
import com.ovit.nswy.member.model.ExpertType;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.service.ExpertTypeService;
import com.ovit.nswy.member.service.SystemDictService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author haoWen
 * @create 2018-01-31 15:50
 **/
@Service
public class ExpertTypeImpl implements ExpertTypeService {
    @Autowired
    private ExpertTypeMapper expertTypeMapper;

    @Override
    public List<Map<String, Object>> findExpertType(Map<String, Object> params) {
        Map<String,Object> currency = new HashMap<>();
        currency.put("pageNum",1);
        currency.put("pageSize",10000000);

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> jsonList  = expertTypeMapper.findExpertType(currency);
        for(Map<String, Object> map :jsonList){
            Map<String,Object> variety= new HashMap<>();
            variety.put("label",MapUtils.getString(map,"label"));
            variety.put("value",MapUtils.getString(map,"value"));


            list.add(variety);
        }

        return list;
    }
}
