package com.ovit.nswy.wiki.controller.search;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.feigen.RelevantInfoFeign;
import com.ovit.nswy.wiki.model.request.*;
import com.ovit.nswy.wiki.search.manager.SpeciesWikiManager;
import com.ovit.nswy.wiki.search.service.SpeciesServce;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by ${huipei.x} on 2017-10-20.
 */
@RestController
@BaseException
@Slf4j
@RequestMapping("/api/species")
@Api(tags = "/api/species",description="物种管理")
public class SpeciesController {

    @Autowired
    private SpeciesServce speciesServce;
    @Autowired
    private SpeciesWikiManager speciesWikiManager;
    @Autowired
    private RelevantInfoFeign relevantInfoFeign;

    private Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping("/listSpecies")
    @ApiOperation(value="物种列表接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpecies( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesQuery speciesQuery)throws ParamValidException {
        Page<Map<String, Object>> mapPage= speciesServce.listSpecies(speciesQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }


    @PostMapping("/findBySpeciesName")
    @ApiOperation(value="物种列表接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response findBySpeciesName(@ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesQuery speciesQuery)throws ParamValidException {
        List<Map<String, Object>> mapList= speciesServce.findBySpeciesName(speciesQuery);
        return Response.successResult(mapList);
    }

    @PostMapping("/getSpeciesList")
    @ApiOperation(value="物种列表全部信息接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesList(@ApiParam(required = true,value = "类目参数类") @RequestBody SpeciesQuery speciesQuery){
        List<Map<String, Object>> result= speciesServce.getSpeciesList(speciesQuery);
        return Response.successResult(result);
    }

    @PostMapping("/getLatelyData")
    @ApiOperation(value="最近更新/查询历史接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getLatelyHistoryData( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesQuery speciesQuery)throws ParamValidException {
        Page<Map<String, Object>> mapPage= speciesServce.getLatelyData(speciesQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }

    @PostMapping("/getSpeciesListInfo")
    @ApiOperation(value="根据条件查询物种列表全部信息接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesListInfo(@ApiParam(required = true,value = "类目参数类") @RequestBody Map<String,Object> queryMap){

        try {
            List<SpeciesQuery> SpeciesQueryList = new ArrayList<>();
            int pageNum = MapUtils.getInteger(queryMap, "pageNum");
            int pageSize = MapUtils.getInteger(queryMap, "pageSize");
            JSONArray classArray = JSONArray.parseArray(queryMap.get("fclassifiedid").toString());
            if (classArray.size() == 0) {
                SpeciesQuery speciesQuery = new SpeciesQuery();
                if (queryMap.get("fpinyin") != null) {
                    speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                }
                if (queryMap.get("keywords") != null) {
                    speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                }
                if (queryMap.get("findustriaclassifiedid") != null) {
                    speciesQuery.setFindustriaclassifiedid(MapUtils.getString(queryMap, "findustriaclassifiedid"));
                }
                List<Map<String,Object>> speciesList = speciesServce.getSpeciesList(speciesQuery);
                int totalNum = speciesList.size();
                Map<String,Object> speciesMap = new HashMap<>();
                List<Map<String,Object>> speciesPageInfo = speciesServce.speciesPageInfo(speciesList, pageNum, pageSize);
                speciesMap.put("speciesListData", speciesPageInfo);
                speciesMap.put("totalNum", totalNum);
                logger.info("筛选条件：{}，筛选后的数据物种数据为: {}", speciesQuery, speciesList);
                return Response.successResult(speciesMap);

            } else {
                Object[] classIds = classArray.toArray();
                for (Object classId : classIds) {
                    if ("0".equals(String.valueOf(classId))) {
                        //选择了动物
                        Map<String,Object> speciesclassList = speciesServce.listClassifySpeciesInfo();
                        List<Map<String,Object>> animalList = (List<Map<String, Object>>) speciesclassList.get("animalList");
                        for (Map<String,Object> animal : animalList) {
                            SpeciesQuery speciesQuery = new SpeciesQuery();
                            speciesQuery.setFclassifiedid(MapUtils.getString(animal, "classId"));
                            if (queryMap.get("fpinyin") != null) {
                                speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                            }
                            if (queryMap.get("keywords") != null) {
                                speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                            }
                            if (queryMap.get("findustriaclassifiedid") != null) {
                                speciesQuery.setFindustriaclassifiedid(MapUtils.getString(queryMap, "findustriaclassifiedid"));
                            }
                            SpeciesQueryList.add(speciesQuery);
                        }
                    } else if ("1".equals(String.valueOf(classId))) {
                        //选择了植物
                        Map<String,Object> speciesclassList = speciesServce.listClassifySpeciesInfo();
                        List<Map<String,Object>> plantList = (List<Map<String, Object>>) speciesclassList.get("plantList");
                        for (Map<String,Object> plant : plantList) {
                            SpeciesQuery speciesQuery = new SpeciesQuery();
                            speciesQuery.setFclassifiedid(MapUtils.getString(plant, "classId"));
                            if (queryMap.get("fpinyin") != null) {
                                speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                            }
                            if (queryMap.get("keywords") != null) {
                                speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                            }
                            if (queryMap.get("findustriaclassifiedid") != null) {
                                speciesQuery.setFindustriaclassifiedid(MapUtils.getString(queryMap, "findustriaclassifiedid"));
                            }
                            SpeciesQueryList.add(speciesQuery);
                        }
                    } else {
                        SpeciesQuery speciesQuery = new SpeciesQuery();
                        speciesQuery.setFclassifiedid(String.valueOf(classId));
                        if (queryMap.get("fpinyin") != null) {
                            speciesQuery.setFpinyin(MapUtils.getString(queryMap, "fpinyin"));
                        }
                        if (queryMap.get("keywords") != null) {
                            speciesQuery.setKeywords(MapUtils.getString(queryMap, "keywords"));
                        }
                        if (queryMap.get("findustriaclassifiedid") != null) {
                            speciesQuery.setFindustriaclassifiedid(MapUtils.getString(queryMap, "findustriaclassifiedid"));
                        }
                        SpeciesQueryList.add(speciesQuery);
                    }
                }
                //通过classId将SpeciesQueryList去重
                List<SpeciesQuery> queryList = removeDuplicate(SpeciesQueryList);
                List<Map<String,Object>> speciesAllList = new ArrayList<>();
                for (SpeciesQuery species : queryList) {
                    List<Map<String,Object>> speciesList = speciesServce.getSpeciesList(species);
                    speciesAllList.addAll(speciesList);
                }

                int totalNum = speciesAllList.size();
                Map<String,Object> speciesMap = new HashMap<>();
                List<Map<String,Object>> speciesPageInfo = speciesServce.speciesPageInfo(speciesAllList, pageNum, pageSize);
                speciesMap.put("speciesListData", speciesPageInfo);
                speciesMap.put("totalNum", totalNum);
                logger.info("筛选后的数据物种数据为: {}", speciesAllList);
                return Response.successResult(speciesMap);
            }
        } catch (Exception e) {
            logger.error(e);
            return Response.errorResult("物种列表查询失败");
        }
    }

    @PostMapping("/saveSpecies")
    @ApiOperation(value="新增物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpecies(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesAddQuery speciesAddQuery){

        Object result= speciesServce.saveSpecies(speciesAddQuery);
        //物种新增成功则查询出当前记录返回修改使用
        Map<String, Object> resultMap = null;
        if (!"0".equals(result)) {
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setSpeciesid(result.toString());
            Page<Map<String, Object>> mapPage= speciesServce.listSpecies(speciesQuery);
            List<Map<String,Object>> list = mapPage.getRecordList();
            if (CollectionUtils.isNotEmpty(list)) {
                new HashMap<>();
                logger.debug("物种列表:{}",list);
                Map<String, Object> speciesMap = list.get(0);
                resultMap = new HashMap<>();
                resultMap.put("indexid",MapUtils.getString(speciesMap,"indexid"));
                resultMap.put("speciesid",MapUtils.getString(speciesMap,"speciesid"));
            }
        }
        return Response.successResult(resultMap);
    }


    @GetMapping("/getSpecies/{indexid}")
    @ApiOperation(value="获取物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpecies(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result = speciesServce.getSpecies(indexid);
        if (StringUtils.isBlank(MapUtils.getString(result, "likedcount"))) {
            result.put("likedcount", 0);
        }
        speciesWikiManager.getSpeciesEscape(result);
        return Response.successResult(result);
    }


    @GetMapping("/deleteSpecies/{indexid}/{userId}")
    @ApiOperation(value="物理删除物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpecies(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid, @ApiParam(required = true,value = "用户ID")@PathVariable String userId){
        //新增一条数据,后台审核
        Map<String, Object> result = speciesServce.getSpecies(indexid);
        SpeciesAddQuery speciesHistory = new SpeciesAddQuery();
        try {
            speciesServce.mapToSpeciesAddQuery(speciesHistory, result);
        } catch (Exception e) {
            logger.error("转化对象异常", e);
        }
        speciesHistory.setFcreatorid(userId);
        speciesHistory.setAuditstatus(3);
        speciesHistory.setIndexid("");
        //SpeciesAddQuery speciesAddQuery = new SpeciesAddQuery();
        //speciesAddQuery.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesAddQuery.setAuditstatus(3);
        speciesServce.saveSpecies(speciesHistory);
        return Response.successResult();
    }

    @PostMapping("/deleteWikiSpecies")
    @ApiOperation(value="根据条件查询物种列表全部信息接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteWikiSpecies(@ApiParam(required = true,value = "类目参数类") @RequestBody Map<String,Object> queryMap){
        List<Map<String,Object>> speciesQueryList = (List<Map<String,Object>>)queryMap.get("dataList");
        for(Map<String,Object> map :speciesQueryList){

            SpeciesAddQuery speciesHistory = new SpeciesAddQuery();

            speciesHistory.setIndexid(MapUtils.getString(map,"indexid"));
            speciesHistory.setFisdelete(1);

            speciesServce.updateSpecies(speciesHistory);

        }

        return Response.successResult();
    }


    @GetMapping("/getSpeciesPinYin/{chinese}")
    @ApiOperation(value="获取中文转拼音接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesPinYin(@ApiParam(required = true,value = "中文字段") @PathVariable String chinese){
       String result= speciesServce.getPinYin(chinese);
        return Response.successResult(result);
    }


    @GetMapping("/deleteByIndexid/{indexid}/{fisdelete}")
    @ApiOperation(value="删除物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteByIndexid(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid,@ApiParam(required = true,value = "删除字段")@PathVariable Integer fisdelete){
        speciesServce.updateFisdeleteByIndexid(indexid, fisdelete);
        return Response.successResult();
    }


    @PostMapping("/updateSpeciesAudit")
    @ApiOperation(value="修改物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesAudit(@ApiParam(required = true,value = "修改参数类") @RequestBody SpeciesAddQuery speciesAddQuery){
        speciesServce.updateSpecies(speciesAddQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpecies")
    @ApiOperation(value="修改物种并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpecies(@ApiParam(required = true,value = "修改参数类") @RequestBody SpeciesAddQuery speciesAddQuery){
        //新增一条数据,后台审核
        speciesAddQuery.setAuditstatus(0);
        speciesServce.saveSpecies(speciesAddQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpeciesPassAudit")
    @ApiOperation(value="修改物种审核通过接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesPassAudit(@ApiParam(required = true,value = "修改参数类") @RequestBody SpeciesAddQuery speciesAddQuery){
        //审核的indexid
        String auditIndexId = speciesAddQuery.getIndexid();
        //查询审核的数据
        Map<String, Object> auditSpeciesInfo = speciesServce.getSpecies(auditIndexId);
        String speciesId = MapUtils.getString(auditSpeciesInfo, "speciesid");
        SpeciesQuery speciesQuery = new SpeciesQuery();
        speciesQuery.setSpeciesid(speciesId);
        //speciesQuery.setAuditStatus(1);
        List<Map<String, Object>> originSpeciesInfo = speciesServce.getSpeciesList(speciesQuery);
        if(originSpeciesInfo.size() ==0 || originSpeciesInfo!=null){
            speciesQuery = new SpeciesQuery();
            speciesQuery.setSpeciesid(speciesId);
            speciesQuery.setAuditStatus(1);
            speciesQuery.setFname(MapUtils.getString(auditSpeciesInfo,"fname"));
            speciesQuery.setFcreatorid(MapUtils.getString(auditSpeciesInfo,"fcreatorid"));
            originSpeciesInfo = speciesServce.findBySpeciesName(speciesQuery);
        }
        //原始数据的indexId
        String originIndexId =  MapUtils.getString(originSpeciesInfo.get(0), "indexid");
        auditSpeciesInfo.put("indexid", originIndexId);
        speciesServce.updateSpeciesPassAudit(auditSpeciesInfo);
        return Response.successResult();
    }


    @GetMapping("/getIsNameExist/{name}")
    @ApiOperation(value="获取输入物种名称是否存在")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getIsNameExist(@ApiParam(required = true,value = "物种名称")@PathVariable String name){
        Object result=speciesServce.isNameExist(name);
        return Response.successResult(result);
    }


    @PostMapping("/searchSpecies")
    @ApiOperation(value="获取物种详情搜索接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response searchSpecies(@ApiParam(required = true,value = "物种详情参数类") @RequestBody SpeciesDetailQuery speciesDetailQuery){
        Object result=speciesWikiManager.searchSpecies(speciesDetailQuery);
        return Response.successResult(result);
    }

    @PostMapping("/getRelevantInfo")
    @ApiOperation(value="获取物种百科相关信息接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getRelevantInfo(@ApiParam(required = true,value = "获取物种百科相关信息") @RequestBody Map<String,Object> params){
        //获取相关专家、相关企业、相关产品信息
        Object result = relevantInfoFeign.getRelevantInfo(params);
        JSONObject jsonObject = JSONObject.parseObject(result.toString());
        Map<String,Object> relevantInfo = JSON.parseObject(jsonObject.get("data").toString(), Map.class);
        String classId = MapUtils.getString(params, "classId");
        SpeciesQuery speciesQuery = new SpeciesQuery();
        //speciesQuery.setFname(MapUtils.getString(params, "speciesName"));
        speciesQuery.setFclassifiedid(classId);
        speciesQuery.setPageSize(6);
        speciesQuery.setIsLemma(MapUtils.getString(params, "speciesName"));
        Page<Map<String, Object>> mapPage= speciesServce.listSpecies(speciesQuery);
        List<Map<String,Object>> recordList = mapPage.getRecordList();
        List<Map<String,Object>> relevantLemma = new ArrayList<>();
        for (Map<String, Object> record : recordList) {
            Map<String,Object> lemma = new HashMap<>();
            lemma.put("fid", MapUtils.getString(record, "fid"));
            lemma.put("fname", MapUtils.getString(record, "fname"));
            lemma.put("ficon", record.get("ficon"));
            lemma.put("fclassid", MapUtils.getString(record, "fclassifiedid"));
            lemma.put("indexid", MapUtils.getString(record, "indexid"));
            relevantLemma.add(lemma);
        }

        relevantInfo.put("relevantLemma", relevantLemma);

        logger.info("获取物种相关数据为：{}", relevantInfo);
        return Response.successResult(relevantInfo);
    }

    @PostMapping("/getSpeciesClassify")
    @ApiOperation(value="物种列表接口", response = SpeciesQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesClassify( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesQuery speciesQuery)throws ParamValidException {
        Map<String, Object> mapPage = speciesServce.getSpeciesClassify(speciesQuery);
        return Response.successResult(mapPage);
    }


    @PostMapping("/insertSpecies")
    @ApiOperation(value="新增物种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response insertSpecies(@RequestBody Map params){
        speciesServce.insertSpecies(params);
        return Response.successResult();
    }

    /**
     * 通过classId,去重
     * @param SpeciesQueryList
     * @return
     */
    public List<SpeciesQuery> removeDuplicate(List<SpeciesQuery> SpeciesQueryList) {

        List<SpeciesQuery> result = new ArrayList<>();
        List<String> classIdList  = new ArrayList<>();
        for (SpeciesQuery species : SpeciesQueryList) {
            String classId = species.getFclassifiedid();
            if (classIdList.contains(classId)) {
                //List包含classId,重复，不加入到result中
                logger.info("改物种ID：{}，已存在", classId);
            } else {
                classIdList.add(classId);
                result.add(species);
            }
        }
        return result;
    }

}
