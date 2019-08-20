package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.CultureSightMapper;
import com.ovit.nswy.member.perfectInfo.mapper.EnvConditionMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.CultureSightService;
import com.ovit.nswy.member.perfectInfo.service.EnvConditionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CultureSightServiceImpl implements CultureSightService {

    @Autowired
    private EnvConditionMapper envConditionMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private CultureSightMapper cultureSightMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private TemplateConfigService templateConfigService;



    /**
     * 通过用户名查询用户的风景名胜设施信息  初始化数据
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findSceneryByAccount(Map<String, Object> params) {

        //List<Map<String,Object>> subModule = perfectBasicMapper.findSubModule(params);

        List<Map<String,Object>> subModule = perfectBasicService.findSubModule(params);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("subModule", subModule);
//        List<Map<String, Object>> sceneryList = cultureSightMapper.findSceneryByAccount(params);
//        resultMap.put("defaultData", sceneryList);
//        resultMap.put("propertyName", MapUtils.getString(sceneryList.get(0), "propertyName"));
//        //获取预览信息
//        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public Map<String, Object> findFamousScenery(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> sceneryList = cultureSightMapper.findSceneryByAccount(params);
        for (Map<String, Object> map : sceneryList) {
            ConvertDataUtil.merge(map, "uploadImg");
        }
        //获取属性名称
        //Map<String,Object> propertyInfo = cultureSightMapper.findPropertyInfo(params);
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(sceneryList)) {
            sceneryList = new ArrayList<>();
            resultMap.put("defaultData", sceneryList);
            //resultMap.put("propertyName", "");
        } else {
            resultMap.put("defaultData", sceneryList);
            //resultMap.put("propertyName", MapUtils.getString(sceneryList.get(0), "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updatePropertyInfo(Map<String, Object> params) {

        Map<String,Object> propertyMap = cultureSightMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyMap)) {
            //插入
            cultureSightMapper.insertPropertyInfo(params);
        } else {
            //更新
            cultureSightMapper.updatePropertyInfo(params);
        }

        /*List<Map<String,Object>> sceneryList = cultureSightMapper.findSceneryByAccount(params);
        if(sceneryList.size()>0){
            cultureSightMapper.updateSceneryName(params);
        }else{
            cultureSightMapper.saveSceneryName(params);
        }*/
    }

    /**
     * 修改用户的风景名胜设施信息
     * @param params
     * @return
     */
    @Override
    public void saveScenery(Map<String, Object> params) {

        ConvertDataUtil.split(params, "uploadImg");
        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.remove("id");
        }
        cultureSightMapper.saveSceneryName(params);

    }

    @Override
    public void saveCultural(Map<String, Object> params) {

        ConvertDataUtil.split(params, "uploadImg");
        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.remove("id");
        }
        cultureSightMapper.saveCulturalName(params);

    }

    @Override
    public Map<String, Object> findCulturalHeritage(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> culturalList = cultureSightMapper.findCulturalHeritage(params);
        for (Map<String, Object> map : culturalList) {
            ConvertDataUtil.merge(map, "uploadImg");
        }
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        //Map<String,Object> propertyInfo = cultureSightMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(culturalList)) {
            culturalList = new ArrayList<>();
            resultMap.put("defaultData", culturalList);
        } else {
            resultMap.put("defaultData", culturalList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void updateCulturalName(Map<String, Object> params) {

        List<Map<String,Object>> culturalList = cultureSightMapper.findCulturalHeritage(params);
        if(culturalList.size()>0){
            cultureSightMapper.updateCulturalName(params);
        }else{
            cultureSightMapper.saveCulturalName(params);
        }

    }

    @Override
    public void saveTextPreview(Map<String, Object> params) {

        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }
    }

    @Override
    public void deleteScenery(Map<String, Object> params) {

        cultureSightMapper.deleteScenery(params);

    }

    @Override
    public void deleteCultural(Map<String, Object> params) {

        cultureSightMapper.deleteCultural(params);

    }

    @Override
    public Map<String, Object> findPropertyInfo(Map<String, Object> params) {

        return cultureSightMapper.findPropertyInfo(params);

    }


    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        Map<String,Object> textPreview = perfectBasicMapper.findTextPreview(params);

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account",MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
        } else {
            result.put("preview", "");
        }

        return result;
    }


}
