package com.ovit.nswy.member.perfectInfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.AdministrationDivisionService;
import com.ovit.nswy.member.util.ImageUtil;
import com.ovit.nswy.member.util.TreeUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *行政区分
 */
@Service
public class AdministrationDivisionServiceImpl implements AdministrationDivisionService {

    @Autowired
    private AdministrationDivisionMapper administrationDivisionMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private EmployeeRosterMapper employeeRosterMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;


    @Override
    public Map<String,Object> findDepartmentInfo(Map<String,Object> params){

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = administrationDivisionMapper.findDepartmentInfo(params);
        TreeUtil treeUtil = new TreeUtil();
        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("departmentInfo_name", "组织结构");
        } else {
            map.put("departmentInfo_name", MapUtils.getString(propertyInfo, "propertyName"));
        }
        if(list.size()>0){

            map.put("departmentInfo",treeUtil.treeNodeList(list));

            map.put("status","1".equals(MapUtils.getString(list.get(0),"status"))? true : false);

        }else{

            map.put("departmentInfo",new String[]{});

            map.put("status",true);

        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }


            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));

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
    public void updateDepartmentInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("department_name",MapUtils.getString(params,"propertyName"));
        params.put("user_id",MapUtils.getString(params,"account"));
        List<Map<String,Object>> list = administrationDivisionMapper.findDepartmentInfo(map);
        if(list.size()>0){
            administrationDivisionMapper.updateDepartmentInfoName(params);
        }else{
            administrationDivisionMapper.saveDepartmentInfo(params);
        }

    }



    @Override
    public void saveDepartmentInfo(Map<String,Object> params){
        Map<String,Object> departmentInfo = (Map<String, Object>) params.get("departmentInfo");
        String user_id = MapUtils.getString(params,"user_id");
        departmentInfo.put("department_name",MapUtils.getString(params,"departmentInfo_name"));
        departmentInfo.put("user_id",user_id);
        departmentInfo.put("year_id",MapUtils.getString(params,"yearId"));
        departmentInfo.put("status",MapUtils.getBoolean(params,"status"));
        departmentInfo.put("templateId", MapUtils.getString(params,"templateId"));
        administrationDivisionMapper.saveDepartmentInfo(departmentInfo);

    }


    @Override
    public Map<String,Object> findAdministrativeDivision(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("administrativeDivision_name", "行政区域");
        } else {
            map.put("administrativeDivision_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = administrationDivisionMapper.findAdministrativeDivision(params);
        TreeUtil treeUtil = new TreeUtil();
        if(list.size()>0){


            map.put("administrativeDivision",treeUtil.treeNodeList(list));
            map.put("status","1".equals(MapUtils.getString(list.get(0),"status"))? true : false);

        }else{

            map.put("status",true);

            map.put("administrativeDivision",new ArrayList<>());



        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
            }

            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));

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
    public void updateAdministrativeDivisionName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("division_name",MapUtils.getString(params,"propertyName"));
        params.put("user_id",MapUtils.getString(params,"account"));
        List<Map<String,Object>> list = administrationDivisionMapper.findAdministrativeDivision(map);
        if(list.size()>0){
            administrationDivisionMapper.updateAdministrativeDivisionName(params);
        }else{
            administrationDivisionMapper.saveAdministrativeDivision(params);
        }

    }

    @Override
    public void saveAdministrativeDivision(Map<String,Object> params){
        String user_id = MapUtils.getString(params,"user_id");
        Map<String,Object> administrativeDivision = (Map<String, Object>) params.get("administrativeDivision");
        administrativeDivision.put("division_name",MapUtils.getString(params,"administrativeDivision_name"));
        administrativeDivision.put("user_id",user_id);
        administrativeDivision.put("year_id",MapUtils.getString(params,"yearId"));
        administrativeDivision.put("status",MapUtils.getBoolean(params,"status"));
        administrativeDivision.put("templateId", MapUtils.getString(params,"templateId"));
        administrationDivisionMapper.saveAdministrativeDivision(administrativeDivision);

    }

    @Override
    public List<Map<String,Object>> findRoster(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();


        List<Map<String,Object>> employeeRoster = employeeRosterMapper.findEmployeeRoster(params);
        for(Map<String,Object> map:employeeRoster){
            Map<String,Object> employee = new HashMap<>();
            employee.put("name",MapUtils.getString(map,"name"));
            employee.put("sex",MapUtils.getString(map,"sex"));
            list.add(employee);
        }

        List<Map<String,Object>> listOfExtraStaff = employeeRosterMapper.findListOfExtraStaff(params);
        for(Map<String,Object> map:listOfExtraStaff){
            Map<String,Object> listOf = new HashMap<>();
            listOf.put("name",MapUtils.getString(map,"name"));
            listOf.put("sex",MapUtils.getString(map,"sex"));
            list.add(listOf);
        }
        return list;
    }


    @Override
    public Map<String,Object> findManagerialStaff(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("managerialStaff_name", "管理人员");
        } else {
            map.put("managerialStaff_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);
        List<Map<String,Object>> list = administrationDivisionMapper.findManagerialStaff(params);
        if(list.size()>0){


            for(Map<String,Object> staff:list ){
                staff.put("status","1".equals(MapUtils.getString(staff,"status"))? true : false);
                String image = MapUtils.getString(staff,"image");
                if(StringUtil.isNotEmpty(image)){
                    String[] members = image.split(";");
                    staff.put("image",members);
                }else{
                    staff.put("image", new String[]{});
                }
            }

            map.put("managerialStaff",list);

        }else{

            map.put("managerialStaff",new ArrayList<>());

        }

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> templatelist  = templateConfigService.find(historyInfo);
            historyInfo.put("parent_id",MapUtils.getString(params,"parent_id"));
            if(templatelist.size()>0){
                historyInfo.put("user_id",MapUtils.getString(templatelist.get(0),"account"));
            }else{
                historyInfo.put("user_id", MapUtils.getString(params,"user_id"));
                historyInfo.put("year_id",MapUtils.getString(params,"year_id"));
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
    public void updateManagerialStaffName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("managerial_staff_name",MapUtils.getString(params,"propertyName"));
        params.put("user_id",MapUtils.getString(params,"account"));
        List<Map<String,Object>> list = administrationDivisionMapper.findManagerialStaff(map);
        if(list.size()>0){
            administrationDivisionMapper.updateManagerialStaffName(params);
        }else{
            administrationDivisionMapper.saveManagerialStaff(params);
        }

    }

    @Override
    public void saveManagerialStaff(Map<String,Object> params){
        Map<String,Object> managerialStaff = (Map<String, Object>) params.get("managerialStaff");
        String image = MapUtils.getString(managerialStaff,"image");
        if(StringUtil.isNotEmpty(image)) {
            JSONArray entrance_graduation_time_model = JSONArray.parseArray(image);
            if (entrance_graduation_time_model.size() == 0) {
                managerialStaff.put("image", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(image);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                managerialStaff.put("image", memberMerge);
            }
        }else{
            managerialStaff.put("image", "");
        }
        String user_id = MapUtils.getString(params,"user_id");
        managerialStaff.put("managerial_staff_name",MapUtils.getString(params,"managerialStaff_name"));
        managerialStaff.put("user_id",user_id);
        managerialStaff.put("year_id",MapUtils.getString(params,"yearId"));
        managerialStaff.put("templateId", MapUtils.getString(params,"templateId"));

        administrationDivisionMapper.saveManagerialStaff(managerialStaff);

    }

    @Override
    public void saveTextPreviews(Map<String,Object> params){
        saveTextPreview(params);

    }



    private void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId", MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveTextPreview(textPreview);
        }
    }

    @Override
    public void deleteDepartMentInfo(Map<String,Object> params){
        administrationDivisionMapper.deleteDepartMentInfo(params);
    }

    @Override
    public void deleteAdministrativeDivision(Map<String,Object> params){
        administrationDivisionMapper.deleteAdministrativeDivision(params);
    }

    @Override
    public void deleteManagerialStaff(Map<String,Object> params){
        administrationDivisionMapper.deleteManagerialStaff(params);
    }


    @Override
    public  List<Map<String,Object>> findManagerialStaffByAccount(Map<String,Object> params){



        List<Map<String,Object>> list = administrationDivisionMapper.findManagerialStaff(params);
        if(list.size()>0){


            for(Map<String,Object> staff:list ){
                staff.put("status","1".equals(MapUtils.getString(staff,"status"))? true : false);
                String image = MapUtils.getString(staff,"image");
                if(StringUtil.isNotEmpty(image)){
                    String[] members = image.split(";");
                    staff.put("image",members);
                }else{
                    staff.put("image", new String[]{});
                }
            }



        }

        return list;
    }

}
