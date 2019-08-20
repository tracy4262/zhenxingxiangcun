package com.ovit.nswy.member.service.impl;

import com.github.xphsc.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.member.mapper.PerfectInformationMapper;
import com.ovit.nswy.member.model.Certification;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PerfectInformationServiceImpl implements PerfectInformationService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectInformationMapper perfectInformationMapper;

    @Autowired
    private FarmFamilyDetailService farmFamilyDetailService;

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private ExpertInfoService expertInfoService;


    @Override
    public Map<String,Object> findSysUserInfo(Map<String,Object> params){

        Map<String,Object> tabs = new HashMap<>();

        String account = MapUtils.getString(params, "account");
        LoginUser user = loginUserService.findByLoginName(account);
        int userType = user.getUserType();
        if (4 == userType) {
            //如果认证了专家，审核没通过时为普通用户
            int approveStatus = expertInfoService.getApproveStatus(user.getLoginAccount());
            user.setUserType(approveStatus == 1 ? 4 : 0);
        }
        params.put("userType", user.getUserType());

        List<Map<String,Object>> listTab = new ArrayList<>();

        List<Map<String,Object>> sysUserInfos  = perfectInformationMapper.findSysUserInfo(params);


        for(Map<String,Object> map :sysUserInfos){

            if(MapUtils.getString(map,"privacy_information").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","privateInformation");
                tabsData.put("title","隐私信息");
                listTab.add(tabsData);
            }

            if(MapUtils.getString(map,"network_information").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","networkInformation");
                tabsData.put("title","网络信息");
                listTab.add(tabsData);

            }
            if(MapUtils.getString(map,"education_live").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","education");
                tabsData.put("title","教育经历");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"work_live").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","work");
                tabsData.put("title","工作经历");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"political_status").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","policy");
                tabsData.put("title","政治面貌");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"religion_faith").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","religion");
                tabsData.put("title","宗教信仰");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"specialty_information").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","professionalInformation");
                tabsData.put("title","专业信息");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"honour_style").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","corpHonor");
                tabsData.put("title","荣誉风采");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"family_Member").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","member");
                tabsData.put("title","家庭成员");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"output_product").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","product");
                tabsData.put("title","产出产品");
                listTab.add(tabsData);
            }

            if(MapUtils.getString(map,"provide_services").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","provide");
                tabsData.put("title","提供服务");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"purchase_intention").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","purchase");
                tabsData.put("title","求购信息");
                listTab.add(tabsData);
            }

            if(MapUtils.getString(map,"houses_condition").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","asset");
                tabsData.put("title","资产管理");
                listTab.add(tabsData);
            }


            if(MapUtils.getString(map,"organization").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","department");
                tabsData.put("title","组织机构");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"management_team").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","team");
                tabsData.put("title","管理团队");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"survey_operation").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","survey");
                tabsData.put("title","经营概况");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"finance_information").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","assetFinance");
                tabsData.put("title","财务信息");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"property_information").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
               /* tabsData.put("name","propertyInformation");
                tabsData.put("title","资产信息");*/
            }
            if(MapUtils.getString(map,"product_serve").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","productService");
                tabsData.put("title","产品&服务");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"specialty_credentials").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","qualification");
                tabsData.put("title","专业资质");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"common_assets").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","commonAssets");
                tabsData.put("title","普通资产");
                listTab.add(tabsData);
            }
/*            if(MapUtils.getString(map,"intangible_assets").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","intangibleAssets");
                tabsData.put("title","无形资产");
                listTab.add(tabsData);
            }
            if(MapUtils.getString(map,"facility_assets").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","facilityAssets");
                tabsData.put("title","设施资产");
                listTab.add(tabsData);
            }

            if(MapUtils.getString(map,"place_of_business").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","placeOfBusiness");
                tabsData.put("title","经营场所");
                listTab.add(tabsData);
            }*/

            if(MapUtils.getString(map,"overview_info").equals("1")){
                Map<String,Object> tabsData = new HashMap<>();
                tabsData.put("name","overViewInfo");
                tabsData.put("title","概览信息");
                listTab.add(tabsData);
            }



        }
        tabs.put("tabsData", listTab);
        tabs.put("isNext",true);
        return tabs;
    }


    @Override
    public Map<String,Object> findPerfectInformation(Map<String,Object> params){
        Map<String,Object> perfectInformation = new HashMap<>();
        Map<String,Object> modulesInfo = (Map<String, Object>) params.get("modulesInfo");
        if ("1".equals(MapUtils.getString(modulesInfo, "education_live"))) {
            // 教育经历
            List<Map<String,Object>> educations = perfectInformationMapper.findEducation(params);
            List<Map<String,Object>> educationList = new ArrayList<>();
            for(Map<String,Object> education:educations){
                Map<String,Object> educationInfo = new LinkedHashMap<>();
                educationInfo.put("school",this.publicMap(MapUtils.getString(education,"school_model"),
                        MapUtils.getString(education,"school_name"),MapUtils.getString(education,"school_name_status")));

                educationInfo.put("degree",this.publicMap(MapUtils.getString(education,"education_model"),
                        MapUtils.getString(education,"education"),MapUtils.getString(education,"education_status")));

                educationInfo.put("major",this.publicMap(MapUtils.getString(education,"major_model"),
                        MapUtils.getString(education,"major"),MapUtils.getString(education,"major_status")));

                educationInfo.put("recruitment",this.publicMap(MapUtils.getString(education,"is_general_model"),
                        MapUtils.getString(education,"is_general"),MapUtils.getString(education,"is_general_status")));

                Map<String,Object> educationMap = this.publicMap(MapUtils.getString(education,"entrance_graduation_time_model"),
                        MapUtils.getString(education,"entrance_graduation_time"),MapUtils.getString(education,"entrance_graduation_time_status"));

                String memberPicture = MapUtils.getString(educationMap,"model");
                if(StringUtil.isNotEmpty(memberPicture)){
                    String[] members = memberPicture.split(";");
                    educationMap.put("model",members);
                }else{
                    educationMap.put("model", new String[]{});
                }
                educationInfo.put("graduationTime",educationMap);
                educationList.add(educationInfo);

            }
            //perfectInformation.put("education",educations);
            perfectInformation.put("education",educationList);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "work_live"))) {
            // 工作经历
            List<Map<String,Object>> works = perfectInformationMapper.findWork(params);
            List<Map<String,Object>> workList = new ArrayList<>();
            for(Map<String,Object> work:works){
                Map<String,Object> workInfo = new LinkedHashMap<>();
                workInfo.put("WorkUnit",this.publicMap(MapUtils.getString(work,"work_unit_model"),
                        MapUtils.getString(work,"work_unit"),MapUtils.getString(work,"work_unit_status")));

                workInfo.put("job",this.publicMap(MapUtils.getString(work,"work_position_model"),
                        MapUtils.getString(work,"work_position"),MapUtils.getString(work,"work_position_status")));

                Map<String,Object> workMap = this.publicMap(MapUtils.getString(work,"work_time_model"),
                        MapUtils.getString(work,"work_time"),MapUtils.getString(work,"work_time_status"));
                String memberPicture = MapUtils.getString(workMap,"model");
                if(StringUtil.isNotEmpty(memberPicture)) {
                    String[] members = memberPicture.split(";");
                    workMap.put("model",members);
                }else{
                    workMap.put("model", new String[]{});
                }
                workInfo.put("workTime",workMap);

                workInfo.put("detail",this.publicMap(MapUtils.getString(work,"work_details_model"),
                        MapUtils.getString(work,"work_details"),MapUtils.getString(work,"work_details_status")));

                workList.add(workInfo);

            }
            //perfectInformation.put("work",works);
            perfectInformation.put("work", workList);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "honour_style"))) {
            //荣誉风采
            List<Map<String,Object>> honor_info = perfectInformationMapper.findHonor(params);
            if(honor_info!=null&&honor_info.size()>0){
                for(Map<String,Object> honor:honor_info){
                    //荣誉证书，可有多个
                    String honors = MapUtils.getString(honor, "file");
                    if (StringUtils.isBlank(honors)) {
                        //没有荣誉证书
                        honor.put("honorPictureList", new String[0]);
                    } else {
                        String[] honorArray = honors.split(";");
                        honor.put("honorPictureList", honorArray);
                    }
                    if(StringUtil.isNotEmpty(MapUtils.getString(honor,"honour_status"))){
                        if(MapUtils.getString(honor,"honour_status").equals("1")){
                            honor.put("honour_status",true);
                        }else{
                            honor.put("honour_status",false);
                        }
                    }else{
                        honor.put("honour_status",false);
                    }
                }
                perfectInformation.put("honors",honor_info);
            }

        }

        if ("1".equals(MapUtils.getString(modulesInfo, "family_Member"))) {
            //家庭成员
            List<Map<String,Object>> family_meber  = farmFamilyDetailService.findFamilyMember(params);

            perfectInformation.put("member",family_meber);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "output_product"))) {
            //产出产品
            List<Map<String,Object>>   output_product= farmFamilyDetailService.findOutputProduct(params);

            perfectInformation.put("product",output_product);
        }


        if ("1".equals(MapUtils.getString(modulesInfo, "provide_services"))) {
            //提供服务
            List<Map<String,Object>> provide_services  = farmFamilyDetailService.findProvideServices(params);

            perfectInformation.put("provide",provide_services);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "purchase_intention"))) {
            //求购意向
            List<Map<String,Object>> purchase_intention  = farmFamilyDetailService.findPurchaseIntention(params);

            perfectInformation.put("purchase",purchase_intention);
        }


        if ("1".equals(MapUtils.getString(modulesInfo, "houses_condition"))) {
            //房屋生活情况
            List<Map<String,Object>> house_situation  = farmFamilyDetailService.findHouseSituation(params);

            perfectInformation.put("house",house_situation);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "home_condition"))) {
            // 家庭现代化情况
            List<Map<String,Object>> family_situation  = farmFamilyDetailService.findFamilySituation(params);

            perfectInformation.put("modern",family_situation);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "management_team"))) {
            //团队管理
            List<Map<String,Object>> management_team =perfectInformationMapper.findTeam(params);
            if(management_team.size()>0&&management_team!=null){
                for(Map<String,Object> memberMap : management_team) {
                    //成员照片，可有多个
                    String members = MapUtils.getString(memberMap, "avatar");
                    if (StringUtils.isBlank(members)) {
                        //没有成员照片
                        memberMap.put("avatar", new String[0]);
                    } else {
                        String[] memberArray = members.split(";");
                        memberMap.put("avatar", memberArray);
                    }
                    if(StringUtil.isNotEmpty(MapUtils.getString(memberMap,"team_status"))){
                        if(MapUtils.getString(memberMap,"team_status").equals("1")){
                            memberMap.put("team_status",true);
                        }else{
                            memberMap.put("team_status",false);
                        }
                    }else{
                        memberMap.put("team_status",false);
                    }
                }
            }

            perfectInformation.put("team",management_team);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "finance_information"))) {
            //财务信息
            Map<String,Object> finance_info =perfectInformationMapper.findFinance(params);
            if(finance_info!=null&&finance_info.size()>0){
                if(StringUtil.isNotEmpty(MapUtils.getString(finance_info,"finance_status"))){
                    if(MapUtils.getString(finance_info,"finance_status").equals("1")){
                        finance_info.put("finance_status",true);
                    }else{
                        finance_info.put("finance_status",false);
                    }
                }else{
                    finance_info.put("finance_status",false);
                }
            }
            perfectInformation.put("finance",finance_info);
        }

        return perfectInformation;

    }

    private Map<String,Object> publicMap(String model,String name,String status){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", StringUtils.isBlank(model) ? "" : model);
        maps.put("name", StringUtils.isBlank(name) ? "" : name);
        maps.put("status", "1".equals(status) ? true : false);
        return maps;

    }

    private Map<String,Object> modelMap(String model,String name){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", model);
        maps.put("name", name);
        return maps;
    }

    @Override
    @Transactional
    public void insertPerfectInformation(Map<String,Object> params){

        Map<String,Object> modulesInfo = (Map<String, Object>) params.get("modulesInfo");
        if ("1".equals(MapUtils.getString(modulesInfo, "education_live"))) {
            //教育经历
            perfectInformationMapper.deleteEducation(params);
            List<Map<String,Object>> educational_experience = (List<Map<String, Object>>) params.get("education");
            for(Map<String,Object> educational:educational_experience){
                Map<String,Object> education =  new HashMap<>();
                education.put("school_model",MapUtils.getString( (Map<String, Object>) educational.get("school"),"model"));
                education.put("school_name",MapUtils.getString( (Map<String, Object>) educational.get("school"),"name"));
                education.put("school_name_status", ((Map<String, Object>) educational.get("school")).get("status"));

                education.put("education_model",MapUtils.getString( (Map<String, Object>) educational.get("degree"),"model"));
                education.put("education",MapUtils.getString( (Map<String, Object>) educational.get("degree"),"name"));
                education.put("education_status", ((Map<String, Object>) educational.get("degree")).get("status"));

                education.put("major_model",MapUtils.getString( (Map<String, Object>) educational.get("major"),"model"));
                education.put("major",MapUtils.getString( (Map<String, Object>) educational.get("major"),"name"));
                education.put("major_status", ((Map<String, Object>) educational.get("major")).get("status"));

                education.put("is_general_model",MapUtils.getString( (Map<String, Object>) educational.get("recruitment"),"model"));
                education.put("is_general",MapUtils.getString( (Map<String, Object>) educational.get("recruitment"),"name"));
                education.put("is_general_status", ((Map<String, Object>) educational.get("recruitment")).get("status"));


                String memberPicture = MapUtils.getString((Map<String, Object>) educational.get("graduationTime"),"model");
                if(StringUtil.isNotEmpty(memberPicture)) {
                    JSONArray entrance_graduation_time_model = JSONArray.parseArray(memberPicture);
                    if (entrance_graduation_time_model.size() == 0) {
                        education.put("entrance_graduation_time_model", "");
                    } else {
                        String[] members = getJsonToStringArray(memberPicture);
                        String memberMerge = stringArrayMerge(members);
                        education.put("entrance_graduation_time_model", memberMerge);
                    }
                }else{
                    education.put("entrance_graduation_time_model", "");
                }
                education.put("entrance_graduation_time",MapUtils.getString( (Map<String, Object>) educational.get("graduationTime"),"name"));
                education.put("entrance_graduation_time_status", ((Map<String, Object>) educational.get("graduationTime")).get("status"));

                education.put("account",MapUtils.getString(params,"account"));

                perfectInformationMapper.insertEducational(education);
            }
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "work_live"))) {
            //工作经历
            perfectInformationMapper.deleteWork(params);
            List<Map<String,Object>> work_experience = (List<Map<String, Object>>) params.get("work");
            for(Map<String,Object> work:work_experience) {
                Map<String, Object> workExperience = new HashMap<>();
                workExperience.put("work_unit_model", MapUtils.getString((Map<String, Object>) work.get("WorkUnit"), "model"));
                workExperience.put("work_unit", MapUtils.getString((Map<String, Object>) work.get("WorkUnit"), "name"));
                workExperience.put("work_unit_status", ((Map<String, Object>) work.get("WorkUnit")).get("status"));

                workExperience.put("work_position_model", MapUtils.getString((Map<String, Object>) work.get("job"), "model"));
                workExperience.put("work_position", MapUtils.getString((Map<String, Object>) work.get("job"), "name"));
                workExperience.put("work_position_status", ((Map<String, Object>) work.get("job")).get("status"));


                String memberPicture = MapUtils.getString((Map<String, Object>) work.get("workTime"), "model");
                if (StringUtil.isNotEmpty(memberPicture)){
                    JSONArray workTime = JSONArray.parseArray(memberPicture);
                    if (workTime.size() == 0) {
                        workExperience.put("work_time_model", "");
                    } else {
                        String[] members = getJsonToStringArray(memberPicture);
                        String memberMerge = stringArrayMerge(members);
                        workExperience.put("work_time_model", memberMerge);
                    }
                }else{
                    workExperience.put("work_time_model", "");
                }
                workExperience.put("work_time",MapUtils.getString( (Map<String, Object>) work.get("workTime"),"name"));
                workExperience.put("work_time_status", ((Map<String, Object>) work.get("workTime")).get("status"));

                workExperience.put("work_details_model",MapUtils.getString( (Map<String, Object>) work.get("detail"),"model"));
                workExperience.put("work_details",MapUtils.getString( (Map<String, Object>) work.get("detail"),"name"));
                workExperience.put("work_details_status", ((Map<String, Object>) work.get("detail")).get("status"));

                workExperience.put("account",MapUtils.getString(params,"account"));

                perfectInformationMapper.insertWork(workExperience);
            }
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "honour_style"))) {
            //荣誉风采
            perfectInformationMapper.deleteHonor(params);
            List<Map<String,Object>> honor_info = (List<Map<String, Object>>) params.get("corpHonor");
            for (Map<String,Object> honor : honor_info) {
                honor.put("account",MapUtils.getString(params,"account"));
                //荣誉证书，可有多个
                String honorPicture = MapUtils.getString(honor, "honorPictureList");
                JSONArray honorArray = JSONArray.parseArray(honorPicture);
                if (honorArray.size() == 0) {
                    honor.put("honorPictureList", "");
                } else {
                    String[] honors = getJsonToStringArray(honorPicture);
                    String honorMerge = stringArrayMerge(honors);
                    honor.put("honorPictureList", honorMerge);
                }
                perfectInformationMapper.insertHonor(honor);
            }
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "family_Member"))) {
            //家庭成员
            farmFamilyDetailService.insertFamilyMember(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "output_product"))) {
            //产出产品
            farmFamilyDetailService.insertOutputProduct(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "provide_services"))) {
            //提供服务
            farmFamilyDetailService.insertProvideServices(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "purchase_intention"))) {
            //求购意向
            farmFamilyDetailService.insertPurchaseIntention(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "houses_condition"))) {
            //房屋生活情况
            farmFamilyDetailService.insertHouseSituation(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "home_condition"))) {
            //家庭现代化情况
            farmFamilyDetailService.insertFamilySituation(params);
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "management_team"))) {
            //团队管理
            perfectInformationMapper.deleteTeam(params);
            List<Map<String,Object>> management_team = (List<Map<String, Object>>) params.get("team");
            for (Map<String,Object> team : management_team) {
                team.put("account",MapUtils.getString(params,"account"));
                //成员照片，可有多个
                String memberPicture = MapUtils.getString(team, "avatar");
                JSONArray memberArray = JSONArray.parseArray(memberPicture);
                if (memberArray.size() == 0) {
                    team.put("avatar", "");
                } else {
                    String[] members = getJsonToStringArray(memberPicture);
                    String memberMerge = stringArrayMerge(members);
                    team.put("avatar", memberMerge);
                }
                perfectInformationMapper.insertTeam(team);
            }
        }

        if ("1".equals(MapUtils.getString(modulesInfo, "finance_information"))) {
            //财务信息
            perfectInformationMapper.deleteFinance(params);
            Map<String,Object> finance_info = (Map<String, Object>) params.get("assetFinance");
            finance_info.put("account",MapUtils.getString(params,"account"));
            perfectInformationMapper.insertFinance(finance_info);
        }
    }

    /**
     * 插入隐私信息
     * @param privacyInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertPrivacyInfo(String account, Map<String, Object> privacyInfo) {

        try {
            if (!privacyInfo.isEmpty()) {
                //先删除用户相关数据
                perfectInformationMapper.deletePrivacyInfoByAccount(account);
                //再插入相关数据
                privacyInfo.put("account", account);
                perfectInformationMapper.insertPrivacyInfo(privacyInfo);
                //认证第五步修改隐私信息时，同步更新certification表中的手机号、常驻地两个信息

                String sex  = MapUtils.getString( (Map<String, Object>) privacyInfo.get("sex"),"model");
                if(sex.equals("男")){
                    privacyInfo.put("sexual",1);
                }else{
                    privacyInfo.put("sexual",0);
                }
                certificationService.UpdatePrivacyInfo(privacyInfo);
                //认证第五步修改隐私信息时，同步更新login_user表中的头像信息
                LoginUser loginUser = new LoginUser();
                loginUser.setLoginAccount(account);
                loginUser.setAvatar(MapUtils.getString(privacyInfo, "avatar"));
                loginUserService.updateByAccount(loginUser);

                logger.info("保存隐私信息成功");
            }
        } catch (Exception e) {
            logger.error("保存隐私信息过程出错：{}", e);
        }
    }

    /**
     * 插入网络信息
     * @param networkInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertNetworkInfo(String account, Map<String, Object> networkInfo) {

        try {
            //先删除用户相关数据
            perfectInformationMapper.deleteNetworkInfoByAccount(account);
            if (!networkInfo.isEmpty()) {

                //再插入相关数据
                networkInfo.put("account", account);
                perfectInformationMapper.insertNetworkInfoInfo(networkInfo);
                logger.info("保存网络信息成功");
            }
        } catch (Exception e) {
            logger.error("保存网络信息过程出错：{}", e);
        }
    }

    /**
     * 插入政治面貌信息
     * @param account
     * @param networkInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertPolicyInfo(String account, Map<String, Object> policyInfo) {

        try {
            //先删除用户相关数据
            perfectInformationMapper.deletePolicyInfoByAccount(account);
            if (!policyInfo.isEmpty()) {

                //再插入相关数据
                policyInfo.put("account", account);
                perfectInformationMapper.insertPolicyInfoInfo(policyInfo);
                logger.info("保存政治面貌信息成功");
            }
        } catch (Exception e) {
            logger.error("保存政治面貌信息过程出错：{}", e);
        }

    }

    /**
     * 插入宗教信仰信息
     * @param account
     * @param religionInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertReligionInfo(String account, Map<String, Object> religionInfo) {

        try {
            //先删除用户相关数据
            perfectInformationMapper.deleteReligionInfoByAccount(account);
            if (!religionInfo.isEmpty()) {

                //再插入相关数据
                religionInfo.put("account", account);
                perfectInformationMapper.insertReligionInfoInfo(religionInfo);
                logger.info("保存宗教信仰信息成功");
            }
        } catch (Exception e) {
            logger.error("保存宗教信仰信息过程出错：{}", e);
        }

    }

    /**
     * 插入经营概况信息
     * @param account
     * @param manageSurveyInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertManageSurveyInfo(String account, Map<String, Object> manageSurveyInfo) {

        try {
            //先删除用户相关数据
            perfectInformationMapper.deleteManageSurveyByAccount(account);
            if (!manageSurveyInfo.isEmpty()) {

                //再插入相关数据
                manageSurveyInfo.put("account", account);
                perfectInformationMapper.insertManageSurveyInfo(manageSurveyInfo);
                logger.info("保存经营概况信息成功");
            }
        } catch (Exception e) {
            logger.error("保存经营概况信息过程出错：{}", e);
        }

    }

    /**
     * 插入专业信息
     * @param account
     * @param professionInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertProfessionInfo(String account, Map<String, Object> professionInfo) {
        try {
            String expertType = MapUtils.getString(professionInfo, "expertType");
            if (StringUtils.isNotBlank(expertType)) {
                //传输的数据非空
                Map<String, Object> hisProfession = perfectInformationMapper.getProfessionInfoByAccount(account);
                //先删除用户相关数据
                perfectInformationMapper.deleteProfessionInfoByAccount(account);
                //再插入相关数据
                professionInfo.put("account", account);
                //个人照片，可有多个
                String personalPicture = MapUtils.getString(professionInfo, "personalPicture");
                JSONArray pictureArray = JSONArray.parseArray(personalPicture);
                if (pictureArray.size() == 0) {
                    professionInfo.put("personalPicture", "");
                } else {
                    String[] pictures = getJsonToStringArray(personalPicture);
                    String pictureMerge = stringArrayMerge(pictures);
                    professionInfo.put("personalPicture", pictureMerge);
                }
                //资质证书，可有多个
                String qualificationCertificate = MapUtils.getString(professionInfo, "qualificationsPicture");
                JSONArray certificateArray = JSONArray.parseArray(qualificationCertificate);
                if (certificateArray.size() == 0) {
                    professionInfo.put("qualificationsPicture", "");
                } else {
                    String[] certificates = getJsonToStringArray(qualificationCertificate);
                    String certificateMerge = stringArrayMerge(certificates);
                    professionInfo.put("qualificationsPicture", certificateMerge);
                }
                //荣誉证书，可有多个
                String honorPicture = MapUtils.getString(professionInfo, "honorPicture");
                JSONArray honorArray = JSONArray.parseArray(honorPicture);
                if (honorArray.size() == 0) {
                    professionInfo.put("honorPicture", "");
                } else {
                    String[] honors = getJsonToStringArray(honorPicture);
                    String honorMerge = stringArrayMerge(honors);
                    professionInfo.put("honorPicture", honorMerge);
                }
                if (MapUtils.isNotEmpty(hisProfession)) {
                    if (StringUtils.isNotBlank(MapUtils.getString(hisProfession, "approveStatus"))) {
                        professionInfo.put("approveStatus", MapUtils.getString(hisProfession, "approveStatus"));
                    }
                }
                perfectInformationMapper.insertProfessionInfo(professionInfo);
                //乡村、企业、机关配置了专家信息后，仍保存原来的用户性质
                //个人认证填写专业信息后，更新login_user表中的userType为4，4代表为专家类型
                LoginUser userInfo = loginUserService.findByLoginName(account);
                if (userInfo.getUserType() == 0) {
                    LoginUser loginUser = new LoginUser();
                    loginUser.setLoginAccount(account);
                    loginUser.setUserType(4);
                    loginUserService.updateByAccount(loginUser);
                }
                logger.info("保存专业信息成功");
            } else {
                logger.info("专业信息为空，不保存");
            }

        } catch (Exception e) {
            logger.error("保存专业信息过程出错：{}", e);
        }

    }

    /**
     * 插入专业资质信息
     * @param account
     * @param qualificationInfo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertProfessionalQualification(String account, List<Map<String, Object>> qualificationList) {
        try {
            for (Map<String, Object> qualification : qualificationList) {
                qualification.put("account", account);

                //资质证书，可有多个
                String files = MapUtils.getString(qualification, "qualificationPictureList");
                JSONArray fileArray = JSONArray.parseArray(files);
                if (fileArray.size() == 0) {
                    qualification.put("qualificationPictureList", "");
                } else {
                    String[] quas = getJsonToStringArray(files);
                    String quaMerge = stringArrayMerge(quas);
                    qualification.put("qualificationPictureList", quaMerge);
                }

            }
            //先删除用户相关数据
            perfectInformationMapper.deleteQualificationByAccount(account);
            if (qualificationList.size() > 0) {

                //再插入相关数据
                perfectInformationMapper.insertQualificationInfo(qualificationList);
                logger.info("保存专业资质信息成功");
            } else {
                logger.info("没有专业资质信息需保存");
            }

        } catch (Exception e) {
            logger.error("保存专业资质信息过程出错：{}", e);
        }

    }

    /**
     * 插入产品服务信息
     * @param account
     * @param productServiceList
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertProductService(String account, List<Map<String, Object>> productServiceList) {
        try {
            for (Map<String, Object> productService : productServiceList) {
                productService.put("account", account);

                //图片，可有多个
                String images = MapUtils.getString(productService, "pictureList");
                JSONArray imageArray = JSONArray.parseArray(images);
                if (imageArray.size() == 0) {
                    productService.put("pictureList", "");
                } else {
                    String[] products = getJsonToStringArray(images);
                    String productMerge = stringArrayMerge(products);
                    productService.put("pictureList", productMerge);
                }

                //资质证书，可有多个
                String certificates = MapUtils.getString(productService, "certificateList");
                JSONArray certificateArray = JSONArray.parseArray(certificates);
                if (certificateArray.size() == 0) {
                    productService.put("certificateList", "");
                } else {
                    String[] products = getJsonToStringArray(certificates);
                    String productMerge = stringArrayMerge(products);
                    productService.put("certificateList", productMerge);
                }

            }
            //先删除用户相关数据
            perfectInformationMapper.deleteProductServiceByAccount(account);
            if (productServiceList.size() > 0) {
                //再插入相关数据
                perfectInformationMapper.insertProductService(productServiceList);
                logger.info("保存产品服务信息成功");
            } else {
                logger.info("没有产品服务信息需保存");
            }


        } catch (Exception e) {
            logger.error("保存产品服务信息过程出错：{}", e);
        }
    }

    @Override
    public Map<String,Object> findGateway(Map<String,Object> params){
        Map<String,Object> gateWay = new HashMap<>();
        String account = MapUtils.getString(params,"account");
        Map<String,Object> privacyInfo = perfectInformationMapper.getPrivacyInfoByAccount(account);
        String privateInformation = "";

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"name_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"name") +"："+MapUtils.getString(privacyInfo,"name_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"sex_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"sex") +"："+MapUtils.getString(privacyInfo,"sex_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"birthday_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"birthday") +"："+MapUtils.getString(privacyInfo,"birthday_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"profession_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"profession") +"："+MapUtils.getString(privacyInfo,"profession_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"position_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"position") +"："+MapUtils.getString(privacyInfo,"position_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"excel_species_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"excel_species") +"："+MapUtils.getString(privacyInfo,"excel_species_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"phone_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"phone") +"："+MapUtils.getString(privacyInfo,"phone_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"resident_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"resident") +"："+MapUtils.getString(privacyInfo,"resident_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"coordinate_position_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"coordinate_position") +"："+MapUtils.getString(privacyInfo,"coordinate_position_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"postal_code_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"postal_code") +"："+MapUtils.getString(privacyInfo,"postal_code_model")+"，";
        }

        if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"telephone_model"))) {
            privateInformation += MapUtils.getString(privacyInfo,"telephone") +"："+MapUtils.getString(privacyInfo,"telephone_model")+"。";
        }
        gateWay.put("privateInformation",privateInformation);

        Map<String,Object> networkInfo = perfectInformationMapper.getNetworkInfoByAccount(account);

        String networkInformation = "";
        if(StringUtil.isNotBlank(MapUtils.getString(networkInfo,"qq_number_model"))) {
            networkInformation += MapUtils.getString(networkInfo,"qq_number") +"："+MapUtils.getString(networkInfo,"qq_number_model")+"，";
        }
        if(StringUtil.isNotBlank(MapUtils.getString(networkInfo,"email_model"))) {
            networkInformation += MapUtils.getString(networkInfo,"email") +"："+MapUtils.getString(networkInfo,"email_model")+"，";
        }
        if(StringUtil.isNotBlank(MapUtils.getString(networkInfo,"apply_domain_model"))) {
            networkInformation += MapUtils.getString(networkInfo,"apply_domain") +"："+MapUtils.getString(networkInfo,"apply_domain_model")+"。";
        }
        gateWay.put("networkInformation",networkInformation);

        // 教育经历
        List<Map<String,Object>> educations = perfectInformationMapper.findEducation(params);
        String education_live = "";
        for(Map<String,Object> education:educations){
            if(StringUtil.isNotBlank(MapUtils.getString(education,"school_model"))) {
                education_live += MapUtils.getString(education,"school_name") +"："+MapUtils.getString(education,"school_model")+"，";
            }

            if(StringUtil.isNotBlank(MapUtils.getString(education,"education_model"))) {
                education_live += MapUtils.getString(education,"education") +"："+MapUtils.getString(education,"education_model")+"，";
            }

            if(StringUtil.isNotBlank(MapUtils.getString(education,"major_model"))) {
                education_live += MapUtils.getString(education,"major") +"："+MapUtils.getString(education,"major_model")+"，";
            }

            if(StringUtil.isNotBlank(MapUtils.getString(education,"is_general_model"))) {
                education_live += MapUtils.getString(education,"is_general") +"："+MapUtils.getString(education,"is_general_model")+"，";
            }

            if(StringUtil.isNotBlank(MapUtils.getString(education,"entrance_graduation_time_model"))) {
                education_live += MapUtils.getString(education,"entrance_graduation_time") +"："+MapUtils.getString(education,"entrance_graduation_time_model")+"。";
            }
        }

        gateWay.put("education",education_live);

        // 工作经历
        List<Map<String,Object>> works = perfectInformationMapper.findWork(params);
        String work_live = "";
        for(Map<String,Object> work:works){
            if(StringUtil.isNotBlank(MapUtils.getString(work,"work_unit_model"))) {

                work_live += MapUtils.getString(work,"work_unit") +"："+MapUtils.getString(work,"work_unit_model")+"，";
            }
            if(StringUtil.isNotBlank(MapUtils.getString(work,"work_position_model"))) {

                work_live += MapUtils.getString(work,"work_position") +"："+MapUtils.getString(work,"work_position_model")+"，";
            }
            if(StringUtil.isNotBlank(MapUtils.getString(work,"work_unit_model"))) {

                work_live += MapUtils.getString(work,"work_time") +"："+MapUtils.getString(work,"work_time_model")+"，";
            }
            if(StringUtil.isNotBlank(MapUtils.getString(work,"work_details_model"))) {

                work_live += MapUtils.getString(work,"work_details") +"："+MapUtils.getString(work,"work_details_model")+"。";
            }
        }
        gateWay.put("work",work_live);

        Map<String,Object> policyInfo  = perfectInformationMapper.getPolicyInfoByAccount(account);
        String policy = "";
        if (MapUtils.isNotEmpty(policyInfo)) {
            if(StringUtil.isNotBlank(MapUtils.getString(policyInfo,"political_face_model"))) {
                policy += MapUtils.getString(policyInfo, "political_face") + "：" + MapUtils.getString(policyInfo, "political_face_model") +"，";

            }
            if(StringUtil.isNotBlank(MapUtils.getString(policyInfo,"join_time_model"))) {

                policy +=    MapUtils.getString(policyInfo, "join_time") + "：" + MapUtils.getString(policyInfo, "join_time_model")+"。";
            }
        }
        gateWay.put("policy",policy);

        Map<String,Object> religionInfo = perfectInformationMapper.getReligionInfoByAccount(account);
        String religion = "";
        if (MapUtils.isNotEmpty(religionInfo)) {
            if(StringUtil.isNotBlank(MapUtils.getString(religionInfo,"belief_church_model"))){
                religion += MapUtils.getString(religionInfo,"belief_church") +"："+MapUtils.getString(religionInfo,"belief_church_model")+"。";
            }


        }
        gateWay.put("religion",religion);


        return gateWay;
    }

    /**
     * 获取隐私信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> getPrivacyInfo(String account) {

        Map<String,Object> privacyInfo = perfectInformationMapper.getPrivacyInfoByAccount(account);
        //LinkedHashMap,保证有序
        Map<String, Object> privacyMap = new LinkedHashMap<>();
        //从certification表中获取姓名、手机号、常驻地的数据,从login_user表中获取头像信息
        Certification userInfo = certificationService.selectByAccount(account);
        if (MapUtils.isNotEmpty(privacyInfo)) {

            privacyMap.put("avatar", MapUtils.getString(privacyInfo,"image"));
            privacyMap.put("userName", publicMap(userInfo.getRealname(),
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

            String addr;
            if(StringUtil.isNotBlank(MapUtils.getString(privacyInfo,"addr_Detail"))){
                addr = MapUtils.getString(privacyInfo,"addr_Detail");
            }else{
                addr = "详细地址";
            }

            privacyMap.put("addrDetail", publicMap(MapUtils.getString(privacyInfo,"addr_Detail_model"),
                    addr,MapUtils.getString(privacyInfo,"addr_Detail_status")));
        } else {
            LoginUser loginUser = loginUserService.findByLoginName(account);
            String image = loginUser.getAvatar();
            if (StringUtils.isNotBlank(image)) {
                if (image.startsWith("http:")) {
                    image = image.substring(5);
                }
            } else {
                image = "";
            }

            Map<String,Object> map = perfectInformationMapper.findAddrDetail(account);
            privacyMap.put("avatar", image);
            privacyMap.put("userName", publicMap(userInfo.getRealname(), "姓名","1"));
            privacyMap.put("sex", publicMap("", "性别","1"));
            privacyMap.put("ethnicGroup", publicMap("", "民族","1"));
            privacyMap.put("birthday", publicMap("", "生日","1"));
            privacyMap.put("profession", publicMap("", "职业","1"));
            privacyMap.put("professionalTitle", publicMap("", "职称","1"));
            privacyMap.put("species", publicMap("", "擅长物种","1"));
            privacyMap.put("phone", publicMap(userInfo.getMobile(), "手机号","1"));
            privacyMap.put("coordinatePoint", publicMap("", "坐标位置","1"));
            privacyMap.put("postalCode", publicMap("", "邮政编码","1"));
            privacyMap.put("tel", publicMap("", "座机号码","1"));

            privacyMap.put("number", publicMap("", "门牌号","1"));
            privacyMap.put("addr", publicMap(userInfo.getCity(), "常驻地","1"));

            privacyMap.put("addrDetail", publicMap(MapUtils.getString(map,"addr_Detail"), "详细地址","1"));
    }

        logger.info("根据用户：{}, 获取的隐私信息为：{}", account, privacyMap);

        return privacyMap;
    }

    /**
     * 获取网络信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> getNetworkInfo(String account) {

        Map<String,Object> networkInfo = perfectInformationMapper.getNetworkInfoByAccount(account);
        Map<String, Object> networkMap = new LinkedHashMap<>();
        if (MapUtils.isNotEmpty(networkInfo)) {
            networkMap.put("status", "1".equals(MapUtils.getString(networkInfo,"authority")) ? true : false);
            networkMap.put("ID", modelMap(MapUtils.getString(networkInfo,"nswy_id_model"),
                    MapUtils.getString(networkInfo,"nswy_id")));

            networkMap.put("QQ", modelMap(MapUtils.getString(networkInfo,"qq_number_model"),
                    MapUtils.getString(networkInfo,"qq_number")));

            networkMap.put("Email", modelMap(MapUtils.getString(networkInfo,"email_model"),
                    MapUtils.getString(networkInfo,"email")));

            networkMap.put("domainName", modelMap(MapUtils.getString(networkInfo,"apply_domain_model"),
                    MapUtils.getString(networkInfo,"apply_domain")));
        } else {
            //networkInfo = new HashMap<>();
            String nswyId = generateNswyId();
            networkMap.put("status", true);
            networkMap.put("ID", modelMap(nswyId,"农事无忧ID"));
            networkMap.put("QQ", modelMap("","QQ号码"));
            networkMap.put("Email", modelMap("","邮箱"));
            networkMap.put("domainName", modelMap("","申请域名"));
        }

        logger.info("根据用户：{}, 获取的网络信息为：{}", account, networkMap);

        return networkMap;
    }

    /**
     * 获取政治面貌信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> getPolicyInfo(String account) {

        Map<String,Object> policyInfo = new HashMap<>();
        policyInfo = perfectInformationMapper.getPolicyInfoByAccount(account);
        if (MapUtils.isNotEmpty(policyInfo)) {
            policyInfo.put("policy", publicMap(MapUtils.getString(policyInfo,"political_face_model"),
                    MapUtils.getString(policyInfo,"political_face"),MapUtils.getString(policyInfo,"political_face_status")));

            policyInfo.put("joinTime", publicMap(MapUtils.getString(policyInfo,"join_time_model"),
                    MapUtils.getString(policyInfo,"join_time"),MapUtils.getString(policyInfo,"join_time_status")));
        }
        logger.info("根据用户：{}, 获取的政治面貌信息为：{}", account, policyInfo);

        return policyInfo;
    }

    /**
     * 获取宗教信仰信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> getReligionInfo(String account) {

        Map<String,Object> religionInfo = new HashMap<>();
        religionInfo = perfectInformationMapper.getReligionInfoByAccount(account);
        if (MapUtils.isNotEmpty(religionInfo)) {
            religionInfo.put("religion", publicMap(MapUtils.getString(religionInfo,"belief_church_model"),
                    MapUtils.getString(religionInfo,"belief_church"),MapUtils.getString(religionInfo,"belief_church_status")));
        }
        logger.info("根据用户：{}, 获取的宗教信仰信息为：{}", account, religionInfo);

        return religionInfo;
    }

    /**
     * 获取经营概况信息
     * @param account
     */
    @Override
    public Map<String, Object> getManageSurveyInfo(String account) {

        Map<String,Object> manageSurveyInfo = perfectInformationMapper.getManageSurveyByAccount(account);
        if(manageSurveyInfo!=null&&manageSurveyInfo.size()>0){
            if(StringUtil.isNotEmpty(MapUtils.getString(manageSurveyInfo,"manage_status"))){
                if(MapUtils.getString(manageSurveyInfo,"manage_status").equals("1")){
                    manageSurveyInfo.put("manage_status",true);
                }else{
                    manageSurveyInfo.put("manage_status",false);
                }
            }else{
                manageSurveyInfo.put("manage_status",false);
            }
        }

        logger.info("根据用户：{}, 获取的经营概况信息为：{}", account, manageSurveyInfo);

        return manageSurveyInfo;

    }

    /**
     * 获取专业信息
     * @param account
     */
    @Override
    public Map<String, Object> getProfessionInfo(String account) {

        Map<String,Object> professionInfo = new HashMap<>();
        professionInfo = perfectInformationMapper.getProfessionInfoByAccount(account);
        if (MapUtils.isNotEmpty(professionInfo)) {
            String personalPicture = MapUtils.getString(professionInfo, "personal_photo");
            if (StringUtils.isBlank(personalPicture)) {
                //没有个人照片信息
                professionInfo.put("personalPicture", new String[0]);
            } else {
                String[] pictures = personalPicture.split(";");
                professionInfo.put("personalPicture", pictures);
            }
            String qualificationCertificate = MapUtils.getString(professionInfo, "qualification_certificate");
            if (StringUtils.isBlank(qualificationCertificate)) {
                //没有资质证书信息
                professionInfo.put("qualificationsPicture", new String[0]);
            } else {
                String[] certificates = qualificationCertificate.split(";");
                professionInfo.put("qualificationsPicture", certificates);
            }
            String honorCertificate = MapUtils.getString(professionInfo, "honor_certificate");
            if (StringUtils.isBlank(honorCertificate)) {
                //没有荣誉证书信息
                professionInfo.put("honorPicture", new String[0]);
            } else {
                String[] honors = honorCertificate.split(";");
                professionInfo.put("honorPicture", honors);
            }
            if(StringUtil.isNotEmpty(MapUtils.getString(professionInfo,"expert_status"))){
                if(MapUtils.getString(professionInfo,"expert_status").equals("1")){
                    professionInfo.put("expert_status",true);
                }else{
                    professionInfo.put("expert_status",false);
                }
            }else{
                professionInfo.put("expert_status",false);
            }

        }
        logger.info("根据用户：{}, 获取的专业信息为：{}", account, professionInfo);

        return professionInfo;
    }

    /**
     * 获取专业资质信息
     * @param account
     */
    @Override
    public List<Map<String, Object>> getProfessionalQualification(String account) {

        List<Map<String,Object>> qualificationList = new ArrayList<>();
        qualificationList = perfectInformationMapper.getQualificationByAccount(account);
        if (qualificationList.size() > 0) {
            for (Map<String, Object> qualification : qualificationList) {

                //资质证书，可有多个
                String files = MapUtils.getString(qualification, "qualification_file");

                if (StringUtils.isBlank(files)) {
                    //没有资质证书
                    qualification.put("qualificationPictureList", new String[0]);
                } else {
                    String[] pictures = files.split(";");
                    qualification.put("qualificationPictureList", pictures);
                }

                if(StringUtil.isNotEmpty(MapUtils.getString(qualification,"professional_status"))){
                    if(MapUtils.getString(qualification,"professional_status").equals("1")){
                        qualification.put("professional_status",true);
                    }else{
                        qualification.put("professional_status",false);
                    }
                }else{
                    qualification.put("professional_status",false);
                }
            }
        }
        logger.info("根据用户：{}, 获取的专业资质信息为：{}", account, qualificationList);

        return qualificationList;
    }

    /**
     * 获取产品服务信息
     * @param account
     */
    @Override
    public List<Map<String, Object>> getProductService(String account) {

        List<Map<String,Object>> productServiceList = new ArrayList<>();
        productServiceList = perfectInformationMapper.getProductServiceByAccount(account);
        if (productServiceList.size() > 0) {
            for (Map<String, Object> productService : productServiceList) {

                //图片，可有多个
                String images = MapUtils.getString(productService, "image");
                if (StringUtils.isBlank(images)) {
                    //没有资质证书
                    productService.put("pictureList", new String[0]);
                } else {
                    String[] pictures = images.split(";");
                    productService.put("pictureList", pictures);
                }

                //资质证书，可有多个
                String certificates = MapUtils.getString(productService, "qualification_certificate");
                if (StringUtils.isBlank(certificates)) {
                    //没有资质证书
                    productService.put("certificateList", new String[0]);
                } else {
                    String[] pictures = certificates.split(";");
                    productService.put("certificateList", pictures);
                }

                if(StringUtil.isNotEmpty(MapUtils.getString(productService,"product_status"))){
                    if(MapUtils.getString(productService,"product_status").equals("1")){
                        productService.put("product_status",true);
                    }else{
                        productService.put("product_status",false);
                    }
                }else{
                    productService.put("product_status",false);
                }

            }
        }
        logger.info("根据用户：{}, 获取的产品服务信息为：{}", account, productServiceList);

        return productServiceList;
    }

    /**
     * 根据用户查询完善信息的模块
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> getModulesByAccount(String account) {
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("account", account);

        LoginUser user = loginUserService.findByLoginName(account);
        int userType = user.getUserType();
        if (4 == userType) {
            //如果认证了专家，审核没通过时为普通用户
            int approveStatus = expertInfoService.getApproveStatus(user.getLoginAccount());
            user.setUserType(approveStatus == 1 ? 4 : 0);

        }
        userInfo.put("userType", user.getUserType());

        List<Map<String,Object>> sysUserInfo  = perfectInformationMapper.findSysUserInfo(userInfo);

        return sysUserInfo.get(0);
    }

    /**
     * json转为String数组
     * @param str
     * @return
     */
    public static String[] getJsonToStringArray(String str) {
        JSONArray jsonArray = JSONArray.parseArray(str);
        String[] arr=new String[jsonArray.size()];
        for(int i=0;i<jsonArray.size();i++){
            arr[i]=jsonArray.getString(i);
            System.out.println(arr[i]);
        }
        return arr;
    }

    /**
     * 将String数组用分号分隔
     * @param strArray
     * @return
     */
    public static String stringArrayMerge(String[] strArray) {

        StringBuffer sb = new StringBuffer();
        for (String str : strArray) {
            sb.append(str).append(";");
        }
        String sbString = sb.toString();
        String result = sbString.substring(0, sbString.length() - 1);

        return result;
    }

    /**
     * 生成农事无忧ID
     * @return
     */
    public static String generateNswyId() {

        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int num =(int)(Math.random()*900)+100;
        String nswyId = "NSWY" + currentTime + String.valueOf(num);
        return nswyId;
    }

    @Override
    public List<Map<String, Object>> findDepartById(Map<String,Object> params) {
        return perfectInformationMapper.findDepartById(params);
    }
    //更新组织机构
    @Override
    public void updateDepartStatus(Map<String,Object> params){
        perfectInformationMapper.updateDepartStatus(params);
    }



    List<Map<String,Object>> departmentList = new ArrayList<>();

    @Override
    public List<Map<String,Object>> findDepartment(Map<String,Object> params){

        departmentList= perfectInformationMapper.findDepartList(params);

        List<Map<String,Object>> treeNodes = new ArrayList<>();
        List<Map<String,Object>> rootNodes = this.getRootNodes(departmentList);
        for (Map<String,Object> rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    //保存组织机构
    @Override
    public void insertOrganization(Map<String,Object> params){
         perfectInformationMapper.insertOrganization(params);
    }

    //更新组织机构
    @Override
    public void updateOrganization(Map<String,Object> params){
        perfectInformationMapper.updateOrganization(params);
    }

    @Override
    public void deleteDepartment(Map<String,Object> params){
        perfectInformationMapper.deleteDepartment(params);
    }

    // 获取集合中所有的根节点
    public List<Map<String,Object>> getRootNodes(List<Map<String,Object>> list) {
        List<Map<String,Object>> rootNodes = new ArrayList<>();
        for (Map<String,Object> node : list) {
            if (rootNode(node)) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    // 判断一级节点
    public boolean rootNode(Map<String,Object> nodes) {
        boolean isRootNode = false;
        if("0".equals(MapUtils.getString(nodes,"pid"))){
            isRootNode = true;

        }
        return isRootNode;
    }

    // 递归子节点
    public void buildChildNodes(Map<String,Object> node) {
        List<Map<String,Object>> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Map<String,Object> child : children) {
                buildChildNodes(child);
            }
            node.put("children",children);
        }
    }

    // 获取父节点下所有的子节点
    public List<Map<String,Object>> getChildNodes(Map<String,Object> pnode) {
        List<Map<String,Object>> childNodes = new ArrayList<>();
        for (Map<String,Object> n : departmentList) {
            if(MapUtils.getString(pnode,"id").equals(MapUtils.getString(n,"pid"))){
                childNodes.add(n);
            }
        }
        return childNodes;
    }


    @Override
    public Map<String,Object> findWorkTime(Map<String,Object> params){
        Map<String,Object> work = new HashMap<>();
        List<Map<String,Object>> works = perfectInformationMapper.findWorkTime(params);

        if(works.size()>0){
            Map<String,Object> workTime = works.get(0);
            work.put("WorkUnit",this.publicMap(MapUtils.getString(workTime,"work_unit_model"),
                    MapUtils.getString(workTime,"work_unit"),MapUtils.getString(workTime,"work_unit_status")));

            work.put("job",this.publicMap(MapUtils.getString(workTime,"work_position_model"),
                    MapUtils.getString(workTime,"work_position"),MapUtils.getString(workTime,"work_position_status")));

            Map<String,Object> workMap = this.publicMap(MapUtils.getString(workTime,"work_time_model"),
                    MapUtils.getString(workTime,"work_time"),MapUtils.getString(workTime,"work_time_status"));
            String memberPicture = MapUtils.getString(workMap,"model");
            if(StringUtil.isNotEmpty(memberPicture)) {
                String[] members = memberPicture.split(";");
                workMap.put("model",members);
            }else{
                workMap.put("model", new String[]{});
            }
            work.put("workTime",workMap);

            work.put("detail",this.publicMap(MapUtils.getString(workTime,"work_details_model"),
                    MapUtils.getString(workTime,"work_details"),MapUtils.getString(workTime,"work_details_status")));

        }

        return work;
    }

    /**
     * 设施资产
     * @param params
     * @return
     */
    @Override
    public  List<Map<String,Object>> findFacilityAssets(Map<String,Object> params){

        List<Map<String,Object>> list  = perfectInformationMapper.findFacilityAssets(params);
        for(Map<String,Object> assets:list){
            String assetPicture = MapUtils.getString(assets, "assetPicture");
            if (StringUtils.isBlank(assetPicture)) {
                assets.put("assetPicture", new String[]{});
            } else {
                String[] pictures = assetPicture.split(";");
                assets.put("assetPicture", pictures);
            }
            if(StringUtil.isNotEmpty(MapUtils.getString(assets,"assets_status"))){
                if(MapUtils.getString(assets,"assets_status").equals("1")){
                    assets.put("assets_status",true);
                }else{
                    assets.put("assets_status",false);
                }
            }else{
                assets.put("assets_status",false);
            }
        }
        return list;
    }
    @Override
     public void insertFacilityAssets(Map<String,Object> params){
        perfectInformationMapper.deleteFacilityAssets(params);
        List<Map<String,Object>> facility_assets = (List<Map<String, Object>>) params.get("facilityAssets");
        for (Map<String,Object> assets : facility_assets) {
            String farm_family_id= MapUtils.getString(params,"farm_family_id");
            //通过ID来判断是否为种养户还是认证
            if(StringUtil.isNotEmpty(farm_family_id)){
                assets.put("farm_family_id",farm_family_id);
                assets.put("account","");

            }else{
                assets.put("farm_family_id",farm_family_id);
                assets.put("account",MapUtils.getString(params,"account"));

            }
            String assetPicture = MapUtils.getString(assets, "assetPicture");
            JSONArray picture = JSONArray.parseArray(assetPicture);
            if (picture.size() == 0) {
                assets.put("assetPicture", "");
            } else {
                String[] string = getJsonToStringArray(assetPicture);
                String asset = stringArrayMerge(string);
                assets.put("assetPicture", asset);
            }

            perfectInformationMapper.insertFacilityAssets(assets);
        }

     }

    /**
     * 无形资产
     * @param params
     * @return
     */
    @Override
     public List<Map<String,Object>> findIntangibleAssets(Map<String,Object> params){

        List<Map<String,Object>> list  = perfectInformationMapper.findIntangibleAssets(params);
        for(Map<String,Object> assets:list){
            String assetPicture = MapUtils.getString(assets, "certificate");
            if (StringUtils.isBlank(assetPicture)) {
                assets.put("certificate", new String[]{});
            } else {
                String[] pictures = assetPicture.split(";");
                assets.put("certificate", pictures);
            }
            if(StringUtil.isNotEmpty(MapUtils.getString(assets,"assets_status"))){
                if(MapUtils.getString(assets,"assets_status").equals("1")){
                    assets.put("assets_status",true);
                }else{
                    assets.put("assets_status",false);
                }
            }else{
                assets.put("assets_status",false);
            }
        }
        return list;
     }
    @Override
     public void insertIntangibleAssets(Map<String,Object> params){
        perfectInformationMapper.deleteIntangibleAssets(params);
        List<Map<String,Object>> intangible_assets = (List<Map<String, Object>>) params.get("intangibleAssets");
        for (Map<String,Object> assets : intangible_assets) {
            String farm_family_id= MapUtils.getString(params,"farm_family_id");
            //通过ID来判断是否为种养户还是认证
            if(StringUtil.isNotEmpty(farm_family_id)){
                assets.put("farm_family_id",farm_family_id);
                assets.put("account","");

            }else{
                assets.put("farm_family_id",farm_family_id);
                assets.put("account",MapUtils.getString(params,"account"));
            }
            String certificate = MapUtils.getString(assets, "certificate");
            JSONArray picture = JSONArray.parseArray(certificate);
            if (picture.size() == 0) {
                assets.put("certificate", "");
            } else {
                String[] string = getJsonToStringArray(certificate);
                String asset = stringArrayMerge(string);
                assets.put("certificate", asset);
            }

            perfectInformationMapper.insertIntangibleAssets(assets);
        }
     }

    /**
     * 经营场所
     * @param params
     * @return
     */
    @Override
     public List<Map<String,Object>> findPlaceOfBusiness(Map<String,Object> params){
        List<Map<String,Object>> list  = perfectInformationMapper.findPlaceOfBusiness(params);
        for(Map<String,Object> assets:list){
            String assetPicture = MapUtils.getString(assets, "certificate");
            if (StringUtils.isBlank(assetPicture)) {
                assets.put("certificate", new String[]{});
            } else {
                String[] pictures = assetPicture.split(";");
                if(pictures.equals("")){
                    assets.put("certificate", assetPicture);
                }else{
                    assets.put("certificate", pictures);
                }

            }

            String contract = MapUtils.getString(assets, "contract");
            if (StringUtils.isBlank(contract)) {
                assets.put("contract", new String[]{});
            } else {
                String[] pictures = contract.split(";");
                assets.put("contract", pictures);

            }
            if(StringUtil.isNotEmpty(MapUtils.getString(assets,"assets_status"))){
                if(MapUtils.getString(assets,"assets_status").equals("1")){
                    assets.put("assets_status",true);
                }else{
                    assets.put("assets_status",false);
                }
            }else{
                assets.put("assets_status",false);
            }
        }
        return list;
     }
    @Override
     public void insertPlaceOfBusiness(Map<String,Object> params){
        perfectInformationMapper.deletePlaceOfBusiness(params);
        List<Map<String,Object>> place_of_business = (List<Map<String, Object>>) params.get("placeOfBusiness");
        for (Map<String,Object> assets : place_of_business) {
            String farm_family_id= MapUtils.getString(params,"farm_family_id");
            //通过ID来判断是否为种养户还是认证
            if(StringUtil.isNotEmpty(farm_family_id)){
                assets.put("farm_family_id",farm_family_id);
                assets.put("account","");

            }else{
                assets.put("farm_family_id",farm_family_id);
                assets.put("account",MapUtils.getString(params,"account"));

            }
            String certificate = MapUtils.getString(assets, "certificate");
            JSONArray picture = JSONArray.parseArray(certificate);
            if (picture.size() == 0) {
                assets.put("certificate", "");
            } else {
                String[] string = getJsonToStringArray(certificate);
                String asset = stringArrayMerge(string);
                assets.put("certificate", asset);
            }

            String contract = MapUtils.getString(assets, "contract");
            JSONArray contracts = JSONArray.parseArray(contract);
            if (contracts.size() == 0) {
                assets.put("contract", "");
            } else {
                String[] string = getJsonToStringArray(contract);
                String asset = stringArrayMerge(string);
                assets.put("contract", asset);
            }

            perfectInformationMapper.insertPlaceOfBusiness(assets);
        }
     }

    /**
     * 普通资产
     * @param params
     * @return
     */
    @Override
     public List<Map<String,Object>> findCommonAssets(Map<String,Object> params){
        List<Map<String,Object>> common_asset  =  perfectInformationMapper.findCommonAssets(params);
        if(common_asset.size()>0&&common_asset!=null){
            for(Map<String,Object> map:common_asset){
                if(StringUtil.isNotEmpty(MapUtils.getString(map,"common_status"))){
                    if(MapUtils.getString(map,"common_status").equals("1")){
                        map.put("common_status",true);
                    }else{
                        map.put("common_status",false);
                    }
                }else{
                    map.put("common_status",false);
                }

            }
        }
        return common_asset;
     }
    @Override
     public void insertCommonAssets(Map<String,Object> params){
         perfectInformationMapper.deleteCommonAssets(params);
         List<Map<String,Object>> common_assets = (List<Map<String, Object>>) params.get("commonAssets");
         for (Map<String,Object> assets : common_assets) {
            assets.put("account",MapUtils.getString(params,"account"));
            perfectInformationMapper.insertCommonAssets(assets);
         }
     }

     @Override
     public PageList findHonorInfo(Map<String,Object> params){
         Map<String,Object> info = new HashMap<>();
         info.put("account",MapUtils.getString(params,"account"));
         List<Map<String,Object>> honorMap = new ArrayList<>();
         //荣誉风采
         List<Map<String,Object>> honor_info = perfectInformationMapper.findHonor(info);
         for(Map<String,Object> honor:honor_info){
             //荣誉证书，可有多个
             String honors = MapUtils.getString(honor, "file");
             if (StringUtils.isNotBlank(honors)) {
                 String[] honorArray = honors.split(";");
                 for(int i=0;i<honorArray.length;i++){
                     Map<String,Object> map = new HashMap<>();
                     map.put("url",honorArray[i]);
                     honorMap.add(map);
                 }
             }
         }
         return PageList.pageInfoList(params,honorMap);

     }

    /**
     * 专业资质（门户）
     * @param account
     */
    @Override
    public PageList findQualification(Map<String,Object> params) {
        List<Map<String,Object>> professionMap = new ArrayList<>();
        List<Map<String,Object>> qualificationList = perfectInformationMapper.getQualificationByAccount(MapUtils.getString(params,"account"));
        if (qualificationList.size() > 0) {
            for (Map<String, Object> qualification : qualificationList) {
                //资质证书，可有多个
                String files = MapUtils.getString(qualification, "qualification_file");
                if (StringUtils.isNotBlank(files)) {
                    String[] pictures = files.split(";");
                    for(int i=0;i<pictures.length;i++){
                        Map<String,Object> map = new HashMap<>();
                        map.put("url",pictures[i]);
                        professionMap.add(map);
                    }
                }

            }
        }
        return PageList.pageInfoList(params,professionMap);
    }

    @Override
    public Map<String,Object> findLoginUrl(String account){
        return perfectInformationMapper.findLoginUser(account);
    }

    @Override
    public List<Map<String,Object>> findOverviewInfo(Map<String,Object> params){
        return perfectInformationMapper.findOverviewInfo(params);
    }
    @Override
    public void insertOverviewInfo(Map<String,Object> params){
        perfectInformationMapper.deleteOverviewInfo(params);
        List<Map<String,Object>> overviewInfo = (List<Map<String, Object>>) params.get("overViewInfo");
        Map<String,Object> map = overviewInfo.get(0);
        map.put("account",MapUtils.getString(params,"account"));
        perfectInformationMapper.insertOverviewInfo(map);
    }

    /**
     * 更新隐私信息
     * @param privacyInfo
     */
    @Override
    public void updatePrivacyInfo(Map<String, Object> privacyInfo) {
        perfectInformationMapper.updatePrivacyInfo(privacyInfo);
    }

    /**
     * 查询隐私信息，获取真实姓名和图像
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findPrivacyInfo(Map<String, Object> params) {

        return perfectInformationMapper.findPrivacyInfo(params);

    }

}
