package com.ovit.nswy.member.web.product.base;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.member.service.*;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.xml.crypto.Data;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 土地利用现状
 *
 * @author haoWen
 * @create 2017-12-26 09:28
 **/
@RestController
@RequestMapping("/product-land-use-quo")
public class LandUseQuoController {
    private Logger logger = LogManager.getLogger(this.getClass());
    //全局的错误码
    private static final int ERROR_CODE = 500;

    //地块信息
    @Autowired
    private LandUseQuoService landUseQuoService;

    //土壤营养化学指标
    @Autowired
    private LandNutritiveService landNutritiveService;

    //土壤物理性状指标
    @Autowired
    private LandPhysicalService landPhysicalService;

    //土壤生物学指标
    @Autowired
    private LandBiologicalService landBiologicalService;

    //土壤环境指标信息
    @Autowired
    private LandEnvironmentalService landEnvironmentalService;

    //土壤微量元素指标信息
    @Autowired
    private LandSoilMicroelementService landSoilMicroelementService;

    //地块水质信息
    @Autowired
    private LandWaterQualityService landWaterQualityService;
    //水质检测报告上传
    @Autowired
    private LandWaterQualityReportService landWaterQualityReportService;

    //上传状态--未上传
    private static final String STATUS_N = "N";
    //上传状态--已上传
    private static final String STATUS_Y = "Y";
    /**
     * 新增地块信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/plot-save",method = RequestMethod.POST )
    public Result saveLandPlot(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("新增地块信息，参数：{}",params);
            result = new Result();
            Map<String,Object> map = MapUtils.getMap(params,"data");
            String landId = MapUtils.getString(map,"landId");
            logger.info("landId:{}",landId);
            if (StringUtils.isEmpty(landId)) {
                landId = UUID.randomUUID().toString();
                map.put("landId",landId);
            }
            map.put("productId",params.get("productId"));
            if (StringUtils.isNotEmpty(MapUtils.getString(map,"longitudeLatitude"))){
                map.put("mapSign",STATUS_Y);
            } else {
                map.put("mapSign",STATUS_N);
            }

            String classes = MapUtils.getString(map,"landUseClasses");
            if (classes.indexOf("-") != -1) {
                String[] landUseClasses = classes.split("-");
                map.put("landUsed",landUseClasses[1]);
                map.put("landType",landUseClasses[0]);
            } else {
                map.put("landType",classes);
                map.put("landUsed","");
            }
            landUseQuoService.saveLandPlot(map);
            result.setData(map);
            logger.info("新增地块信息成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("新增地块信息失败：{}",e);
        }
        return result;
    }


    /**
     * 删除地块信息
     * @param params
     * @return
     */
    //FIXME 删除地块信息时同时删除关联表
    @RequestMapping(value = "/plot-delete",method = RequestMethod.POST )
    public Result deleteLandPlot(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("删除地块信息，参数：{}",params);
            result = new Result();
            landUseQuoService.deleteLandPlot(params);
            logger.info("删除地块信息成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("删除地块信息失败：{}",e);
        }
        return result;
    }


    /**
     * 查询地块信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/plot-query",method = RequestMethod.POST )
    public Result queryLandPlot(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("查询地块信息，参数：{}",params);
            result = new Result();
            List<Map<String,Object>> landList = landUseQuoService.queryLandPlot(params);
            if (CollectionUtils.isNotEmpty(landList)){
                Map<String,Object> map = countArea(initLandPlotInfo(landList));
                result.setData(map);
                logger.info("组装的地块信息：{}",map);
            }
            logger.info("查询地块信息成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("查询地块信息失败：{}",e);
        }
        return result;
    }


    /**
     * 按照土地用途分类，将相同用途的数据放在一个list中
     * @param landList
     * @return
     */
    private Map<String,List<Map<String,Object>>> initLandPlotInfo(List<Map<String,Object>> landList){
        Map<String,List<Map<String,Object>>> mapAll = new HashMap<>();
       for (Map<String,Object> map :landList){
           Map<String,Object> landNutritiveMap =  landNutritiveService.queryIsExit(map);
           Map<String,Object> landPhysicalMap =  landPhysicalService.queryIsExit(map);
           Map<String,Object> landBiologicalMap = landBiologicalService.queryIsExit(map);
           Map<String,Object> landEnvironmentalMap = landEnvironmentalService.queryIsExit(map);
           Map<String,Object> landSoilMicroelementMap = landSoilMicroelementService.queryIsExit(map);
            if (checkeMapIsEmpty(landNutritiveMap) &&
                    checkeMapIsEmpty(landPhysicalMap) &&
                    checkeMapIsEmpty(landBiologicalMap) &&
                    checkeMapIsEmpty(landEnvironmentalMap) &&
                    checkeMapIsEmpty(landSoilMicroelementMap)) {
                map.put("soilInfo","未上传");
            } else {
                map.put("soilInfo","已上传");
            }
           //以土地用途为主键分类
           String key = MapUtils.getString(map,"landUsed");
           if (mapAll.containsKey(key)){
               mapAll.get(key).add(map);
           } else {
               List<Map<String,Object>> landTypeList = new ArrayList<>();
               landTypeList.add(map);
               mapAll.put(key,landTypeList);
           }
           //logger.debug("按照土地用途分类：{}",mapAll);
       }
        return mapAll;
    }

    /**
     * 计算每种地块用途的面积和总数量
     *
     * @param params
     * @return
     */
    private Map<String, Object> countArea(Map<String,List<Map<String,Object>>> params){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //数据结构为Map<String,List<Map<String,List<Map<String,Object>>>>>
        //1.第一层MAP为描述以及地块汇总信息
        Map<String,Object> arrayMap = new HashMap<>();
        //2.第二层每一种类型的总面积，总数量以及地块信息
        List<Object> list = new ArrayList<>();
        //2.第三层MAP为地块用途分类：每一种类型的总面积，总数量以及地块信息
        //3.第四层LIST为按用途分类的地块list
        //List<Object>landList = new ArrayList<>();
        //4.第五层MAP为每一个地块信息的数据
        //Map<String,Object> land = new HashMap<>();
        StringBuffer describe = new StringBuffer("其中：");
        StringBuffer AllDescribe = new StringBuffer("");
        //总面积
        double totalArea = 0;
        //总数量
        int totalCount = 0;
        //每种用途的key
        int index = 0;
        //计算每一种使用用途的总面积、并显示其平方米、亩、公顷三种数值，保留2位小数
        for (Map.Entry<String,List<Map<String,Object>>> listEntry : params.entrySet()){
            Map<String,Object> landUsedMap = new HashMap<>();
            String mapKey = "landUseClasses" + index;
            String key = listEntry.getKey();
            List<Map<String,Object>> listMap = listEntry.getValue();
            //每种地块使用类型面积
            double landUsedTotalArea = 0;
            //遍历每一种使用用途的每一块地块的面积，并将亩、公顷换算成平方米
            for (Map<String,Object> map : listMap) {
                double landArea = Double.parseDouble(MapUtils.getString(map,"landArea"));
                String unitArea = MapUtils.getString(map,"unitArea");
                //logger.info("面积：{}{}",landArea,unitArea);
                if ("公顷".equals(unitArea)) {
                    landArea = landArea * 10000;
                }else if ("亩".equals(unitArea)) {
                    landArea = (landArea / 3 ) * 2000;
                   // landArea = area.substring(0,area.indexOf("."));
                }
                //logger.info("面积换算成平方米：{}",landArea);
                //统计每一种用途的地块面积
                landUsedTotalArea += landArea;
            }
            //统计所有的地块面积
            totalArea += landUsedTotalArea;
            //统计地块总数量
            totalCount += listMap.size();
            describe.append(key).append(listMap.size()).append("块，面积").append(landUsedTotalArea).append("平方米；");
            landUsedMap.put(mapKey ,listMap);
            landUsedMap.put("AllLandUsed",key);
            landUsedMap.put("landUsedTotalArea",String.format("%.2f",Double.parseDouble(decimalFormat.format(landUsedTotalArea))));
            landUsedMap.put("landUsedCount",listMap.size());
            list.add(landUsedMap);
            index++;
        }
        //平方米换算为亩
        double totalAreaMu = (totalArea + (totalArea / 2)) / 1000;
        //平方米换算公顷
        double totalAreaGongQin = totalArea / 10000;
        //用一句话描述所有土地的利用现状，面积等等
        AllDescribe.append("总占地面积")
                   .append(String.format("%.0f",totalArea))
                   .append("平方米，合：")
                   .append(String.format("%.2f",Double.parseDouble(decimalFormat.format(totalAreaMu))))
                   .append("亩，")
                   .append(String.format("%.2f",Double.parseDouble(decimalFormat.format(totalAreaGongQin))))
                   .append("公顷。")
                   .append("地块数：")
                   .append(totalCount)
                   .append("块")
                   .append(String.valueOf(describe).substring(0,describe.length()-1));
        arrayMap.put("list",list);
        arrayMap.put("describe",AllDescribe);
        return arrayMap;
    }


    /**
     * 土壤信息
     * 土壤营养（化学）指标
     * saveOrUpdate
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-nutritive-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandInfoNutritive(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤营养化学指标新增/修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landNutritiveService.saveOrUpdateLandInfoNutritive(map);
            logger.info("土壤信息-土壤营养化学指标新增/修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤营养化学指标新增/修改失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤营养（化学）指标查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-nutritive-query",method = RequestMethod.POST)
    public Result queryLandInfoNutritive(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤营养化学指标查询，参数：{}",params);
            result = new Result();
            Map<String,Object> landNutritiveMap = landNutritiveService.queryLandInfoNutritive(params);
            result.setData(landNutritiveMap);
            logger.info("土壤信息-土壤营养化学指标查询成功：{}",landNutritiveMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤营养化学指标查询失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤物理性状指标新增
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-physical-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandInfoPhysical(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤物理性状指标新增/修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landPhysicalService.saveOrUpdateLandInfoPhysical(map);
            logger.info("土壤信息-土壤物理性状指标新增/修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤物理性状指标新增/修改失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤物理性状指标查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-physical-query",method = RequestMethod.POST)
    public Result queryLandInfoPhysical(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤物理性状指标查询，参数：{}",params);
            result = new Result();
            Map<String,Object> landPhysicalMap = landPhysicalService.queryLandInfoPhysical(params);
            result.setData(landPhysicalMap);
            logger.info("土壤信息-土壤物理性状指标查询成功：{}",landPhysicalMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤物理性状指标查询失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤生物学指标新增
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-biological-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandInfoBiological(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤生物学指标新增/修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landBiologicalService.saveOrUpdateLandInfoBiological(map);
            logger.info("土壤信息-土壤生物学指标新增/修改成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤生物学指标新增/修改失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤生物学指标查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-biological-query",method = RequestMethod.POST)
    public Result queryLandInfoBiological(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤生物学指标新增/修改，参数：{}",params);
            result = new Result();
            Map<String,Object> landBiologicalMap = landBiologicalService.queryLandInfoBiological(params);
            result.setData(landBiologicalMap);
            logger.info("土壤信息-土壤生物学指标查询成功：{}",landBiologicalMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤生物学指标查询失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤环境指标新增/修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-environmental-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandInfoEnvironmental(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤环境指标新增/修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landEnvironmentalService.saveOrUpdateLandInfoEnvironmental(map);
            logger.info("土壤信息-土壤环境指标新增/修改成功");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤环境指标新增/修改失败：{}",e);
        }
        return result;
    }
    /**
     * 土壤信息
     * 土壤环境指标查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-environmental-query",method = RequestMethod.POST)
    public Result queryLandInfoEnvironmental(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤环境指标查询，参数：{}",params);
            result = new Result();
            Map<String,Object> landEnvironmentalMap = landEnvironmentalService.queryLandInfoEnvironmental(params);
            result.setData(landEnvironmentalMap);
            logger.info("土壤信息-土壤环境指标查询成功：{}",landEnvironmentalMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤环境指标查询失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤微量元素新增/修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-microelements-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandInfoMicroelements(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤微量元素新增/修改，参数：{}",params);
            result = new Result();
            Map<String, Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landSoilMicroelementService.saveOrUpdateLandInfoMicroelements(map);
            logger.info("土壤信息-土壤微量元素新增/修改成功");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-土壤微量元素新增/修改失败：{}",e);
        }
        return result;
    }

    /**
     * 土壤信息
     * 土壤微量元素查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-microelements-query",method = RequestMethod.POST)
    public Result queryLandInfoMicroelements(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            logger.debug("土壤信息-土壤微量元素查询，参数：{}",params);
            result = new Result();
            Map<String,Object> landSoilMicroelementlMap = landSoilMicroelementService.queryLandInfoMicroelements(params);
            result.setData(landSoilMicroelementlMap);
            logger.info("土壤信息-土壤微量元素查询成功：{}",landSoilMicroelementlMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("土壤信息-ccv：{}",e);
        }
        return result;
    }

    /**water-quality/save
     * 地块水质信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-water-quality-save",method = RequestMethod.POST)
    public Result saveOrUpdateLandWaterQuality(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("地块水质信息新增/修改，参数：{}",params);
            Map<String, Object> map = MapUtils.getMap(params,"data");
            String landId = MapUtils.getString(params,"landId");
            map.put("landId",landId);
            landWaterQualityService.saveOrUpdateLandWaterQuality(map);
            //新增水质信息的时候修改地块信息的水质信息上传状态
            if (StringUtils.isAllBlank(
                    MapUtils.getString(map,"waterPH"),
                    MapUtils.getString(map,"cyanide"),
                    MapUtils.getString(map,"fluoride"),
                    MapUtils.getString(map,"chloride"),
                    MapUtils.getString(map,"arsenic"),
                    MapUtils.getString(map,"mercury"),
                    MapUtils.getString(map,"lead"),
                    MapUtils.getString(map,"cadmium"),
                    MapUtils.getString(map,"chromium"),
                    MapUtils.getString(map,"fecalColiform")
            ) && JSON.parseArray(MapUtils.getString(map,"upList")).size() == 0) {
                map.put("waterQualityInfo",STATUS_N);
            } else {
                map.put("waterQualityInfo",STATUS_Y);
            }
            landUseQuoService.updateWaterQualityInfo(map);
            logger.info("地块水质信息新增/修改成功！");
            List<Map<String,Object>> reportMap = landWaterQualityReportService.query(map);
            if (CollectionUtils.isNotEmpty(reportMap)) {
                landWaterQualityReportService.delete(map);
                logger.info("删除水质质量检测报告：{}份",reportMap.size());
            }
            List uploadReportList = JSON.parseArray(MapUtils.getString(map,"upList"));
            if (CollectionUtils.isNotEmpty(uploadReportList)) {
                for (int i = 0; i < uploadReportList.size(); i++) {
                    Map<String,Object> urlMap = (Map<String, Object>) uploadReportList.get(i);
                    urlMap.put("landId",landId);
                    landWaterQualityReportService.uploadReport(urlMap);
                }
                logger.info("上传水质质量检测报告：{}份",uploadReportList.size());
            }
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("地块水质信息新增/修改失败：{}",e);
        }
        return result;

    }

    /**
     * 水质信息查询
     * @param params
     * @return
     */

    @RequestMapping(value = "/land-info-water-quality-query",method = RequestMethod.POST)
    public Result queryLandWaterQuality(@RequestBody Map<String, Object> params){
        logger.debug("地块水质信息查询，参数：{}",params);
        Result result = new Result();
        Map<String,Object> map = landWaterQualityService.queryLandWaterQuality(params);
        //FIXME 水质信息检测报告
        return result;
    }

    /**
     * 水质信息上传检测报告
     * @param params
     * @return
     */
    @RequestMapping(value = "/land-info-water-quality-upload-report",method = RequestMethod.POST)
    public Result uploadReport(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("水质信息上传检测报告：{}",params);
            Map<String,Object> map = MapUtils.getMap(params,"data");
            map.put("landId",params.get("landId"));
            landWaterQualityReportService.uploadReport(map);
            logger.info("水质信息上传检测报告成功！");
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("水质信息上传检测报告失败：{}",e);
        }
        return result;
    }

    /**
     * 查询土壤信息、水质信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/query-all",method = RequestMethod.POST)
    public Result queryAll(@RequestBody Map<String, Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询土壤信息、水质信息:{}",params);
            Map<String,Object> allMap = new HashMap<>();
            //土壤营养（化学）指标查询
            Map<String,Object> landNutritiveMap = landNutritiveService.queryLandInfoNutritive(params);
            //土壤物理性状指标查询
            Map<String,Object> landPhysicalMap = landPhysicalService.queryLandInfoPhysical(params);
            //土壤生物学指标查询
            Map<String,Object> landBiologicalMap = landBiologicalService.queryLandInfoBiological(params);
            //土壤环境指标查询
            Map<String,Object> landEnvironmentalMap = landEnvironmentalService.queryLandInfoEnvironmental(params);
            //土壤微量元素查询
            Map<String,Object> landSoilMicroelementlMap = landSoilMicroelementService.queryLandInfoMicroelements(params);
            //水质信息查询
            Map<String,Object> landWaterQualityMap = landWaterQualityService.queryLandWaterQuality(params);
            //水质检测报告
            List<Map<String,Object>> reportMap = landWaterQualityReportService.query(params);

            allMap.put("landNutritiveMap",landNutritiveMap);
            allMap.put("landPhysicalMap",landPhysicalMap);
            allMap.put("landBiologicalMap",landBiologicalMap);
            allMap.put("landEnvironmentalMap",landEnvironmentalMap);
            allMap.put("landSoilMicroelementlMap",landSoilMicroelementlMap);
            allMap.put("landWaterQualityMap",landWaterQualityMap);
            allMap.put("reportMap",reportMap);
            logger.info("查询土壤信息、水质信息成功:{}",allMap);
            result.setData(allMap);
        } catch (Exception e) {
            result.setCode(ERROR_CODE);
            logger.error("查询土壤信息、水质信息失败：{}",e);
        }
        return result;
    }


    private boolean checkeMapIsEmpty(Map<String,Object> params){
        boolean checkeStatus = true;
        if (MapUtils.isNotEmpty(params)) {
            for (Map.Entry<String,Object> entry : params.entrySet()) {
                if (StringUtils.isNotEmpty(String.valueOf(entry.getValue()))){
                    checkeStatus = false;
                    continue;
                }
            }
        }
        return checkeStatus;
    }
}
