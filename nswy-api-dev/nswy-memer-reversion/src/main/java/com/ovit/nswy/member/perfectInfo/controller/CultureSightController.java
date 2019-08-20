package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.certification.service.AppSettingsService;
import com.ovit.nswy.member.perfectInfo.mapper.CultureSightMapper;
import com.ovit.nswy.member.perfectInfo.service.CultureSightService;
import com.ovit.nswy.member.perfectInfo.service.EnvConditionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
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
 * 文化景观
 */
@RestController
@RequestMapping("/cultureSight")
public class CultureSightController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private CultureSightService cultureSightService;

    @Autowired
    private AppSettingsService appSettingsService;

    /**
     * 文化景观初始化查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/initData", method = RequestMethod.POST)
    public Result cultureInitData(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            //默认查询风景名胜设施信息的数据(因为子模块的数据是手工添加的，可控)
            Map<String, Object> defaultMap = cultureSightService.findSceneryByAccount(params);
            //获取应用名称
            Map<String, Object> appMap = appSettingsService.findAppSetting(params);
            defaultMap.put("moduleName", MapUtils.getString(appMap, "appName"));
            result.setMsg("文化景观初始化数据");
            result.setData(defaultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 风景名胜设施信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFamousScenery", method = RequestMethod.POST)
    public Result findFamousScenery(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> famousScenery = cultureSightService.findFamousScenery(params);
            result.setMsg("风景名胜设施信息查询成功");
            result.setData(famousScenery);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改属性名称
     * @param params
     * @return
     */
    @RequestMapping(value = "/updatePropertyInfo",method = RequestMethod.POST)
    public Result updateSceneryName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.updatePropertyInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-风景名胜设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveScenery",method = RequestMethod.POST)
    public Result saveScenery(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.saveScenery(params);
            result.setMsg("风景名胜设施信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 文化遗产设施信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findCulturalHeritage", method = RequestMethod.POST)
    public Result findCulturalHeritage(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> culturalHeritage = cultureSightService.findCulturalHeritage(params);
            result.setMsg("文化遗产设施信息查询成功");
            result.setData(culturalHeritage);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-文化遗产设施信息
     * @param params
     * @return
     */
    /*@RequestMapping(value = "/updateCulturalName",method = RequestMethod.POST)
    public Result updateCulturalName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.updateCulturalName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }*/

    /**
     *插入/更新-文化遗产设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCultural",method = RequestMethod.POST)
    public Result saveCulturalName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.saveCultural(params);
            result.setMsg("文化遗产设施信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新或保存文字预览信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.saveTextPreview(params);
            result.setMsg("更新或保存文字预览信息成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-风景名胜设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteScenery",method = RequestMethod.POST)
    public Result deleteScenery(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.deleteScenery(params);
            result.setMsg("风景名胜设施信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-文化遗产设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteCultural",method = RequestMethod.POST)
    public Result deleteCultural(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            cultureSightService.deleteCultural(params);
            result.setMsg("文化遗产设施信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
