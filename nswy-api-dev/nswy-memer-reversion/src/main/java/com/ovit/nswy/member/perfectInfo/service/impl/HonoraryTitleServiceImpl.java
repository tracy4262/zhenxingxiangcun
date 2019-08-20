package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.HonoraryTitleService;
import com.ovit.nswy.member.util.ImageUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 荣誉称号
 */
@Service
public class HonoraryTitleServiceImpl implements HonoraryTitleService {

    @Autowired
    private HonoraryTitleMapper honoraryTitleMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;



    @Override
    public Map<String,Object> findHonoraryTitle(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"yearId"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        params.put("year_id",MapUtils.getString(params,"yearId"));
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("honorName", "荣誉称号");
        } else {
            map.put("honorName", MapUtils.getString(propertyInfo, "honorName"));
        }
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> lists = honoraryTitleMapper.findHonoraryTitle(params);
        if(lists.size()>0){

            for(Map<String,Object> honorary:lists){
                String images = MapUtils.getString(honorary,"honorPictureList");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    honorary.put("honorPictureList",image);
                }else{
                    honorary.put("honorPictureList",new String[]{});
                }
                honorary.put("status","1".equals(MapUtils.getString(honorary,"status"))? true : false);
            }

            map.put("honoraryTitle",lists);

        }else{

            map.put("honoraryTitle",new ArrayList<>());

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
    public void updateHonoraryTitleName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("honorary_title_name",MapUtils.getString(params,"honorName"));
        List<Map<String,Object>> list = honoraryTitleMapper.findHonoraryTitle(map);
        if(list.size()>0){
            honoraryTitleMapper.updateHonoraryTitleName(params);
        }else{
            honoraryTitleMapper.saveHonoraryTitle(params);
        }

    }



    @Override
    public void saveHonoraryTitle(Map<String,Object> params){
        Map<String,Object> employeeRoster = MapUtils.getMap(params,"honoraryTitle");
        String user_id = MapUtils.getString(params,"user_id");
        String images = MapUtils.getString(employeeRoster,"honorPictureList");
        JSONArray image = JSONArray.parseArray(images);
        if (image.size() == 0) {
            employeeRoster.put("honorPictureList", "");
        } else {
            String[] empower = ImageUtil.getJsonToStringArray(images);
            String pictureMerge = ImageUtil.stringArrayMerge(empower);
            employeeRoster.put("honorPictureList", pictureMerge);
        }
        employeeRoster.put("user_id",user_id);

        employeeRoster.put("honorary_title_name",MapUtils.getString(params,"honorName"));
        employeeRoster.put("yearId",MapUtils.getString(params,"yearId"));
        employeeRoster.put("templateId",MapUtils.getString(params,"templateId"));
        honoraryTitleMapper.saveHonoraryTitle(employeeRoster);

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
    public void deleteHonoraryTitle(Map<String,Object> params){
        honoraryTitleMapper.deleteHonoraryTitle(params);
    }


    @Override
    public List<Map<String,Object>> findHonoraryTitleByAccount(Map<String,Object> params){



        Map<String,Object> member = new HashMap<>();

        member.put("user_id",MapUtils.getString(params,"account"));

        List<Map<String,Object>> lists = honoraryTitleMapper.findHonoraryTitle(member);
        if(lists.size()>0){

            for(Map<String,Object> honorary:lists){
                String images = MapUtils.getString(honorary,"honorPictureList");
                if(StringUtil.isNotBlank(images)){
                    String[] image = images.split(";");
                    honorary.put("honorPictureList",image);
                }else{
                    honorary.put("honorPictureList",new String[]{});
                }
                honorary.put("status","1".equals(MapUtils.getString(honorary,"status"))? true : false);
            }



        }



        return lists;
    }

}
