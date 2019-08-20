package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.WorkExperienceService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作经历
 */
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    @Override
    public Map<String,Object> findWorkExperience(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("workExperience_name", "工作经历");
        } else {
            map.put("workExperience_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        List<Map<String,Object>> lists = workExperienceMapper.findWorkExperience(params);
        if(lists.size()>0){

            for(Map<String,Object> work:lists){
                work.put("status","1".equals(MapUtils.getString(work,"status"))? true : false);
                String work_time_model = MapUtils.getString(work,"work_time_model");
                if(StringUtil.isNotEmpty(work_time_model)) {
                    String[] members = work_time_model.split(";");
                    work.put("work_time_model",members);
                }else{
                    work.put("work_time_model", new String[]{});
                }
            }

            map.put("workExperience",lists);

        }else{


            map.put("workExperience",new ArrayList<>());

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
    public void updateWorkExperienceName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("work_experience_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = workExperienceMapper.findWorkExperience(map);
        if(list.size()>0){
            workExperienceMapper.updateWorkExperienceName(params);
        }else{
            workExperienceMapper.saveWorkExperience(params);
        }

    }



    @Override
    public void saveWorkExperience(Map<String,Object> params){
        Map<String,Object> workExperience = MapUtils.getMap(params,"workExperience");
        String work_time_model = MapUtils.getString(workExperience,"work_time_model");
        if(StringUtil.isNotEmpty(work_time_model)) {
            JSONArray topographic_model = JSONArray.parseArray(work_time_model);
            if (topographic_model.size() == 0) {
                workExperience.put("work_time_model", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(work_time_model);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                workExperience.put("work_time_model", memberMerge);
            }
        }else{
            workExperience.put("work_time_model", "");
        }

        String user_id = MapUtils.getString(params,"user_id");
        workExperience.put("user_id",user_id);
        workExperience.put("work_experience_name",MapUtils.getString(params,"workExperience_name"));
        workExperience.put("year_id",MapUtils.getString(params,"yearId"));
        workExperience.put("templateId",MapUtils.getString(params,"templateId"));
        workExperienceMapper.saveWorkExperience(workExperience);

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


    @Override
    public void deleteWorkExperience(Map<String,Object> params){
        workExperienceMapper.deleteWorkExperience(params);
    }




}
