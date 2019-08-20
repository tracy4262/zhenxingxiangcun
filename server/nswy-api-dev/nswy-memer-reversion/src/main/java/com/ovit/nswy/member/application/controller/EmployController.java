package com.ovit.nswy.member.application.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.application.service.EmployService;
import com.ovit.nswy.member.perfectInfo.service.ExpertAuthService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聘请管理
 */
@RestController
@RequestMapping("/employ")
public class EmployController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EmployService employService;

    /**
     * 查找专家
     * @param params
     * @return
     */
    @RequestMapping(value = "/findExpert",method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> list = employService.findExpert(params);
            result.setMsg("专家信息查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 聘请管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/manage",method = RequestMethod.POST)
    public Result manage(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> list = employService.manage(params);
            result.setMsg("聘请信息查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 受聘管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/beEmployed",method = RequestMethod.POST)
    public Result beEmployed(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> list = employService.beEmployed(params);
            result.setMsg("受聘管理信息查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/orderDetail",method = RequestMethod.GET)
    public Result orderDetail(@RequestParam Integer id){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = new HashMap<>();
            if (id == null) {
                result.setData(resultMap);
                result.setCode(300);
                result.setMsg("无数据");
            } else {
                resultMap = employService.orderDetail(id);
                result.setData(resultMap);
                result.setMsg("查询订单详情成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 邀请专家
     * @param params
     * @return
     */
    @RequestMapping(value = "/invite",method = RequestMethod.POST)
    public Result invite(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employService.invite(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 取消邀请
     * @param params
     * @return
     */
    @RequestMapping(value = "/cacelInvite",method = RequestMethod.POST)
    public Result cacelInvite(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employService.cacelInvite(params);
            result.setMsg("取消邀请成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 解除关系
     * @param params
     * @return
     */
    @RequestMapping(value = "/relieve",method = RequestMethod.POST)
    public Result relieve(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employService.relieve(params);
            result.setMsg("取消邀请成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 受邀管理
     * @param params
     * @return
     */
    @RequestMapping(value = "/beInvited",method = RequestMethod.POST)
    public Result beInvited(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> list = employService.beInvited(params);
            result.setMsg("受邀管理信息查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 处理邀请
     * @param params
     * @return
     */
    @RequestMapping(value = "/dealInvite",method = RequestMethod.POST)
    public Result dealInvite(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            employService.dealInvite(params);
            result.setMsg("邀请处理成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
