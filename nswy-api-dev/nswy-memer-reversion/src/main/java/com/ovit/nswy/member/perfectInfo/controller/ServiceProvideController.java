package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.OutputProductService;
import com.ovit.nswy.member.perfectInfo.service.ServiceProvideService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 提供服务
 */
@RestController
@RequestMapping("/provideService")
public class ServiceProvideController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ServiceProvideService serviceProvideService;

    /**
     * 提供服务信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> output = serviceProvideService.find(params);
            result.setMsg("提供服务信息查询成功");
            result.setData(output);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-提供服务信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            serviceProvideService.save(params);
            result.setMsg("提供服务信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-提供服务信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            serviceProvideService.delete(params);
            result.setMsg("提供服务信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
