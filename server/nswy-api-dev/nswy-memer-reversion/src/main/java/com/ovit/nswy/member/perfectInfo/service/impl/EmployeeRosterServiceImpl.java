package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.feigen.MemberServiceFeign;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.EmployeeRosterMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PhysicalGeographyMapper;
import com.ovit.nswy.member.perfectInfo.service.EmployeeRosterService;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人口信息
 */
@Service
public class EmployeeRosterServiceImpl implements EmployeeRosterService {

    @Autowired
    private EmployeeRosterMapper employeeRosterMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private MemberServiceFeign memberServiceFeign;



    @Override
    public Map<String,Object> findEmployeeRoster(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("employeeRoster_name", "员工花名册");
        } else {
            map.put("employeeRoster_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = employeeRosterMapper.findEmployeeRoster(params);
        if(list.size()>0){

            for(Map<String,Object> roster:list ){
                roster.put("status","1".equals(MapUtils.getString(roster,"status"))? true : false);
                JSONArray json = JSONArray.parseArray(MapUtils.getString(roster,"custom_field"));
                roster.put("custom_field",json);
            }

            map.put("employeeRoster",list);

        }else{

            map.put("employeeRoster",new ArrayList<>());

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
    public void updateEmployeeRosterName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("employee_roster_name",MapUtils.getString(params,"propertyName"));
        params.put("user_id",MapUtils.getString(params,"account"));
        List<Map<String,Object>> list = employeeRosterMapper.findEmployeeRoster(map);
        if(list.size()>0){
            employeeRosterMapper.updateEmployeeRosterName(params);
        }else{
            employeeRosterMapper.saveEmployeeRoster(params);
        }

    }



    @Override
    public void saveEmployeeRoster(Map<String,Object> params){
        Map<String,Object> employeeRoster = (Map<String, Object>) params.get("employeeRoster");

        String user_id = MapUtils.getString(params,"user_id");
        employeeRoster.put("employee_roster_name",MapUtils.getString(params,"employeeRoster_name"));
        employeeRoster.put("user_id",user_id);
        employeeRoster.put("year_id",MapUtils.getString(params,"yearId"));
        employeeRoster.put("templateId",MapUtils.getString(params,"templateId"));
        String custom_field  = JSON.toJSONString(employeeRoster.get("custom_field"));
        employeeRoster.put("custom_field", custom_field);

        if(MapUtils.getString(employeeRoster,"id").equals("0")){
            if(StringUtil.isNotBlank(MapUtils.getString(employeeRoster,"card"))){
                String proxyName = StringUtils.join("s", RandomStringUtils.randomAlphanumeric(7).toLowerCase());
                params.put("farm_account",proxyName);
                //生成账号
                params.put("unique_id", UuidUtil.get32UUID());
                String farmValue = MapUtils.getString(employeeRoster,"card");
                String password=farmValue.substring(farmValue.length()-6,farmValue.length());
                //password = new SimpleHash("SHA-1", Constants.SESSION_SALT, password).toString();
                params.put("login_passwd",password);
                params.put("user_type","1");//个人
                params.put("name",MapUtils.getString(employeeRoster,"name"));//个人
                employeeRosterMapper.insertLogin(params);

                if(StringUtil.isNotBlank(MapUtils.getString(employeeRoster,"phone"))){
                    Map<String,Object> infoMap = new HashMap<>();

                    infoMap.put("loginPassword",password);
                    infoMap.put("phone",MapUtils.getString(employeeRoster,"phone"));
                    infoMap.put("displayName",MapUtils.getString(employeeRoster,"name"));
                    infoMap.put("name",MapUtils.getString(params,"name"));
                    infoMap.put("agencyer",user_id);
                    infoMap.put("account",proxyName);
                    Object info = memberServiceFeign.smsSendMasUserInfo(infoMap);
                }
                employeeRoster.put("staffAccount",proxyName);

            }
        }

        employeeRosterMapper.saveEmployeeRoster(employeeRoster);

    }


    @Override
    public Map<String,Object> findListOfExtraStaff(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("listOfExtraStaff_name", "编外人员花名册");
        } else {
            map.put("listOfExtraStaff_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = employeeRosterMapper.findListOfExtraStaff(params);
        if(list.size()>0){

            for(Map<String,Object> roster:list ){
                roster.put("status","1".equals(MapUtils.getString(roster,"status"))? true : false);
                JSONArray json = JSONArray.parseArray(MapUtils.getString(roster,"custom_field"));
                roster.put("custom_field",json);
            }

            map.put("listOfExtraStaff",list);

        }else{

            map.put("listOfExtraStaff",new ArrayList<>());


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
    public void updateListOfExtraStaffName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("list_of_extra_staff_name",MapUtils.getString(params,"propertyName"));
        List<Map<String,Object>> list = employeeRosterMapper.findListOfExtraStaff(map);
        if(list.size()>0){
            employeeRosterMapper.updateListOfExtraStaffName(params);
        }else{
            employeeRosterMapper.saveListOfExtraStaff(params);
        }

    }



    @Override
    public void saveListOfExtraStaff(Map<String,Object> params){
        Map<String,Object> listOfExtraStaff = (Map<String, Object>) params.get("listOfExtraStaff");
        String user_id = MapUtils.getString(params,"user_id");
        listOfExtraStaff.put("list_of_extra_staff_name",MapUtils.getString(params,"listOfExtraStaff_name"));
        listOfExtraStaff.put("user_id",user_id);
        listOfExtraStaff.put("year_id",MapUtils.getString(params,"yearId"));
        listOfExtraStaff.put("templateId",MapUtils.getString(params,"templateId"));
        String custom_field  = JSON.toJSONString(listOfExtraStaff.get("custom_field"));
        listOfExtraStaff.put("custom_field", custom_field);
        employeeRosterMapper.saveListOfExtraStaff(listOfExtraStaff);



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
    public void deleteEmployeeRoster(Map<String,Object> params){
        employeeRosterMapper.deleteEmployeeRoster(params);
    }

    @Override
    public void deleteListOfExtraStaff(Map<String,Object> params){
        employeeRosterMapper.deleteListOfExtraStaff(params);
    }


}
