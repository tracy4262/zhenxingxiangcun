package com.ovit.nswy.member.perfectInfo.service.impl;

import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.EcoSocialDevelopMapper;
import com.ovit.nswy.member.perfectInfo.mapper.LandSituationMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.EcoSocialDevelopService;
import com.ovit.nswy.member.perfectInfo.service.LandSituationService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
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
public class LandSituationServiceImpl implements LandSituationService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private LandSituationMapper landSituationMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 通过用户名查询用户的土地利用现状信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> find(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //农用地
        params.put("type", "1");
        List<Map<String, Object>> agricultural = landSituationMapper.find(params);
        if (CollectionUtils.isEmpty(agricultural)) {
            agricultural = new ArrayList<>();
        }
        resultMap.put("agricultural", agricultural);
        //建设用地
        params.put("type", "2");
        List<Map<String, Object>> construction = landSituationMapper.find(params);
        if (CollectionUtils.isEmpty(construction)) {
            construction = new ArrayList<>();
        }
        resultMap.put("construction", construction);
        //未利用地
        params.put("type", "3");
        List<Map<String, Object>> future = landSituationMapper.find(params);
        if (CollectionUtils.isEmpty(future)) {
            future = new ArrayList<>();
        }
        resultMap.put("future", future);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "土地利用信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }


    /**
     * 修改用户的土地利用现状信息
     * @param params
     * @return
     */
    @Override
    public void save(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }

        landSituationMapper.save(list);

    }

    /**
     * 删除土地利用现状信息
     * @param params
     */
    @Override
    public void delete(Map<String, Object> params) {

        landSituationMapper.delete(params);

    }

    /**
     * 土地利用现状分类字典
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> dict(Map<String, Object> params) {
        return landSituationMapper.dict(params);
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
                historyInfo.put("user_id",MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id", MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
            result.put("status", MapUtils.getInteger(textPreview, "status"));
        } else {
            result.put("preview", "");
            result.put("status", 1);
        }

        return result;
    }


}
