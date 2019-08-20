package com.ovit.nswy.portal.controller;

import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.MyGateService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的门户
 *
 * @author haoWen
 * @create 2018-01-12 11:41
 **/
@RestController
@RequestMapping("/myGate")
public class MyGateController {

    @Autowired
    private MyGateService myGateService;

    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 获取用户类型
     * 前端通过类型决定门户跳转
     * @param params
     * @return
     */
    @RequestMapping(value = "/getUserType", method = RequestMethod.POST)
    public Result getUserType(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            logger.debug("我的门户获取用户类型，参数：{}",params);
            result = new Result();
            Map<String,Object> website = myGateService.queryUserTypeByAccount(params);
            logger.info("我的门户获取用户类型成功：{}",website);
            result.setData(website);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("我的门户获取用户类型失败：{}",e);
        }
        return result;
    }
    /**
     * 获取门户的模块
     * @param params
     * @return
     */
    @RequestMapping(value = "/getModular", method = RequestMethod.POST)
    public Result getModular(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            logger.debug("我的门户获取模块名，参数：{}",params);
            result = new Result();
            Map<String,Object> website = myGateService.queryModularByAccount(params);
            logger.info("我的门户获取模块名成功：{}",website);
            result.setData(website);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("我的门户获取模块名失败：{}",e);
        }
        return result;
    }

    /**
     * 知识、政策、资讯标签
     * @param params
     * @return
     */
    @RequestMapping(value = "/getLabel", method = RequestMethod.POST)
    public Result getLabel(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("标签查询：{}",params);
            Map<String,Object> label = myGateService.queryLabel(params);
            if (MapUtils.isNotEmpty(label)) {
               String[] labelName = MapUtils.getString(label,"label").split(" ");
               label.put("label", Arrays.asList(labelName));
            } else {
                label = new HashMap<>();
                label.put("label",Arrays.asList("全部"));
            }
            logger.info("标签查询成功：{}",label);
            result.setData(label);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("标签查询失败：{}",e);
        }
        return result;
    }
}
