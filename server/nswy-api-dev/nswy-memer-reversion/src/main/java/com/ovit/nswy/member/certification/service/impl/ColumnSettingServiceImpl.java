package com.ovit.nswy.member.certification.service.impl;

import com.ovit.nswy.member.certification.mapper.ColumnSettingMapper;
import com.ovit.nswy.member.certification.service.ColumnSettingService;
import com.ovit.nswy.member.certification.service.LoginUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ColumnSettingServiceImpl implements ColumnSettingService {

    @Autowired
    private ColumnSettingMapper columnSettingMapper;

    @Autowired
    private LoginUserService loginUserService;

    @Override
    public List<Map<String, Object>> findColumnSettingByAccount(Map<String,Object> params) {
        return columnSettingMapper.findColumnSettingByAccount(params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateSettingInfo(Map<String,Object> params) {

        String account = MapUtils.getString(params, "account");
        String templateId = MapUtils.getString(params, "templateId");
        //先删除用户数据
        columnSettingMapper.deleteSettingByAccount(params);
        //然后保存数据
        String templateType = MapUtils.getString(params, "templateType");
        List<Map<String,Object>> settingList = (List<Map<String, Object>>) params.get("columnSetting");
        for (int i = 0; i < settingList.size(); i++) {

            settingList.get(i).put("account", account);
            settingList.get(i).put("templateType", templateType);
            settingList.get(i).put("order", i+1);
            settingList.get(i).put("templateId", templateId);

        }
        columnSettingMapper.saveSettingInfo(settingList);

        Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

        //保存用户步骤
        loginUserService.saveOrUpdateStep(loginMap);

    }

    @Override
    public Map<String, Object> columnDict() {
        //先查第一级数据
        List<Map<String, Object>> list = columnSettingMapper.findColumnDict(0);
        for (Map<String,Object> map : list) {
            int id = MapUtils.getInteger(map, "id");
            List<Map<String, Object>> secondLevel = columnSettingMapper.findColumnDict(id);
            map.remove("id");
            if (CollectionUtils.isNotEmpty(secondLevel)) {
                for (Map<String,Object> sencond : secondLevel) {
                    sencond.remove("id");
                }
                map.put("children", secondLevel);
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("attributions", list);
        return resultMap;
    }
}
