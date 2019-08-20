package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.EnvConditionMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.EnvConditionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnvConditionServiceImpl implements EnvConditionService {

    @Autowired
    private EnvConditionMapper envConditionMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 空气质量状况判断标准表
     * @return
     */
    @Override
    public List<Map<String, Object>> airQuaStandard() {
        return envConditionMapper.airQuaStandard();
    }

    /**
     * 通过用户名查询用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findAirQuaByAccount(Map<String, Object> params) {

        Map<String, Object> envMap = envConditionMapper.findAirQuaByAccount(params);
        if (MapUtils.isEmpty(envMap)) {
            envMap = new HashMap<>();
        }
        ConvertDataUtil.merge(envMap, "detectReport");
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            envMap.put("propertyName", "");
        } else {
            envMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(envMap, params);
        return envMap;
    }

    @Override
    public Map<String, Object> findAirQuaByAccountList(Map<String, Object> params) {

        Map<String, Object> envMap = envConditionMapper.findAirQuaByAccount(params);

        return envMap;
    }


    @Override
    public void updateAirQuaName(Map<String, Object> params) {
        Map<String,Object> airQuaMap = envConditionMapper.findAirQuaByAccount(params);

        if (MapUtils.isEmpty(airQuaMap)) {
            //插入
            envConditionMapper.insertAirQuaByAccount(params);

        } else {
            //更新
            envConditionMapper.modifyAirQuaByAccount(params);

        }
    }

    /**
     * 修改用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public void modifyAirQuaByAccount(Map<String, Object> params) {

        split(params, "detectReport");
        envConditionMapper.modifyAirQuaByAccount(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }

    }

    /**
     * 新增用户的空气质量信息
     * @param params
     * @return
     */
    @Override
    public void insertAirQuaByAccount(Map<String, Object> params) {

        split(params, "detectReport");
        envConditionMapper.insertAirQuaByAccount(params);
        //保存预览、完善程度等信息
        perfectBasicMapper.saveTextPreview(params);

    }

    /**
     * 通过用户名查询用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findSurfaceWaterQua(Map<String, Object> params) {

        Map<String, Object> surfaceMap = envConditionMapper.findSurfaceWaterQua(params);
        if (MapUtils.isEmpty(surfaceMap)) {
            surfaceMap = new HashMap<>();
        }
        ConvertDataUtil.merge(surfaceMap, "detectReport");
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            surfaceMap.put("propertyName", "");
        } else {
            surfaceMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(surfaceMap, params);
        return surfaceMap;
    }

    @Override
    public Map<String, Object> findSurfaceWaterQuaList(Map<String, Object> params) {

        Map<String, Object> surfaceMap = envConditionMapper.findSurfaceWaterQua(params);

        return surfaceMap;
    }

    /**
     * 修改用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public void modifySurfaceWaterQua(Map<String, Object> params) {

        split(params, "detectReport");
        split(params, "waterQuality");
        envConditionMapper.modifySurfaceWaterQua(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }

    }

    /**
     * 新增用户的地表水质量信息
     * @param params
     * @return
     */
    @Override
    public void insertSurfaceWaterQua(Map<String, Object> params) {

        split(params, "detectReport");
        split(params, "waterQuality");
        envConditionMapper.insertSurfaceWaterQua(params);
        perfectBasicMapper.saveTextPreview(params);

    }

    @Override
    public List<Map<String, Object>> findWaterStandard() {
        return envConditionMapper.findWaterStandard();
    }

    @Override
    public List<Map<String, Object>> findEnvStandard() {
        return envConditionMapper.findEnvStandard();
    }

    /**
     * 通过用户名查询用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findEcologyEnvQua(Map<String, Object> params) {

        Map<String, Object> ecologyMap = envConditionMapper.findEcologyEnvQua(params);
        if (MapUtils.isEmpty(ecologyMap)) {
            ecologyMap = new HashMap<>();
        }
        ConvertDataUtil.merge(ecologyMap, "detectReport");
        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            ecologyMap.put("propertyName", "");
        } else {
            ecologyMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(ecologyMap, params);
        return ecologyMap;
    }

    @Override
    public Map<String, Object> findEcologyEnvQuaList(Map<String, Object> params) {

        Map<String, Object> ecologyMap = envConditionMapper.findEcologyEnvQua(params);

        return ecologyMap;
    }

    /**
     * 修改用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public void modifyEcologyEnvQua(Map<String, Object> params) {

        split(params, "detectReport");
        split(params, "ecologyEnv");
        envConditionMapper.modifyEcologyEnvQua(params);
        //更新预览、完善程度等信息
        Map<String,Object> previewMap = perfectBasicMapper.findTextPreview(params);
        if (MapUtils.isEmpty(previewMap)) {
            //插入
            perfectBasicMapper.saveTextPreview(params);
        } else {
            perfectBasicMapper.updateTextPreview(params);
        }

    }

    /**
     * 新增用户的生态环境质量信息
     * @param params
     * @return
     */
    @Override
    public void insertEcologyEnvQua(Map<String, Object> params) {

        split(params, "detectReport");
        split(params, "ecologyEnv");
        envConditionMapper.insertEcologyEnvQua(params);
        perfectBasicMapper.saveTextPreview(params);

    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        if (MapUtils.isEmpty(result)) {
            result = new HashMap<>();
        }
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
            result.put("textPreview", MapUtils.getString(textPreview, "textPreview"));
        }else {
            result.put("textPreview", "");
        }

        return result;
    }

    /**
     * 拆分数组
     * @param params
     * @return
     */
    private Map<String,Object> split(Map<String, Object> params, String key) {

        String data = MapUtils.getString(params, key);
        if (StringUtils.isNotBlank(data)) {
            JSONArray dataArray = JSONArray.parseArray(data);
            if (dataArray.size() == 0) {
                params.put(key, "");
            } else {
                String[] datas = ConvertDataUtil.getJsonToStringArray(data);
                String dataMerge = ConvertDataUtil.stringArrayMerge(datas);
                params.put(key, dataMerge);
            }
        } else {
            params.put(key, "");
        }

        return params;
    }


}
