package com.ovit.nswy.member.perfectInfo.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.PoliticalFaceService;
import com.ovit.nswy.member.perfectInfo.service.WorkExperienceService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 政治面貌
 */
@Service
public class PoliticalFaceServiceImpl implements PoliticalFaceService {

    @Autowired
    private PoliticalFaceMapper politicalFaceMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;


    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;


    @Override
    public Map<String,Object> findPoliticalFace(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("politicalFace_name", "政治面貌");
        } else {
            map.put("politicalFace_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        Map<String,Object> lists = politicalFaceMapper.findPoliticalFace(params);
        if(MapUtils.isNotEmpty(lists)){

            map.put("status","1".equals(MapUtils.getString(lists,"status"))? true : false);

            map.put("politicalFace",lists);

        }else{

            map.put("status",true);

            map.put("politicalFace",new HashMap<>());


        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id",MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id", MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            textPreview =   physicalGeographyMapper.findTextPreview(historyInfo);
        }

        if(MapUtils.isNotEmpty(textPreview)){
            map.put("textPreview",textPreview);
        }else{
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);
        }

        return map;
    }

    @Override
    public void updatePoliticalFaceName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("political_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> list = politicalFaceMapper.findPoliticalFace(map);
        if(MapUtils.isNotEmpty(list)){
            politicalFaceMapper.updatePoliticalFaceName(params);
        }else{
            politicalFaceMapper.savePoliticalFace(params);
        }

    }



    @Override
    public void savePoliticalFace(Map<String,Object> params){
        Map<String,Object> politicalFace = MapUtils.getMap(params,"politicalFace");
        String user_id = MapUtils.getString(params,"user_id");
        politicalFace.put("user_id",user_id);
        politicalFace.put("political_name",MapUtils.getString(params,"politicalFace_name"));
        politicalFace.put("year_id",MapUtils.getString(params,"yearId"));
        politicalFace.put("templateId",MapUtils.getString(params,"templateId"));
        politicalFace.put("status",MapUtils.getBoolean(params,"status"));
        politicalFaceMapper.savePoliticalFace(politicalFace);

        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveTextPreview(textPreview);
        }

    }

    @Override
    public void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveTextPreview(textPreview);
        }
    }


}
