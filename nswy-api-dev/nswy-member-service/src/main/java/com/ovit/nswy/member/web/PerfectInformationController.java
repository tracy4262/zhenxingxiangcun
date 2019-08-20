package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.common.enums.CertificationStepEnum;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证第五步
 */
@RestController
@RequestMapping("/perfectInfo")
public class PerfectInformationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectInformationService perfectInformationService;

    @Autowired
    private LoginUserService loginUserService;

    @RequestMapping(value = "/findPerfectInfo", method = RequestMethod.POST)
    public Result findPerfectInfo(@RequestBody Map<String, Object> params) {

        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");

            Map<String,Object> loginUrl  = perfectInformationService.findLoginUrl(account);

            Map<String, Object> modulesInfo = perfectInformationService.getModulesByAccount(account);

            Map<String, Object> perfectInfo = new HashMap<>();
            perfectInfo.put("loginUrl",loginUrl);
            if ("1".equals(MapUtils.getString(modulesInfo, "privacy_information"))) {
                //隐私信息
                Map<String, Object> privateInformation = perfectInformationService.getPrivacyInfo(account);
                perfectInfo.put("privateInformation", privateInformation);
            }


            if ("1".equals(MapUtils.getString(modulesInfo, "network_information"))) {
                //网络信息
                Map<String, Object> networkInformation = perfectInformationService.getNetworkInfo(account);
                perfectInfo.put("networkInformation", networkInformation);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "product_serve"))) {
                //产品服务信息
                List<Map<String, Object>> productService = perfectInformationService.getProductService(account);
                perfectInfo.put("productService", productService);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "specialty_credentials"))) {
                //专业资质信息
                List<Map<String, Object>> qualification = perfectInformationService.getProfessionalQualification(account);
                perfectInfo.put("qualification", qualification);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "survey_operation"))) {
                //经营概况
                Map<String, Object> survey = perfectInformationService.getManageSurveyInfo(account);
                perfectInfo.put("survey", survey);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "specialty_information"))) {
                //专业信息
                Map<String, Object> professionalInformation = perfectInformationService.getProfessionInfo(account);
                perfectInfo.put("professionalInformation", professionalInformation);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "political_status"))) {
                //政治面貌
                Map<String, Object> policyInfo = perfectInformationService.getPolicyInfo(account);
                perfectInfo.put("policy", policyInfo);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "religion_faith"))) {
                //宗教信仰
                Map<String, Object> religionInfo = perfectInformationService.getReligionInfo(account);
                perfectInfo.put("religion", religionInfo);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "facility_assets"))) {
                //设施资产
                List<Map<String, Object>> facilityAssets = perfectInformationService.findFacilityAssets(params);
                perfectInfo.put("facilityAssets", facilityAssets);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "intangible_assets"))) {
                //无形资产
                List<Map<String, Object>> intangibleAssets = perfectInformationService.findIntangibleAssets(params);
                perfectInfo.put("intangibleAssets", intangibleAssets);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "place_of_business"))) {
                //经营场所
                List<Map<String, Object>> placeOfBusiness = perfectInformationService.findPlaceOfBusiness(params);
                perfectInfo.put("placeOfBusiness", placeOfBusiness);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "common_assets"))) {
                //普通资产
                List<Map<String, Object>> commonAssets = perfectInformationService.findCommonAssets(params);
                perfectInfo.put("commonAssets", commonAssets);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "overview_info"))) {
                //概览信息
                List<Map<String, Object>> overViewInfo = perfectInformationService.findOverviewInfo(params);
                perfectInfo.put("overViewInfo", overViewInfo);
            }

            //教育经历、工作经历、荣誉风采、房屋生活情况、家庭现代化情况、团队管理、财务信息
            params.put("modulesInfo", modulesInfo);
            Map<String,Object> moreInfo  = perfectInformationService.findPerfectInformation(params);

            if ("1".equals(MapUtils.getString(modulesInfo, "education_live"))) {
                perfectInfo.put("education", moreInfo.get("education"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "work_live"))) {
                perfectInfo.put("work", moreInfo.get("work"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "honour_style"))) {
                perfectInfo.put("corpHonor", moreInfo.get("honors"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "houses_condition"))) {
                perfectInfo.put("house", moreInfo.get("house"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "home_condition"))) {
                perfectInfo.put("modern", moreInfo.get("modern"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "management_team"))) {
                perfectInfo.put("team", moreInfo.get("team"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "finance_information"))) {
                perfectInfo.put("assetFinance", moreInfo.get("finance"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "family_Member"))) {
                perfectInfo.put("member", moreInfo.get("member"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "output_product"))) {
                perfectInfo.put("product", moreInfo.get("product"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "provide_services"))) {
                perfectInfo.put("provide", moreInfo.get("provide"));
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "purchase_intention"))) {
                perfectInfo.put("purchase", moreInfo.get("purchase"));
            }


            result.setMsg("查询完善信息成功");
            result.setData(perfectInfo);

        } catch (Exception e) {
            logger.error("查询完善信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询完善信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/savePerfectInfo", method = RequestMethod.POST)
    public Result savePerfectInfo(@RequestBody Map<String, Object> params) {

        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            //该用户在完善信息可填写的模块
            Map<String, Object> modulesInfo = perfectInformationService.getModulesByAccount(account);

            if ("1".equals(MapUtils.getString(modulesInfo, "privacy_information"))) {
                //隐私信息
                Map<String,Object> privateInformation = (Map<String, Object>) params.get("privateInformation");
                perfectInformationService.insertPrivacyInfo(account, privateInformation);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "network_information"))) {
                //网络信息
                Map<String,Object> networkInformation = (Map<String, Object>) params.get("networkInformation");
                perfectInformationService.insertNetworkInfo(account, networkInformation);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "product_serve"))) {
                //产品服务信息
                List<Map<String,Object>> productServiceList = (List<Map<String, Object>>) params.get("productService");
                perfectInformationService.insertProductService(account, productServiceList);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "specialty_credentials"))) {
                //专业资质信息
                List<Map<String,Object>> qualificationList = (List<Map<String, Object>>) params.get("qualification");
                perfectInformationService.insertProfessionalQualification(account, qualificationList);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "survey_operation"))) {
                //经营概况
                Map<String,Object> survey = (Map<String, Object>) params.get("survey");
                perfectInformationService.insertManageSurveyInfo(account, survey);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "specialty_information"))) {
                //专业信息
                Map<String,Object> professionalInformation = (Map<String, Object>) params.get("professionalInformation");
                perfectInformationService.insertProfessionInfo(account, professionalInformation);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "political_status"))) {
                //政治面貌
                Map<String,Object> policyInfo = (Map<String, Object>) params.get("policy");
                perfectInformationService.insertPolicyInfo(account, policyInfo);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "religion_faith"))) {
                //宗教信仰
                Map<String,Object> religionInfo = (Map<String, Object>) params.get("religion");
                perfectInformationService.insertReligionInfo(account, religionInfo);
            }
            if ("1".equals(MapUtils.getString(modulesInfo, "facility_assets"))) {
                //设施资产

                perfectInformationService.insertFacilityAssets(params);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "intangible_assets"))) {
                //无形资产

                perfectInformationService.insertIntangibleAssets(params);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "place_of_business"))) {
                //经营场所

                perfectInformationService.insertPlaceOfBusiness(params);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "common_assets"))) {
                //普通资产

                perfectInformationService.insertCommonAssets(params);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "overview_info"))) {
                //概览信息

                perfectInformationService.insertOverviewInfo(params);
            }

            if ("1".equals(MapUtils.getString(modulesInfo, "organization"))) {
                //更新组织机构

                perfectInformationService.updateDepartStatus(params);
            }


            params.put("modulesInfo", modulesInfo);
            //教育经历、工作经历、荣誉风采、房屋生活情况、家庭现代化情况、团队管理、财务信息
            perfectInformationService.insertPerfectInformation(params);

            //保存认证步骤到登陆表中
            LoginUser loginInfo = loginUserService.findByLoginName(account);
            int step = Integer.parseInt(loginInfo.getIsIdentityVerification());
            if (step < 5) {
                loginUserService.updateIdentityFlag(account, String.valueOf(CertificationStepEnum.STEP_PEFECTINFO.getStepNum()));
            }

            result.setMsg("保存完善信息成功");

        } catch (Exception e) {
            logger.error("保存完善信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存完善信息失败");
        }
        return result;
    }

    /**
     * 查询用户对应表单
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSysUserInfo", method = RequestMethod.POST)
    public Result findSysUserInfo(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            Map<String,Object> sysUserInfo = perfectInformationService.findSysUserInfo(params);
            result.setData(sysUserInfo);
        }catch (Exception e){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询用户对应表单失败：{}",e);
            return result;
        }

        return result;
    }

    /**
     *查询表单信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPerfectInformation", method = RequestMethod.POST)
    public Result findPerfectInformation(@RequestBody Map<String,Object> params){
        Result result = new Result();

        Map<String,Object> information  = perfectInformationService.findPerfectInformation(params);

        result.setData(information);
        return result;
    }


    /**
     *修改用户表单信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertPerfectInformation", method = RequestMethod.POST)
    public Result insertPerfectInformation(@RequestBody Map<String,Object> params){
        Result result = new Result();
        perfectInformationService.insertPerfectInformation(params);

        return result;
    }

    /**
     *查询组织机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/findDepartment", method = RequestMethod.POST)
    public Result findDepartment(@RequestBody Map<String,Object> params){
        Result result = new Result();

        List<Map<String,Object>> department  = perfectInformationService.findDepartment(params);

        result.setData(department);
        return result;
    }

    /**
     * 根据ID查询组织机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/findDepartById", method = RequestMethod.POST)
    public Result findDepartById(@RequestBody Map<String,Object> params){
        Result result = new Result();

        List<Map<String,Object>> departmentByIdList  = perfectInformationService.findDepartById(params);

        result.setData(departmentByIdList);
        return result;
    }

    /**
     * 保存组织机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertOrganization", method = RequestMethod.POST)
    public Result insertOrganization(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            perfectInformationService.insertOrganization(params);
        }catch (Exception e){
            logger.error("保存组织机构信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存组织机构信息失败");
        }
        return result;
    }


    /**
     * 更新组织机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
    public Result updateOrganization(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            perfectInformationService.updateOrganization(params);
        }catch (Exception e){
            logger.error("更新组织机构信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更新组织机构信息失败");
        }
        return result;
    }

    /**
     * 删除组织机构
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.POST)
    public Result deleteDepartment(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            perfectInformationService.deleteDepartment(params);
        }catch (Exception e){
            logger.error("删除组织机构信息失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除组织机构信息失败");
        }
        return result;
    }


    /**
     * 查询专家简介
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGateway", method = RequestMethod.POST)
    public Result findGateway(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            Map<String,Object> gateWay = perfectInformationService.findGateway(params);
            result.setData(gateWay);
        }catch (Exception e){
            logger.error("查询门户专家简介: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询门户专家简介信息失败");
        }
        return result;
    }

    /**
     * 查询工作单位
     * @param params
     * @return
     */
    @RequestMapping(value = "/findWorkTime", method = RequestMethod.POST)
    public Result findWorkTime(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            Map<String,Object> workTime = perfectInformationService.findWorkTime(params);
            result.setData(workTime);
        }catch (Exception e){
            logger.error("查询工作单位: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询工作单位信息失败");
        }
        return result;
    }

    /**
     * 查询荣誉风采
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHonorInfo", method = RequestMethod.POST)
    public Result findHonorInfo(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            PageList pageInfo = perfectInformationService.findHonorInfo(params);
            result.setData(pageInfo);
        }catch (Exception e){
            logger.error("查询荣誉风采: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询荣誉风采信息失败");
        }
        return result;


    }

    /**
     * 查询专业资质（门户）
     * @param params
     * @return
     */
    @RequestMapping(value = "/findQualification", method = RequestMethod.POST)
    public Result findQualification(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            PageList professionInfo = perfectInformationService.findQualification(params);
            result.setData(professionInfo);
        }catch (Exception e){
            logger.error("查询荣誉风采: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询荣誉风采信息失败");
        }
        return result;
    }



}
