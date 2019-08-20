package com.ovit.nswy.member.perfectInfo.service.impl;

import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.OutputProductMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.OutputProductService;
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
public class OutputProductServiceImpl implements OutputProductService {

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private OutputProductMapper outputProductMapper;


    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 通过用户名查询用户的产出产品信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> find(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> outputList = outputProductMapper.find(params);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "产出产品");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if (CollectionUtils.isEmpty(outputList)) {
            outputList = new ArrayList<>();
            resultMap.put("list", outputList);
        } else {
            resultMap.put("list", outputList);
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }


    /**
     * 修改用户的产出产品信息
     * @param params
     * @return
     */
    @Override
    public void save(Map<String, Object> params) {

        if (StringUtils.isBlank(MapUtils.getString(params, "id"))) {
            params.remove("id");
        }
        outputProductMapper.save(params);

    }

    /**
     * 删除产出产品信息
     * @param params
     */
    @Override
    public void delete(Map<String, Object> params) {

        outputProductMapper.delete(params);

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
            } else{
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
