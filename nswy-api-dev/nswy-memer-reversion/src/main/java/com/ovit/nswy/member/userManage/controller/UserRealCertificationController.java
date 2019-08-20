package com.ovit.nswy.member.userManage.controller;


import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.certification.service.RealCertificationService;
import com.ovit.nswy.member.perfectInfo.service.ExpertAuthService;
import com.ovit.nswy.member.userManage.service.UserRealCertificationService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 实名认证
 */
@RestController
@RequestMapping("/user/realCertification")
public class UserRealCertificationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserRealCertificationService userRealCertificationService;

    @Autowired
    private ProductionBaseService productionBaseService;

    @Autowired
    private ExpertAuthService expertAuthService;


    /**
     * 会员资质信息
     */

    //会员资质信息查询
    @RequestMapping(value = "/findMemberAptitude",method = RequestMethod.POST)
    public Result findMemberAptitude(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberAptitude(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //会员资质信息更新
    @RequestMapping(value = "/updateMemberAptitude",method = RequestMethod.POST)
    public Result updateMemberAptitude(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.updateMemberAptitude(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //会员资质信息保存
    @RequestMapping(value = "/saveMemberAptitude",method = RequestMethod.POST)
    public Result saveMemberAptitude(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.saveMemberAptitude(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //会员资质信息删除
    @RequestMapping(value = "/deleteMemberAptitude",method = RequestMethod.POST)
    public Result deleteMemberAptitude(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {

            String string  = userRealCertificationService.deleteMemberAptitude(params);
            result.setCode(Integer.valueOf(string));
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }


    /**
     * 联系方式
     *
     */

    //会员联系方式查询
    @RequestMapping(value = "/findMemberContact",method = RequestMethod.POST)
    public Result findMemberContact(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberContact(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findMemberContactOfByAptitude",method = RequestMethod.POST)
    public Result findMemberContactOfByAptitude(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberContactOfByAptitude(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    //会员联系方式更新
    @RequestMapping(value = "/updateMemberContact",method = RequestMethod.POST)
    public Result updateMemberContact(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.updateMemberContact(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //会员联系方式保存
    @RequestMapping(value = "/saveMemberContact",method = RequestMethod.POST)
    public Result saveMemberContact(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.saveMemberContact(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //会员联系方式删除
    @RequestMapping(value = "/deleteMemberContact",method = RequestMethod.POST)
    public Result deleteMemberContact(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.deleteMemberContact(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }


    /**
     * 法人/个人身份
     *
     */

    //法人/个人身份查询
    @RequestMapping(value = "/findMemberIdentity",method = RequestMethod.POST)
    public Result findMemberIdentity(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberIdentity(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //法人/个人身份更新
    @RequestMapping(value = "/updateMemberIdentity",method = RequestMethod.POST)
    public Result updateMemberIdentity(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.updateMemberIdentity(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //法人/个人身份保存
    @RequestMapping(value = "/saveMemberIdentity",method = RequestMethod.POST)
    public Result saveMemberIdentity(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.saveMemberIdentity(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //法人/个人身份删除
    @RequestMapping(value = "/deleteMemberIdentity",method = RequestMethod.POST)
    public Result deleteMemberIdentity(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            String string =  userRealCertificationService.deleteMemberIdentity(params);
            result.setCode(Integer.valueOf(string));
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }


    /**
     * 法人/个人身份（管理员）
     *
     */

    //法人/个人身份（管理员）查询
    @RequestMapping(value = "/findMemberAdministrator",method = RequestMethod.POST)
    public Result findMemberAdministrator(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberAdministrator(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //法人/个人身份（管理员）查询
    @RequestMapping(value = "/findMemberAdministratorOfByIdentity",method = RequestMethod.POST)
    public Result findMemberAdministratorOfByIdentity(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> map  =  userRealCertificationService.findMemberAdministratorOfByIdentity(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //法人/个人身份（管理员）更新
    @RequestMapping(value = "/updateMemberAdministrator",method = RequestMethod.POST)
    public Result updateMemberAdministrator(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.updateMemberAdministrator(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //法人/个人身份（管理员）保存
    @RequestMapping(value = "/saveMemberAdministrator",method = RequestMethod.POST)
    public Result saveMemberAdministrator(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.saveMemberAdministrator(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    //法人/个人身份（管理员）删除
    @RequestMapping(value = "/deleteMemberAdministrator",method = RequestMethod.POST)
    public Result deleteMemberAdministrator(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            userRealCertificationService.deleteMemberAdministrator(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }

    /**
     * 保存，下一步的时候判断是否填写完数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/findRealCertificationEnd",method = RequestMethod.POST)
    public Result findRealCertificationEnd(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            String code  = userRealCertificationService.findRealCertificationEnd(params);
            result.setCode(Integer.valueOf(code));
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;

    }


    /**
     * 查询资质认证信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMemberClassByAccount",method = RequestMethod.POST)
    public Result findMemberClassByAccount(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            Map<String,Object> templateMap = productionBaseService.getYearIdAndTemplateId(params);

            templateMap.put("user_id", MapUtils.getString(params,"account"));

            List<Map<String, Object>> list = userRealCertificationService.findMemberAptitude(templateMap);


            Map<String,Object> map = list.get(list.size()-1);

            Map<String, Object> expert = expertAuthService.findExpertInfo(templateMap);
            if(MapUtils.isNotEmpty(expert)){
                map.put("member_class","专家");
            }

            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




}
