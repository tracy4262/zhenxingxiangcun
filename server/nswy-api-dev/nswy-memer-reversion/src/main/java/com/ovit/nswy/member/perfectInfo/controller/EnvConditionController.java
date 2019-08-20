package com.ovit.nswy.member.perfectInfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.member.certification.service.AppSettingsService;
import com.ovit.nswy.member.perfectInfo.service.EnvConditionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.ConvertDataUtil;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 环境状况
 */
@RestController
@RequestMapping("/envCondition")
public class EnvConditionController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private EnvConditionService envConditionService;

    @Autowired
    private AppSettingsService appSettingsService;


   /**
     * 空气质量状况判断标准表
     * @return
     */
    @RequestMapping(value = "/airQuaStandard", method = RequestMethod.GET)
    public Result airQuaStandard() {
        Result result = new Result();
        try {
            List<Map<String, Object>> airQuaStandard = envConditionService.airQuaStandard();
            result.setMsg("空气质量状况判断标准表查询成功");
            result.setData(airQuaStandard);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 环境状态初始化查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/initData", method = RequestMethod.POST)
    public Result envInitData(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> subModule = perfectBasicService.findSubModule(params);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("subModule", subModule);
            //获取应用名称
            Map<String, Object> appMap = appSettingsService.findAppSetting(params);
            resultMap.put("moduleName", MapUtils.getString(appMap, "appName"));

            //默认查询空气质量信息的数据(因为子模块的数据是手工添加的，可控)
            //Map<String, Object> defaultMap = envConditionService.findAirQuaByAccount(params);
            //resultMap.put("defaultData", defaultMap);
            result.setMsg("环境状态初始化数据");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 空气质量信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAirQuality", method = RequestMethod.POST)
    public Result findAirQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> airQuality = envConditionService.findAirQuaByAccount(params);
            result.setMsg("空气质量信息查询成功");
            if (MapUtils.isEmpty(airQuality)) {
                airQuality = new HashMap<>();
            }
            result.setData(airQuality);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-空气质量信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAirQualityName",method = RequestMethod.POST)
    public Result updateAirQualityName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            envConditionService.updateAirQuaName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 空气质量信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/modifyAirQuality", method = RequestMethod.POST)
    public Result modifyAirQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> airQuality = envConditionService.findAirQuaByAccountList(params);
            if (MapUtils.isEmpty(airQuality)) {
                //不存在数据，新增
                envConditionService.insertAirQuaByAccount(params);
                //保存状态(0：待完善，1：已完成)到reversion_member_text_preview表中

                result.setMsg("空气质量信息新增成功");

            } else {
                //存在数据，修改
                envConditionService.modifyAirQuaByAccount(params);
                result.setMsg("空气质量信息修改成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地表水质量信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findSurfaceWaterQua", method = RequestMethod.POST)
    public Result findSurfaceWaterQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> surWaterQuality = envConditionService.findSurfaceWaterQua(params);
            if (MapUtils.isEmpty(surWaterQuality)) {
                surWaterQuality = new HashMap<>();
            }
            //得到选中的水质信息
            String waterQuality = MapUtils.getString(surWaterQuality, "waterQuality");
            String[] water = new String[0];
            if (StringUtils.isNotBlank(waterQuality)) {
                water = waterQuality.split(";");
            }
            List<Map<String, Object>> waterStandard = envConditionService.findWaterStandard();
            for (Map<String, Object> map : waterStandard) {
                map.put("_checked", false);
                String id = MapUtils.getString(map, "id");
                for (String s : water) {
                    if (id.equals(s)) {
                        map.put("_checked", true);
                        break;
                    }
                }
            }
            surWaterQuality.put("waterQuality", waterStandard);
            surWaterQuality.put("waterSelected", water);
            result.setMsg("地表水质量信息查询成功");
            result.setData(surWaterQuality);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 地表水质量信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/modifySurfaceWaterQua", method = RequestMethod.POST)
    public Result modifySurfaceWaterQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {

            Map<String, Object> surWaterQuality = envConditionService.findSurfaceWaterQuaList(params);
            if (MapUtils.isEmpty(surWaterQuality)) {
                //不存在数据，新增
                envConditionService.insertSurfaceWaterQua(params);
                result.setMsg("地表水质量信息新增成功");

            } else {
                //存在数据，修改
                envConditionService.modifySurfaceWaterQua(params);
                result.setMsg("地表水质量信息修改成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生态环境质量信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEcologyEnvQua", method = RequestMethod.POST)
    public Result findEcologyEnvQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> ecologyQuality = envConditionService.findEcologyEnvQua(params);

            if (MapUtils.isEmpty(ecologyQuality)) {
                ecologyQuality = new HashMap<>();
            }
            //得到选中的环境质量信息
            String envQuality = MapUtils.getString(ecologyQuality, "ecologyEnv");
            String[] env = new String[0];
            if (StringUtils.isNotBlank(envQuality)) {
                env = envQuality.split(";");
            }
            List<Map<String, Object>> envStandard = envConditionService.findEnvStandard();
            for (Map<String, Object> map : envStandard) {
                map.put("_checked", false);
                String id = MapUtils.getString(map, "id");
                for (String s : env) {
                    if (id.equals(s)) {
                        map.put("_checked", true);
                        break;
                    }
                }
            }
            ecologyQuality.put("ecologyEnv", envStandard);
            ecologyQuality.put("envSelected", env);

            result.setMsg("生态环境质量信息查询成功");
            result.setData(ecologyQuality);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生态环境质量信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/modifyEcologyEnvQua", method = RequestMethod.POST)
    public Result modifyEcologyEnvQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> ecologyQuality = envConditionService.findEcologyEnvQuaList(params);
            if (MapUtils.isEmpty(ecologyQuality)) {
                //不存在数据，新增
                envConditionService.insertEcologyEnvQua(params);
                result.setMsg("生态环境质量信息新增成功");

            } else {
                //存在数据，修改
                envConditionService.modifyEcologyEnvQua(params);
                result.setMsg("生态环境质量信息修改成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}
