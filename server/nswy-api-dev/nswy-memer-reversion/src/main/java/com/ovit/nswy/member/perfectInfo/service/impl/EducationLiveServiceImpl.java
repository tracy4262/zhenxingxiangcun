package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.EducationLiveMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.EducationLiveService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 教育经历
 */
@Service
public class EducationLiveServiceImpl implements EducationLiveService {

    @Autowired
    private EducationLiveMapper  educationLiveMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    @Override
    public Map<String,Object> findEducationLive(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("educationLive_name", "教育经历");
        } else {
            map.put("educationLive_name", MapUtils.getString(propertyInfo, "propertyName"));
        }


        // 教育经历
        List<Map<String,Object>> educations = educationLiveMapper.findEducationLive(params);

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        if(educations.size()>0){


            for(Map<String,Object> education:educations){

                education.put("status","1".equals(MapUtils.getString(education,"status")) ? true : false);

                String memberPicture = MapUtils.getString(education,"entrance_graduation_time_model");
                if(StringUtil.isNotEmpty(memberPicture)){
                    String[] members = memberPicture.split(";");
                    education.put("entrance_graduation_time_model",members);
                }else{
                    education.put("entrance_graduation_time_model", new String[]{});
                }

            }

            map.put("educationLive",educations);

        }else{

            map.put("educationLive",new ArrayList<>());

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

        if(MapUtils.isEmpty(textPreview)){
            Map<String,Object> text = new HashMap<>();
            text.put("text_preview","");
            text.put("sys_dict_id","");
            text.put("user_id","");
            text.put("year_id","");
            text.put("is_complete",0);
            map.put("textPreview",text);
        }else{
            map.put("textPreview",textPreview);
        }


        return map;
    }

    @Override
    public void updateEducationalLiveName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("education_live_name",MapUtils.getString(params,"propertyName"));
        params.put("user_id",MapUtils.getString(params,"account"));
        List<Map<String,Object>> educationalLive = educationLiveMapper.findEducationLive(map);

        if(educationalLive.size()>0){
            educationLiveMapper.updateEducationalLiveName(params);
        }else{
            educationLiveMapper.insertEducational(params);
        }
    }

    @Override
    public void saveEducationLive(Map<String,Object> params){
        //教育经历

        Map<String,Object> educational = (Map<String, Object>) params.get("educationLive");
        String memberPicture = MapUtils.getString(educational,"entrance_graduation_time_model");
        if(StringUtil.isNotEmpty(memberPicture)) {
            JSONArray entrance_graduation_time_model = JSONArray.parseArray(memberPicture);
            if (entrance_graduation_time_model.size() == 0) {
                educational.put("entrance_graduation_time_model", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(memberPicture);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                educational.put("entrance_graduation_time_model", memberMerge);
            }
        }else{
            educational.put("entrance_graduation_time_model", "");
        }
        String user_id = MapUtils.getString(params,"user_id");
        educational.put("user_id",user_id);
        educational.put("education_live_name",MapUtils.getString(params,"educationLive_name"));
        educational.put("year_id",MapUtils.getString(params,"yearId"));
        educational.put("templateId",MapUtils.getString(params,"templateId"));
        educationLiveMapper.insertEducational(educational);
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
    public void deleteEducationalExperience(Map<String,Object> params){
        educationLiveMapper.deleteEducationalExperience(params);
    }
}
