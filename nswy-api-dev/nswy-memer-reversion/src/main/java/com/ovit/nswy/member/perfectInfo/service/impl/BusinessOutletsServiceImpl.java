package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.BusinessOutletsMapper;
import com.ovit.nswy.member.perfectInfo.mapper.ExpertInfoMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.BusinessOutletsService;
import com.ovit.nswy.member.perfectInfo.service.ExpertInfoService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 营业网点
 */
@Service
public class BusinessOutletsServiceImpl implements BusinessOutletsService {

    @Autowired
    private BusinessOutletsMapper businessOutletsMapper;


    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    @Override
    public Map<String,Object> findBusinessOutletsInfo(Map<String,Object> params){

        Map<String, Object> resultMap = new HashMap<>();
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "营业网点");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }


        List<Map<String,Object>> list = businessOutletsMapper.findBusinessOutletsInfo(params);

        if(CollectionUtils.isNotEmpty(list)){

            for(Map<String,Object> map:list){
                String networkType = MapUtils.getString(map, "networkType");
                if (StringUtils.isBlank(networkType)) {

                    map.put("networkType", new String[0]);
                } else {
                    String[] work = networkType.split(";");
                    map.put("networkType", work);
                }
                map.put("status","1".equals(MapUtils.getString(map,"status"))? true : false);
            }



            resultMap.put("BusinessOutlets",list);

        }else{

            resultMap.put("BusinessOutlets",new ArrayList<>());

        }

        //获取预览信息
        getPreviewInfo(resultMap, params);

        return resultMap;
    }

    @Override
    public void deleteBusinessOutlets(Map<String,Object> params){

        businessOutletsMapper.deleteBusinessOutlets(params);

    }



    @Override
    public void saveOrUpdateBusinessOutlets(Map<String,Object> params){
        //Map<String,Object> businessOutlets = (Map<String, Object>) params.get("BusinessOutlets");

        String networkType = MapUtils.getString(params,"networkType");
        if(StringUtil.isNotEmpty(networkType)) {
            JSONArray network = JSONArray.parseArray(networkType);
            if (network.size() == 0) {
                params.put("networkType", "");
            } else {
                String[] works = ImageUtil.getJsonToStringArray(networkType);
                String workType = ImageUtil.stringArrayMerge(works);
                params.put("networkType", workType);
            }
        }else{
            params.put("networkType", "");
        }


        businessOutletsMapper.saveOrUpdateBusinessOutlets(params);



    }

    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"preview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("account",MapUtils.getString(params,"account"));
            textPreview.put("yearId",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));

           Map<String,Object> text =  perfectBasicMapper.findTextPreview(textPreview);
           if(MapUtils.isEmpty(text)){
               perfectBasicMapper.saveTextPreview(textPreview);
           }else {
               perfectBasicMapper.updateTextPreview(textPreview);
           }

        }
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
            //result.put("status", "1".equals(MapUtils.getString(textPreview,"status"))? true : false);
        } else {
            result.put("preview", "");
            //result.put("status", true);
        }

        return result;
    }


}
