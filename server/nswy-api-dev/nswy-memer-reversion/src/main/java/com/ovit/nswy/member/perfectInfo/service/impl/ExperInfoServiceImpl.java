package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.ExpertInfoService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专业信息
 */
@Service
public class ExperInfoServiceImpl implements ExpertInfoService {

    @Autowired
    private ExpertInfoMapper expertInfoMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    @Override
    public Map<String,Object> findExpertInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("expertInfo_name", "专业信息");
        } else {
            map.put("expertInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        Map<String,Object> lists = expertInfoMapper.findExpertInfo(params);
        if(MapUtils.isNotEmpty(lists)){


            String personalPicture = MapUtils.getString(lists, "personal_photo");
            if (StringUtils.isBlank(personalPicture)) {
                //没有个人照片信息
                lists.put("personal_photo", new String[0]);
            } else {
                String[] pictures = personalPicture.split(";");
                lists.put("personal_photo", pictures);
            }
            String qualificationCertificate = MapUtils.getString(lists, "qualification_certificate");
            if (StringUtils.isBlank(qualificationCertificate)) {
                //没有资质证书信息
                lists.put("qualification_certificate", new String[0]);
            } else {
                String[] certificates = qualificationCertificate.split(";");
                lists.put("qualification_certificate", certificates);
            }
            String honorCertificate = MapUtils.getString(lists, "honor_certificate");
            if (StringUtils.isBlank(honorCertificate)) {
                //没有荣誉证书信息
                lists.put("honor_certificate", new String[0]);
            } else {
                String[] honors = honorCertificate.split(";");
                lists.put("honor_certificate", honors);
            }

            map.put("status","1".equals(MapUtils.getString(lists,"status"))? true : false);


            map.put("expertInfo",lists);

        }else{


            map.put("status",true);

            map.put("expertInfo",new HashMap<>());


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
    public void updateExpertInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("expert_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> list = expertInfoMapper.findExpertInfo(map);
        if(MapUtils.isNotEmpty(list)){
            expertInfoMapper.updateExpertInfoName(params);
        }else{
            expertInfoMapper.insertExpertInfo(params);
        }

    }



    @Override
    public void insertExpertInfo(Map<String,Object> params){
        Map<String,Object> expertInfo = (Map<String, Object>) params.get("expertInfo");

        String personal_photo = MapUtils.getString(expertInfo,"personal_photo");
        if(StringUtil.isNotEmpty(personal_photo)) {
            JSONArray entrance_graduation_time_model = JSONArray.parseArray(personal_photo);
            if (entrance_graduation_time_model.size() == 0) {
                expertInfo.put("personal_photo", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(personal_photo);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                expertInfo.put("personal_photo", memberMerge);
            }
        }else{
            expertInfo.put("personal_photo", "");
        }

        String honor_certificate = MapUtils.getString(expertInfo,"honor_certificate");
        if(StringUtil.isNotEmpty(honor_certificate)) {
            JSONArray entrance_graduation_time_model = JSONArray.parseArray(honor_certificate);
            if (entrance_graduation_time_model.size() == 0) {
                expertInfo.put("honor_certificate", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(honor_certificate);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                expertInfo.put("honor_certificate", memberMerge);
            }
        }else{
            expertInfo.put("honor_certificate", "");
        }

        String qualification_certificate = MapUtils.getString(expertInfo,"qualification_certificate");
        if(StringUtil.isNotEmpty(qualification_certificate)) {
            JSONArray entrance_graduation_time_model = JSONArray.parseArray(qualification_certificate);
            if (entrance_graduation_time_model.size() == 0) {
                expertInfo.put("qualification_certificate", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(qualification_certificate);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                expertInfo.put("qualification_certificate", memberMerge);
            }
        }else{
            expertInfo.put("qualification_certificate", "");
        }


        String user_id = MapUtils.getString(params,"user_id");
        expertInfo.put("user_id",user_id);
        expertInfo.put("expert_name",MapUtils.getString(params,"expertInfo_name"));
        expertInfo.put("year_id",MapUtils.getString(params,"yearId"));
        expertInfo.put("expert_status",MapUtils.getBoolean(params,"status"));
        expertInfo.put("templateId",MapUtils.getString(params,"templateId"));
        expertInfoMapper.insertExpertInfo(expertInfo);

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
