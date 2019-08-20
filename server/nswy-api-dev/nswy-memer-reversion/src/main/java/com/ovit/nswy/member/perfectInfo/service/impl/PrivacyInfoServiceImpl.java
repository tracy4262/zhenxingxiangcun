package com.ovit.nswy.member.perfectInfo.service.impl;

import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.certification.mapper.RealCertificationMapper;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.*;
import com.ovit.nswy.member.perfectInfo.service.PrivacyInfoService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 隐私信息
 */
@Service
public class PrivacyInfoServiceImpl implements PrivacyInfoService {

    @Autowired
    private PrivacyInfoMapper privacyInfoMapper;

    @Autowired
    private PhysicalGeographyMapper physicalGeographyMapper;

    @Autowired
    private RealCertificationMapper realCertificationMapper;

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private TemplateConfigService templateConfigService;




    @Override
    public Map<String, Object> findPrivacyInfo(Map<String,Object> params) {

        Map<String,Object> map = new HashMap<>();

        Map<String,Object> member = new HashMap<>();
        member.put("dictId",MapUtils.getString(params,"parent_id"));
        member.put("account",MapUtils.getString(params,"user_id"));
        member.put("yearId",MapUtils.getString(params,"year_id"));
        member.put("templateId",MapUtils.getString(params,"templateId"));

        Map<String,Object> propertyInfo = perfectBasicMapper.findPropertyInfo(member);
        if (MapUtils.isEmpty(propertyInfo)) {
            map.put("privateInformation_name", "隐私信息");
        } else {
            map.put("privateInformation_name", MapUtils.getString(propertyInfo, "propertyName"));
        }

        Map<String,Object> textPreview =  physicalGeographyMapper.findTextPreview(params);

        Map<String,Object> privacyInfo = privacyInfoMapper.findPrivacyInfo(params);
        //LinkedHashMap,保证有序
        Map<String, Object> privacyMap = new LinkedHashMap<>();

        //从reversion_member_virtual_info表中获取姓名、手机号、常驻地、头像信息
        //Map<String, Object>  userInfo = realCertificationMapper.findList(params);
       // Map<String,Object> loginUser = realCertificationMapper.findByLoginUser(params);

        if(MapUtils.isNotEmpty(privacyInfo)){
            privacyMap.put("id", MapUtils.getString(privacyInfo,"id"));
            privacyMap.put("avatar", MapUtils.getString(privacyInfo,"image"));
            privacyMap.put("userName", publicMap(MapUtils.getString(privacyInfo,"name_model"),
                    MapUtils.getString(privacyInfo,"name"),MapUtils.getString(privacyInfo,"name_status")));

            privacyMap.put("sex", publicMap(MapUtils.getString(privacyInfo,"sex_model"),
                    MapUtils.getString(privacyInfo,"sex"),MapUtils.getString(privacyInfo,"sex_status")));

            privacyMap.put("ethnicGroup", publicMap(MapUtils.getString(privacyInfo,"nation_model"),
                    MapUtils.getString(privacyInfo,"nation"),MapUtils.getString(privacyInfo,"nation_status")));

            privacyMap.put("birthday", publicMap(MapUtils.getString(privacyInfo,"birthday_model"),
                    MapUtils.getString(privacyInfo,"birthday"),MapUtils.getString(privacyInfo,"birthday_status")));

            privacyMap.put("profession", publicMap(MapUtils.getString(privacyInfo,"profession_model"),
                    MapUtils.getString(privacyInfo,"profession"),MapUtils.getString(privacyInfo,"profession_status")));

            privacyMap.put("professionalTitle", publicMap(MapUtils.getString(privacyInfo,"position_model"),
                    MapUtils.getString(privacyInfo,"position"),MapUtils.getString(privacyInfo,"position_status")));

            privacyMap.put("species", publicMap(MapUtils.getString(privacyInfo,"excel_species_model"),
                    MapUtils.getString(privacyInfo,"excel_species"),MapUtils.getString(privacyInfo,"excel_species_status")));

            privacyMap.put("phone", publicMap(MapUtils.getString(privacyInfo,"phone_model"),
                    MapUtils.getString(privacyInfo,"phone"),MapUtils.getString(privacyInfo,"phone_status")));

            privacyMap.put("coordinatePoint", publicMap(MapUtils.getString(privacyInfo,"coordinate_position_model"),
                    MapUtils.getString(privacyInfo,"coordinate_position"),MapUtils.getString(privacyInfo,"coordinate_position_status")));

            privacyMap.put("postalCode", publicMap(MapUtils.getString(privacyInfo,"postal_code_model"),
                    MapUtils.getString(privacyInfo,"postal_code"),MapUtils.getString(privacyInfo,"postal_code_status")));

            privacyMap.put("tel", publicMap(MapUtils.getString(privacyInfo,"telephone_model"),
                    MapUtils.getString(privacyInfo,"telephone"),MapUtils.getString(privacyInfo,"telephone_status")));

            privacyMap.put("number", publicMap(MapUtils.getString(privacyInfo,"house_number_model"),
                    MapUtils.getString(privacyInfo,"house_number"),MapUtils.getString(privacyInfo,"house_number_status")));

            privacyMap.put("addr", publicMap(MapUtils.getString(privacyInfo,"resident_model"),
                    MapUtils.getString(privacyInfo,"resident"),MapUtils.getString(privacyInfo,"resident_status")));

            /*privacyMap.put("addr", publicMap(MapUtils.getString(loginUser,"city"),
                    MapUtils.getString(privacyInfo,"resident"),MapUtils.getString(privacyInfo,"resident_status")));*/


            String addr;
            if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"addr_Detail"))){
                addr = MapUtils.getString(privacyInfo,"addr_Detail");
            }else{
                addr = "详细地址";
            }

            privacyMap.put("addrDetail", publicMap(MapUtils.getString(privacyInfo,"addr_Detail_model"),
                    addr,MapUtils.getString(privacyInfo,"addr_Detail_status")));

            Map<String,Object> privacyInfoMap = new HashMap<>();

            map.put("privateInformation",privacyMap);
            map.put("status","1".equals(MapUtils.getString(privacyInfo,"status"))? true : false);


        }else{

            Map<String,Object> loginUser = realCertificationMapper.findList(params);
            String image = MapUtils.getString(loginUser,"avatar");
            if (StringUtils.isNotBlank(image)) {
                if (image.startsWith("http:")) {
                    image = image.substring(5);
                }
            } else {
                image = "";
            }

            privacyMap.put("avatar", image);
            privacyMap.put("userName", publicMap("", "姓名","1"));
            privacyMap.put("sex", publicMap("", "性别","1"));
            privacyMap.put("ethnicGroup", publicMap("", "民族","1"));
            privacyMap.put("birthday", publicMap("", "生日","1"));
            privacyMap.put("profession", publicMap("", "职业","1"));
            privacyMap.put("professionalTitle", publicMap("", "职称","1"));
            privacyMap.put("species", publicMap("", "擅长物种","1"));
            privacyMap.put("phone", publicMap("", "手机号","1"));
            privacyMap.put("coordinatePoint", publicMap("", "坐标位置","1"));
            privacyMap.put("postalCode", publicMap("", "邮政编码","1"));
            privacyMap.put("tel", publicMap("", "座机号码","1"));
            privacyMap.put("number", publicMap("", "门牌号","1"));
            privacyMap.put("addr", publicMap("", "常驻地","1"));
            privacyMap.put("addrDetail", publicMap("", "详细地址","1"));



            map.put("status",true);

            map.put("privateInformation",privacyMap);

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
    public void insertPrivacyInfo(Map<String,Object> params){

        Map<String,Object> privateInformation = (Map<String, Object>) params.get("privateInformation");
        String user_id = MapUtils.getString(params,"user_id");
        if(MapUtils.isNotEmpty(privateInformation)){

            privateInformation.put("privacy_information_name",MapUtils.getString(params,"privateInformation_name"));
            privateInformation.put("user_id",user_id);
            privateInformation.put("year_id",MapUtils.getString(params,"yearId"));
            privateInformation.put("templateId",MapUtils.getString(params,"templateId"));

            privacyInfoMapper.insertPrivacyInfo(privateInformation);

            //修改实名认证 手机号， 座机号， 常住地 姓名
            privacyInfoMapper.updateMemberVirtual(privateInformation);

            if(!MapUtils.getString(privateInformation,"avatar").equals("")){
                //修改login_user 图像
                privacyInfoMapper.updateByLoginUserAvatar(privateInformation);
            }
        }

        saveTextPreview(params);


    }

    @Override
    public void updatePrivacyInfoName(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        map.put("year_id",MapUtils.getString(params,"yearId"));
        map.put("user_id",MapUtils.getString(params,"account"));
        params.put("year_id",MapUtils.getString(params,"yearId"));
        params.put("user_id",MapUtils.getString(params,"account"));
        params.put("privacy_information_name",MapUtils.getString(params,"propertyName"));
        Map<String,Object> privacyInfo = privacyInfoMapper.findPrivacyInfo(map);

        if(MapUtils.isNotEmpty(privacyInfo)){
            privacyInfoMapper.updatePrivacyInfoName(params);
        }else{
            privacyInfoMapper.insertPrivacyInfo(params);
        }


    }

    private void saveTextPreview(Map<String,Object> params){
        Map<String,Object> textPreview = MapUtils.getMap(params,"textPreview");

        if(MapUtils.isNotEmpty(textPreview)){
            textPreview.put("sys_dict_id",MapUtils.getString(params,"sys_dict_id"));
            textPreview.put("user_id",MapUtils.getString(params,"user_id"));
            textPreview.put("year_id",MapUtils.getString(params,"yearId"));
            textPreview.put("templateId",MapUtils.getString(params,"templateId"));
            physicalGeographyMapper.saveTextPreview(textPreview);
        }
    }

    private Map<String,Object> publicMap(String model,String name,String status){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", StringUtils.isBlank(model) ? "" : model);
        maps.put("name", StringUtils.isBlank(name) ? "" : name);
        maps.put("status", "1".equals(status) ? true : false);
        return maps;

    }
}
