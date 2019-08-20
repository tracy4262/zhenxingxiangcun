package com.ovit.shop.plantInfo.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.shop.plantInfo.service.PlantInfoService;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plant")
public class PlantInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PlantInfoService plantInfoService;

    /**
     * 年度文件
     */
    @RequestMapping(value = "/findPlantYearInfo", method = RequestMethod.POST)
    public Result findPlantYearInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findPlantYearInfo(params);

             result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取年度文件列表信息失败");
        }
        return result;
    }



    /**
     * 通过id删除年度文件
     */
    @RequestMapping(value = "/deletePlantYearInfo", method = RequestMethod.POST)
    public Result deletePlantYearInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantYearInfo(params);
            result.setMsg("删除年度文件成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除年度文件失败");
        }
        return result;
    }


    /**
     * 新增年度文件
     */
    @RequestMapping(value = "/savePlantYearInfo", method = RequestMethod.POST)
    public Result savePlantYearInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.savePlantYearInfo(params);
            result.setMsg("新增年度文件成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增年度文件失败");
        }
        return result;
    }


    /**
     * 物种信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantSpeciesInfo", method = RequestMethod.POST)
    public Result findPlantSpeciesInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            PageInfo<Map<String, Object>> list = plantInfoService.findPlantSpeciesInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取物种信息列表信息失败");
        }
        return result;
    }

    /**
     * 新增物种信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/savePlantSpeciesInfo", method = RequestMethod.POST)
    public Result savePlantSpeciesInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String,Object>> list = (List<Map<String,Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                map.put("account", MapUtils.getString(params,"account"));
                map.put("yearId", MapUtils.getString(params,"yearId"));
                plantInfoService.savePlantSpeciesInfo(map);
            }

            result.setMsg("新增物种信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增物种信息失败");
        }
        return result;
    }

    /**
     * 删除物种
     * @param params
     * @return
     */
    @RequestMapping(value = "/deletePlantSpeciesInfo", method = RequestMethod.POST)
    public Result deletePlantSpeciesInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantSpeciesInfo(params);

            result.setMsg("删除物种信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除物种信息失败");
        }
        return result;
    }

    /**
     * 生产计划列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantProductionInfo", method = RequestMethod.POST)
    public Result findPlantProductionInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            PageInfo<Map<String, Object>> list = plantInfoService.findPlantProductionInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取生产计划列表信息失败");
        }
        return result;
    }

    /**
     * 生产序号下拉
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantProductionNumber", method = RequestMethod.POST)
    public Result findPlantProductionNumber(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findPlantProductionNumber(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取生产序号列表信息失败");
        }
        return result;
    }

    /**
     * 基地下拉
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProductionBase", method = RequestMethod.POST)
    public Result findProductionBase(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findProductionBase(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取生产计划列表信息失败");
        }
        return result;
    }



    /**
     * 新增生产计划
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdatePlantProduction", method = RequestMethod.POST)
    public Result saveOrUpdatePlantProduction(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.saveOrUpdatePlantProduction(params);

            result.setMsg("新增生产计划成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增生产计划信息失败");
        }
        return result;
    }

    /**
     * 删除生产计划
     * @param params
     * @return
     */
    @RequestMapping(value = "/deletePlantProductionInfo", method = RequestMethod.POST)
    public Result deletePlantProductionInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantProductionInfo(params);

            result.setMsg("删除生产计划信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增生产计划信息失败");
        }
        return result;
    }


    /**
     * 产量测算列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantOutputInfo", method = RequestMethod.POST)
    public Result findPlantOutputInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            PageInfo<Map<String, Object>> list = plantInfoService.findPlantOutputInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取产量测算列表信息失败");
        }
        return result;
    }

    /**
     * 新增/修改产量测算
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdatePlantOutputInfo", method = RequestMethod.POST)
    public Result saveOrUpdatePlantOutputInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.saveOrUpdatePlantOutputInfo(params);

            result.setMsg("新增产量测算成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增产量测算信息失败");
        }
        return result;
    }

    /**
     * 删除产量测算
     * @param params
     * @return
     */
    @RequestMapping(value = "/deletePlantOutputInfo", method = RequestMethod.POST)
    public Result deletePlantOutputInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantOutputInfo(params);

            result.setMsg("删除产量测算信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增产量测算信息失败");
        }
        return result;
    }

    /**
     * 生产序号下拉(产量测算)
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProductionBaseOfOutput", method = RequestMethod.POST)
    public Result findProductionBaseOfOutput(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findProductionBaseOfOutput(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取生产序号(产量测算)列表信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/findPlantTitleInfo", method = RequestMethod.POST)
    public Result findPlantTitleInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findPlantTitleInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取物种信息列表信息失败");
        }
        return result;
    }


    /**
     * 生产记录列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantProductionPlanInfo", method = RequestMethod.POST)
    public Result findPlantProductionPlanInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            PageInfo<Map<String, Object>> list = plantInfoService.findPlantProductionPlanInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取生产记录列表信息失败");
        }
        return result;
    }

    /**
     * 新增/修改生产记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateProductionPlanInfo", method = RequestMethod.POST)
    public Result saveOrUpdateProductionPlanInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.saveOrUpdateProductionPlanInfo(params);

            result.setMsg("新增生产记录成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增生产记录信息失败");
        }
        return result;
    }

    /**
     * 删除生产记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/deletePlantProductionPlanInfo", method = RequestMethod.POST)
    public Result deletePlantProductionPlanInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantProductionPlanInfo(params);

            result.setMsg("删除生产记录信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增生产记录信息失败");
        }
        return result;
    }

    /**
     * 收获（产品名称下拉）
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHarvestProductInfo", method = RequestMethod.POST)
    public Result findHarvestProductInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findHarvestProductInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取收获（产品名称下拉）列表信息失败");
        }
        return result;
    }

    /**
     * 自定义列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPlantCustomInfo", method = RequestMethod.POST)
    public Result findPlantCustomInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findPlantCustomInfo(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取自定义列表信息失败");
        }
        return result;
    }

    /**
     * 新增/修改自定义
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdatePlantCustomInfo", method = RequestMethod.POST)
    public Result saveOrUpdatePlantCustomInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.saveOrUpdatePlantCustomInfo(params);

            result.setMsg("新增自定义成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增自定义信息失败");
        }
        return result;
    }

    /**
     * 删除自定义
     * @param params
     * @return
     */
    @RequestMapping(value = "/deletePlantCustomInfo", method = RequestMethod.POST)
    public Result deletePlantCustomInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.deletePlantCustomInfo(params);

            result.setMsg("删除自定义信息成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增自定义信息失败");
        }
        return result;
    }

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAllTextPreviewList", method = RequestMethod.POST)
    public Result findAllTextPreviewList(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = plantInfoService.findAllTextPreviewList(params);

            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取全部列表信息失败");
        }
        return result;
    }

    /**
     * 全部文字预览
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateAllTextPreview", method = RequestMethod.POST)
    public Result saveOrUpdateAllTextPreview(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            plantInfoService.saveOrUpdateAllTextPreview(params);

            result.setMsg("新增全部文字预览成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("新增全部文字预览信息失败");
        }
        return result;
    }

}
