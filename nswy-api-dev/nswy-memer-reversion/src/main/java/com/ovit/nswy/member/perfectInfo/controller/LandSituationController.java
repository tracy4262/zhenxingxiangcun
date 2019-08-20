package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.EcoSocialDevelopService;
import com.ovit.nswy.member.perfectInfo.service.LandSituationService;
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
 * 土地利用现状
 */
@RestController
@RequestMapping("/landUse")
public class LandSituationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LandSituationService landSituationService;

    /**
     * 土地利用现状信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> industry = landSituationService.find(params);
            result.setMsg("土地利用现状信息查询成功");
            result.setData(industry);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-土地利用现状信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            landSituationService.save(params);
            result.setMsg("土地利用现状信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-土地利用现状信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            landSituationService.delete(params);
            result.setMsg("土地利用现状信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 土地利用现状分类字典
     * @param params
     * @return
     */
    @RequestMapping(value = "/dict", method = RequestMethod.POST)
    public Result dict(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> dict = landSituationService.dict(params);
            result.setMsg("土地利用现状分类字典查询成功");
            result.setData(dict);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
