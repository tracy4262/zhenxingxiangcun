package com.ovit.nswy.member.application.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.application.service.MyRecommendService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  我的推荐
 */
@RestController
@RequestMapping("/myRecommend")
public class MyRecommendController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MyRecommendService myRecommendService;

    /**
     * 推荐服务列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/serviceList",method = RequestMethod.POST)
    public Result serviceList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = myRecommendService.serviceList(params);
            result.setMsg("推荐服务列表查询成功");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 推荐或取消推荐
     * @param params
     * @return
     */
    @RequestMapping(value = "/operation",method = RequestMethod.POST)
    public Result operation(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            myRecommendService.operation(params);
            result.setMsg("操作成功");
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 推荐基地列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/baseList",method = RequestMethod.POST)
    public Result baseList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = myRecommendService.baseList(params);
            result.setMsg("推荐基地列表查询成功");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 推荐专家列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/expertList",method = RequestMethod.POST)
    public Result expertList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<List<Map<String,Object>>> list = myRecommendService.expertList(params);
            result.setMsg("推荐专家列表查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 推荐产品列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/productList",method = RequestMethod.POST)
    public Result productList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = myRecommendService.productList(params);
            result.setMsg("推荐产品列表查询成功");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
