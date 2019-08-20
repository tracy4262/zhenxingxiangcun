package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.EmployeeRosterService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
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
 * 人口信息
 */
@RestController
@RequestMapping("/employeeRoster")
public class EmployeeRosterController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EmployeeRosterService employeeRosterService;




    /**
     *点击人口信息-员工花名册获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEmployeeRoster",method = RequestMethod.POST)
    public Result findEmployeeRoster(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = employeeRosterService.findEmployeeRoster(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-员工花名册
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateEmployeeRosterName",method = RequestMethod.POST)
    public Result updateEmployeeRosterName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.updateEmployeeRosterName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-员工花名册
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveEmployeeRoster",method = RequestMethod.POST)
    public Result saveEmployeeRoster(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.saveEmployeeRoster(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *点击人口信息-编外人员花名册获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findListOfExtraStaff",method = RequestMethod.POST)
    public Result findListOfExtraStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = employeeRosterService.findListOfExtraStaff(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-编外人员花名册
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateListOfExtraStaffName",method = RequestMethod.POST)
    public Result updateListOfExtraStaffName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.updateListOfExtraStaffName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-编外人员花名册
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveListOfExtraStaff",method = RequestMethod.POST)
    public Result saveListOfExtraStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.saveListOfExtraStaff(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteEmployeeRoster",method = RequestMethod.POST)
    public Result deleteEmployeeRoster(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.deleteEmployeeRoster(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/deleteListOfExtraStaff",method = RequestMethod.POST)
    public Result deleteListOfExtraStaff(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employeeRosterService.deleteListOfExtraStaff(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
