package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.perfectInfo.service.AdministrationDivisionService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *行政区分
 */
@RestController
@RequestMapping("/administrationDivision")
public class AdministrationDivisionController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AdministrationDivisionService administrationDivisionService;


    @Autowired
    private ProductionBaseService productionBaseService;



    /**
     *点击行政区域-部门管理获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findDepartmentInfo",method = RequestMethod.POST)
    public Result findDepartmentInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = administrationDivisionService.findDepartmentInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *修改名称-部门管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateDepartmentInfoName",method = RequestMethod.POST)
    public Result updateDepartmentInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.updateDepartmentInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-部门管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveDepartmentInfo",method = RequestMethod.POST)
    public Result saveDepartmentInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.saveDepartmentInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    /**
     *点击行政区域-行政区分获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAdministrativeDivision",method = RequestMethod.POST)
    public Result findAdministrativeDivision(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = administrationDivisionService.findAdministrativeDivision(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *修改名称-行政区分
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAdministrativeDivisionName",method = RequestMethod.POST)
    public Result updateAdministrativeDivisionName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.updateAdministrativeDivisionName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-行政区分
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAdministrativeDivision",method = RequestMethod.POST)
    public Result saveAdministrativeDivision(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.saveAdministrativeDivision(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *点击行政区域-管理人员获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findManagerialStaff",method = RequestMethod.POST)
    public Result findManagerialStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = administrationDivisionService.findManagerialStaff(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *修改名称-管理人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateManagerialStaffName",method = RequestMethod.POST)
    public Result updateManagerialStaffName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.updateManagerialStaffName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-管理人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveManagerialStaff",method = RequestMethod.POST)
    public Result saveManagerialStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.saveManagerialStaff(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-文字预览
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.saveTextPreviews(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除-部门管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteDepartMentInfo",method = RequestMethod.POST)
    public Result deleteDepartMentInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.deleteDepartMentInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除-行政区域
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteAdministrativeDivision",method = RequestMethod.POST)
    public Result deleteAdministrativeDivision(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.deleteAdministrativeDivision(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除-管理人员
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteManagerialStaff",method = RequestMethod.POST)
    public Result deleteManagerialStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            administrationDivisionService.deleteManagerialStaff(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @RequestMapping(value = "/findRoster",method = RequestMethod.POST)
    public Result findRoster(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = administrationDivisionService.findRoster(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findManagerialStaffByAccount",method = RequestMethod.POST)
    public Result findManagerialStaffByAccount(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            //查询出templateid,yearId
            Map<String,Object> templateMap = productionBaseService.getYearIdAndTemplateId(params);

            Map<String,Object> map = new HashMap<>();
            map.put("user_id", MapUtils.getString(templateMap,"account"));

            map.put("year_id", MapUtils.getString(templateMap,"yearId"));

            map.put("templateId", MapUtils.getString(templateMap,"templateId"));

            List<Map<String,Object>> list = administrationDivisionService.findManagerialStaffByAccount(map);

            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}
