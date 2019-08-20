package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.LandInformationService;
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
 * 地块信息
 */
@RestController
@RequestMapping("/landInfo")
public class LandInformationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LandInformationService landInformationService;

    /**
     * 更新农科院地块数据到平台
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateNkyLand", method = RequestMethod.POST)
    public Result updateNkyLand(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.updateNkyLand(params);
            result.setMsg("地块数据成功更新到系统");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地块信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLandInfo", method = RequestMethod.POST)
    public Result findLandInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = landInformationService.findLandInfo(params);
            result.setMsg("地块信息查询成功");
            result.setData(land);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新地块信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateLandInfo", method = RequestMethod.POST)
    public Result updateLandInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.updateLandInfo(params);
            result.setMsg("地块信息更新成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除地块信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteLandInfo", method = RequestMethod.POST)
    public Result deleteLandInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.deleteLandInfo(params);
            result.setMsg("地块信息删除成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地块土壤含量信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSoilContent", method = RequestMethod.POST)
    public Result findSoilContent(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = landInformationService.findSoilContent(params);
            result.setMsg("地块土壤含量信息查询成功");
            result.setData(land);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新地块土壤含量信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSoilContent", method = RequestMethod.POST)
    public Result updateSoilContent(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.updateSoilContent(params);
            result.setMsg("地块土壤含量信息更新成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地块土壤质量信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSoilQuality", method = RequestMethod.POST)
    public Result findSoilQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = landInformationService.findSoilQuality(params);
            result.setMsg("地块土壤质量信息查询成功");
            result.setData(land);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新地块土壤质量信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSoilQuality", method = RequestMethod.POST)
    public Result updateSoilQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.updateSoilQuality(params);
            result.setMsg("地块土壤质量信息更新成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地块水质信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findWaterQuality", method = RequestMethod.POST)
    public Result findWaterQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = landInformationService.findWaterQuality(params);
            result.setMsg("地块水质信息查询成功");
            result.setData(land);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新地块水质信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateWaterQuality", method = RequestMethod.POST)
    public Result updateWaterQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            landInformationService.updateWaterQuality(params);
            result.setMsg("地块水质信息更新成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 土地利用现状信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findLandUse", method = RequestMethod.POST)
    public Result findLandUse(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> industry = landInformationService.findLandUse(params);
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
    @RequestMapping(value = "/saveLandUse",method = RequestMethod.POST)
    public Result saveLandUse(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            landInformationService.saveLandUse(params);
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
    @RequestMapping(value = "/deleteLandUse",method = RequestMethod.POST)
    public Result deleteLandUse(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            landInformationService.deleteLandUse(params);
            result.setMsg("土地利用现状信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
