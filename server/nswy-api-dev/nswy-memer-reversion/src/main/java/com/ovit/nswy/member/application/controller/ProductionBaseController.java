package com.ovit.nswy.member.application.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.certification.service.AppSettingsService;
import com.ovit.nswy.member.perfectInfo.service.EnvConditionService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产基地管理
 */
@RestController
@RequestMapping("/productionBase")
public class ProductionBaseController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ProductionBaseService productionBaseService;

    @Autowired
    private AppSettingsService appSettingsService;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private EnvConditionService envConditionService;

    /**
     * 查找用户地块信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/landInfo",method = RequestMethod.POST)
    public Result landInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            if (StringUtils.isNotBlank(account)) {
                List<Map<String,Object>> landInfo = productionBaseService.landInfo(params);
                result.setMsg("地块信息查询成功");
                result.setData(landInfo);
            } else {
                result.setMsg("用户名为空");
                result.setCode(ResultCode.FAIL);
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 新增或编辑基地
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.saveOrUpdate(params);
            result.setMsg("基地信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除基地
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(@RequestParam Integer id) {
        Result result = new Result();
        try {
            if (id != null) {
                productionBaseService.delete(id);
                result.setMsg("基地信息删除成功");
            } else {
                result.setCode(ResultCode.FAIL);
                result.setMsg("未指定具体基地");
            }

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生产基地列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> list = productionBaseService.list(params);
            result.setMsg("生产基地列表查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 物联设施类别字典
     * @param params
     * @return
     */
    @RequestMapping(value = "/facilitiesType",method = RequestMethod.GET)
    public Result facilitiesType(){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = productionBaseService.facilitiesType();
            result.setMsg("物联设施类别查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 生产基地-查询基础信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBaseInfo",method = RequestMethod.POST)
    public Result findBaseInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> baseInfo = productionBaseService.findBaseInfo(params);
            result.setMsg("生产基地基础信息查询成功");
            result.setData(baseInfo);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生产基地-保存基本信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBaseInfo",method = RequestMethod.POST)
    public Result saveBaseInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.saveBaseInfo(params);
            result.setMsg("生产基地基本信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询生产基地详细信息的模块列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findModuleInfo", method = RequestMethod.POST)
    public Result findModuleInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> moduleList = productionBaseService.findModuleInfo(params);
            result.setData(moduleList);
            result.setMsg("生产基地应用模块信息查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("生产基地应用模块信息查询失败");
        }
        return result;
    }

    @RequestMapping(value = "/findTableHead", method = RequestMethod.POST)
    public Result findTableHead(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.getYearIdAndTemplateId(params);
            Map<String,Object> head = perfectBasicService.findPropertyInfo(params);
            if (MapUtils.isEmpty(head)) {
                head = new HashMap<>();
            }
            result.setData(head);
            result.setMsg("模块数据初始化");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生产基地详细信息模块初始化查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/initData", method = RequestMethod.POST)
    public Result initData(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> resultMap = new HashMap<>();
            List<Map<String,Object>> subModule = productionBaseService.findSubModule(params);
            resultMap.put("subModule", subModule);
            //获取应用名称
            Map<String, Object> appMap = appSettingsService.findAppSetting(params);
            resultMap.put("moduleName", MapUtils.getString(appMap, "appName"));
            result.setMsg("模块数据初始化");
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
    @RequestMapping(value = "/envCondition/findAirQuality", method = RequestMethod.POST)
    public Result findAirQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> airQuality = productionBaseService.findAirQuaByAccount(params);
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
     * 空气质量信息修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/envCondition/modifyAirQuality", method = RequestMethod.POST)
    public Result modifyAirQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> airQuality = productionBaseService.findAirQuaByAccountList(params);
            if (MapUtils.isEmpty(airQuality)) {
                //不存在数据，新增
                productionBaseService.insertAirQuaByAccount(params);
                result.setMsg("空气质量信息新增成功");

            } else {
                //存在数据，修改
                productionBaseService.modifyAirQuaByAccount(params);
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
    @RequestMapping(value = "/envCondition/findSurfaceWaterQua", method = RequestMethod.POST)
    public Result findSurfaceWaterQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> surWaterQuality = productionBaseService.findSurfaceWaterQua(params);
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
    @RequestMapping(value = "/envCondition/modifySurfaceWaterQua", method = RequestMethod.POST)
    public Result modifySurfaceWaterQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {

            Map<String, Object> surWaterQuality = productionBaseService.findSurfaceWaterQuaList(params);
            if (MapUtils.isEmpty(surWaterQuality)) {
                //不存在数据，新增
                productionBaseService.insertSurfaceWaterQua(params);
                result.setMsg("地表水质量信息新增成功");

            } else {
                //存在数据，修改
                productionBaseService.modifySurfaceWaterQua(params);
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
    @RequestMapping(value = "/envCondition/findEcologyEnvQua", method = RequestMethod.POST)
    public Result findEcologyEnvQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> ecologyQuality = productionBaseService.findEcologyEnvQua(params);

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
    @RequestMapping(value = "/envCondition/modifyEcologyEnvQua", method = RequestMethod.POST)
    public Result modifyEcologyEnvQua(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> ecologyQuality = productionBaseService.findEcologyEnvQuaList(params);
            if (MapUtils.isEmpty(ecologyQuality)) {
                //不存在数据，新增
                productionBaseService.insertEcologyEnvQua(params);
                result.setMsg("生态环境质量信息新增成功");

            } else {
                //存在数据，修改
                productionBaseService.modifyEcologyEnvQua(params);
                result.setMsg("生态环境质量信息修改成功");
            }
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
    @RequestMapping(value = "/landInfo/findLandInfo", method = RequestMethod.POST)
    public Result findLandInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = productionBaseService.findLandInfo(params);
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
    @RequestMapping(value = "/landInfo/updateLandInfo", method = RequestMethod.POST)
    public Result updateLandInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.updateLandInfo(params);
            result.setMsg("地块信息更新成功");
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
    @RequestMapping(value = "/landInfo/findSoilContent", method = RequestMethod.POST)
    public Result findSoilContent(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = productionBaseService.findSoilContent(params);
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
    @RequestMapping(value = "/landInfo/updateSoilContent", method = RequestMethod.POST)
    public Result updateSoilContent(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.updateSoilContent(params);
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
    @RequestMapping(value = "/landInfo/findSoilQuality", method = RequestMethod.POST)
    public Result findSoilQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = productionBaseService.findSoilQuality(params);
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
    @RequestMapping(value = "/landInfo/updateSoilQuality", method = RequestMethod.POST)
    public Result updateSoilQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.updateSoilQuality(params);
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
    @RequestMapping(value = "/landInfo/findWaterQuality", method = RequestMethod.POST)
    public Result findWaterQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> land = productionBaseService.findWaterQuality(params);
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
    @RequestMapping(value = "/landInfo/updateWaterQuality", method = RequestMethod.POST)
    public Result updateWaterQuality(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.updateWaterQuality(params);
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
    @RequestMapping(value = "/landInfo/findLandUse", method = RequestMethod.POST)
    public Result findLandUse(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> industry = productionBaseService.findLandUse(params);
            result.setMsg("土地利用现状信息查询成功");
            result.setData(industry);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-土地利用现状信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/landInfo/saveLandUse",method = RequestMethod.POST)
    public Result saveLandUse(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveLandUse(params);
            result.setMsg("土地利用现状信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-土地利用现状信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/landInfo/deleteLandUse",method = RequestMethod.POST)
    public Result deleteLandUse(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteLandUse(params);
            result.setMsg("土地利用现状信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/landInfo/getLandType",method = RequestMethod.POST)
    public Result getLandType(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> map = productionBaseService.getLandType(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新或保存文字预览信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/common/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveTextPreview(params);
            result.setMsg("更新或保存文字预览信息成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击地理位置信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findGeographicalPosition",method = RequestMethod.POST)
    public Result findGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findGeographicalPosition(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-地理位置信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveGeographicalPosition",method = RequestMethod.POST)
    public Result saveGeographicalPosition(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveGeographicalPosition(params);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/physicalGeography/deleteMemberNeighbor",method = RequestMethod.POST)
    public Result deleteMemberNeighbor(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteMemberNeighbor(params);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/physicalGeography/deleteMemberLiveAddress",method = RequestMethod.POST)
    public Result deleteMemberLiveAddress(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteMemberLiveAddress(params);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击国土面积信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findLandAreaInfo",method = RequestMethod.POST)
    public Result findLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findLandAreaInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-国土面积信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveLandAreaInfo",method = RequestMethod.POST)
    public Result saveLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveLandAreaInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/physicalGeography/deleteLandAreaInfo",method = RequestMethod.POST)
    public Result deleteLandAreaInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteLandAreaInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击地形地貌信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findTopographicFeature",method = RequestMethod.POST)
    public Result findTopographicFeature(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findTopographicFeature(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-地形地貌信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveTopographicFeature",method = RequestMethod.POST)
    public Result saveTopographicFeature(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveTopographicFeature(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击气候信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findClimateInfo",method = RequestMethod.POST)
    public Result findClimateInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findClimateInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-气候信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveClimateInfo",method = RequestMethod.POST)
    public Result saveClimateInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveClimateInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击生物资源信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findBiologicalResources",method = RequestMethod.POST)
    public Result findBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findBiologicalResources(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-生物资源信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveBiologicalResources",method = RequestMethod.POST)
    public Result saveBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveBiologicalResources(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/physicalGeography/deleteBiologicalResources",method = RequestMethod.POST)
    public Result deleteBiologicalResources(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteBiologicalResources(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击地质矿产信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/findMineralsInfo",method = RequestMethod.POST)
    public Result findMineralsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findMineralsInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-地质矿产信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/physicalGeography/saveMineralsInfo",method = RequestMethod.POST)
    public Result saveMineralsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveMineralsInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-通信设施及用户信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result findCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findCommunicationFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-通信设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result saveCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveCommunicationFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteCommunicationFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteCommunicationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteCommunicationFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-自来水设施及用户信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result findTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findTapWaternFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-自来水设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result saveTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveTapWaternFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteTapWaternFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteTapWaternFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteTapWaternFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-水利设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result findWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findWaterConservancyFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-水利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result saveWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveWaterConservancyFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteWaterConservancyFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteWaterConservancyFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteWaterConservancyFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-垃圾处理设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result findGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findGarbageDisposaFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 插入/更新-垃圾处理设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result saveGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveGarbageDisposaFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteGarbageDisposaFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteGarbageDisposaFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteGarbageDisposaFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-安保设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result findSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findSecurityFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-安保设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result saveSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveSecurityFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteSecurityFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteSecurityFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteSecurityFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-教育设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result findEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findEducationFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-教育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result saveEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveEducationFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteEducationFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteEducationFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteEducationFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-文化设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result findCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findCultureFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-文化设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result saveCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveCultureFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteCultureFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteCultureFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteCultureFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-体育设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result findSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findSportsFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-体育设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result saveSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveSportsFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteSportsFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteSportsFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteSportsFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-养老福利设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result findOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findOldAgeWelfareFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-养老福利设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result saveOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveOldAgeWelfareFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteOldAgeWelfareFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteOldAgeWelfareFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteOldAgeWelfareFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-其它便民设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result findOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findOtherFacilitiesInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-其它便民设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result saveOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveOtherFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteOtherFacilitiesInfo",method = RequestMethod.POST)
    public Result deleteOtherFacilitiesInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteOtherFacilitiesInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveIotDeviceInfo",method = RequestMethod.POST)
    public Result saveIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveIotDeviceInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-物联设施信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * account
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findIotDeviceInfo",method = RequestMethod.POST)
    public Result findIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findIotDeviceInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteIotDeviceInfo",method = RequestMethod.POST)
    public Result deleteIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteIotDeviceInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询文字预览全部
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAllTextPreviewList", method = RequestMethod.POST)
    public Result findAllTextPreviewList(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = productionBaseService.findAllTextPreviewList(params);
            result.setData(list);
            result.setMsg("全部文字预览查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("全部文字预览查询失败");
        }
        return result;
    }

    /**
     * 文字预览保存/修改成功
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAllTextPreview",method = RequestMethod.POST)
    public Result saveAllTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveAllTextPreview(params);
            result.setMsg("文字预览保存/修改成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 点击公共设施-交通设施及用户信息获取模块属性及相应的数据信息
     * parent_id 子类模块ID
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/findTrafficOfUserInfo",method = RequestMethod.POST)
    public Result findTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = productionBaseService.findTrafficOfUserInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-交通设施及用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/saveTrafficOfUserInfo",method = RequestMethod.POST)
    public Result saveTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveTrafficOfUserInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/communalFacilities/deleteTrafficOfUserInfo",method = RequestMethod.POST)
    public Result deleteTrafficOfUserInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteTrafficOfUserInfo(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 基地介绍
     * @param params
     * @return
     */
    @RequestMapping(value = "/baseIntroduction", method = RequestMethod.POST)
    public Result baseIntroduction(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> baseIntroduction = productionBaseService.baseIntroduction(params);
            result.setData(baseIntroduction);
            result.setMsg("基地介绍查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("基地介绍查询失败");
        }
        return result;
    }

    /**
     * 产业信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/findIndustry", method = RequestMethod.POST)
    public Result findIndustry(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> industry = productionBaseService.findIndustry(params);
            result.setMsg("产业信息查询成功");
            result.setData(industry);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-产业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/saveIndustry",method = RequestMethod.POST)
    public Result saveIndustry(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveIndustry(params);
            result.setMsg("产业信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-产业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/deleteIndustry",method = RequestMethod.POST)
    public Result deleteIndustry(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteIndustry(params);
            result.setMsg("产业信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 种养信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/findBreed", method = RequestMethod.POST)
    public Result findBreed(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> breed = productionBaseService.findBreed(params);
            result.setMsg("种养信息查询成功");
            result.setData(breed);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-种养信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/saveBreed",method = RequestMethod.POST)
    public Result saveBreed(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveBreed(params);
            result.setMsg("种养信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-种养信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/deleteBreed",method = RequestMethod.POST)
    public Result deleteBreed(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteBreed(params);
            result.setMsg("种养信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 农产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/findFarmProduct", method = RequestMethod.POST)
    public Result findFarmProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> farmProduct = productionBaseService.findFarmProduct(params);
            result.setMsg("农产品信息查询成功");
            result.setData(farmProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-农产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/saveFarmProduct",method = RequestMethod.POST)
    public Result saveFarmProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveFarmProduct(params);
            result.setMsg("农产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-农产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/deleteFarmProduct",method = RequestMethod.POST)
    public Result deleteFarmProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteFarmProduct(params);
            result.setMsg("农产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 工业产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/findTechnicalProduct", method = RequestMethod.POST)
    public Result findTechnicalProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> technicalProduct = productionBaseService.findTechnicalProduct(params);
            result.setMsg("工业产品信息查询成功");
            result.setData(technicalProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-工业产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/saveTechnicalProduct",method = RequestMethod.POST)
    public Result saveTechnicalProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveTechnicalProduct(params);
            result.setMsg("工业产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-工业产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/deleteTechnicalProduct",method = RequestMethod.POST)
    public Result deleteTechnicalProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteTechnicalProduct(params);
            result.setMsg("工业产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 服务产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/findServiceProduct", method = RequestMethod.POST)
    public Result findServiceProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> serviceProduct = productionBaseService.findServiceProduct(params);
            result.setMsg("服务产品信息查询成功");
            result.setData(serviceProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-服务产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/saveServiceProduct",method = RequestMethod.POST)
    public Result saveServiceProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveServiceProduct(params);
            result.setMsg("服务产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-服务产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/ecoSocial/deleteServiceProduct",method = RequestMethod.POST)
    public Result deleteServiceProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteServiceProduct(params);
            result.setMsg("服务产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 联系人列表：从实名认证里联系人信息中拉取
     * @param params
     * @return
     */
    @RequestMapping(value = "/contactList",method = RequestMethod.POST)
    public Result contactList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> contactList = productionBaseService.contactList(params);
            result.setMsg("生产基地拉取联系人信息列表查询成功");
            result.setData(contactList);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 物联设施列表：从生产基地详细信息里公共设施里的物联设施信息中拉取
     * @param params
     * @return
     */
    @RequestMapping(value = "/iotList",method = RequestMethod.POST)
    public Result iotList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> iotList = productionBaseService.iotList(params);
            result.setMsg("拉取物联设施信息成功");
            result.setData(iotList);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询生产基地第二步物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBaseIotDeviceInfo",method = RequestMethod.POST)
    public Result findBaseIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String, Object>> list = productionBaseService.findBaseIotDeviceInfo(params);
            result.setData(list);
            result.setMsg("第二步物联设施信息查询成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生产基地第二步物联设施信息保存
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBaseIotDeviceInfo",method = RequestMethod.POST)
    public Result saveBaseIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.saveBaseIotDeviceInfo(params);
            result.setMsg("第二步物联设施信息保存成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除第二步物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteBaseIotDeviceInfo",method = RequestMethod.POST)
    public Result deleteBaseIotDeviceInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            productionBaseService.deleteBaseIotDeviceInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 撤销第二步物联设施信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/revokeBaseIotDeviceInfo",method = RequestMethod.GET)
    public Result revokeBaseIotDeviceInfo(@RequestParam Integer id){
        Result result = new Result();
        try {
            Map<String,Object> map = new HashMap(){
                {
                    put("id", id);
                }
            };
            productionBaseService.revokeBaseIotDeviceInfo(map);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 生产基地-基地相册列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/photoList",method = RequestMethod.POST)
    public Result photoList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = productionBaseService.photoList(params);
            result.setMsg("生产基地相册列表查询成功");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 保存基地相册照片
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBasePhoto",method = RequestMethod.POST)
    public Result saveBasePhoto(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.saveBasePhoto(params);
            result.setMsg("基地相册照片保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 完成生产基地的编辑
     * @param params
     * @return
     */
    @RequestMapping(value = "/editComplete",method = RequestMethod.POST)
    public Result editComplete(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            productionBaseService.editComplete(params);
            result.setMsg("完成生产基地的编辑");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
