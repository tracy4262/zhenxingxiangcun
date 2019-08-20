package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPestListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPestQuery;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;
import com.ovit.nswy.wiki.search.service.SpeciesPestService;
import com.ovit.nswy.wiki.search.service.SpeciesServce;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@RestController
@BaseException
@Slf4j
@RequestMapping("/api/wiki")
@Api(tags = "/api/wiki",description="物种虫害管理")
public class SpeciesPestController {

    @Autowired
    private SpeciesPestService speciesPestService;

    @Autowired
    private SpeciesServce speciesServce;

    private Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping("/listSpeciesPest")
    @ApiOperation(value="物种虫害列表接口", response = SpeciesPestListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesPest( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesPestListQuery speciesPestListQuery){
        Page<Map<String, Object>> mapPage= speciesPestService.listSpeciesPest(speciesPestListQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }

    @PostMapping("/findByPestName")
    @ApiOperation(value="物种虫害列表接口", response = SpeciesPestListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response findByPestName( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesPestListQuery speciesPestListQuery){
        List<Map<String, Object>> mapList= speciesPestService.findByPestName(speciesPestListQuery);
        return Response.successResult(mapList);
    }


    @PostMapping("/getPestList")
    @ApiOperation(value="物种虫害列表全部信息接口", response = SpeciesPestListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getPestList(@ApiParam(required = true,value = "类目参数类") @RequestBody SpeciesPestListQuery speciesPestListQuery){
        List<Map<String, Object>> result= speciesPestService.getPestList(speciesPestListQuery);
        return Response.successResult(result);
    }


    @PostMapping("/saveSpeciesPest")
    @ApiOperation(value="新增物种虫害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesPest(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesPestQuery speciesVarieteyQuery){
        Object result= speciesPestService.saveSpeciesPest(speciesVarieteyQuery);
        return Response.successResult(result);
    }


    @GetMapping("/getSpeciesPest/{indexid}")
    @ApiOperation(value="获取物种虫害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesPest(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result = speciesPestService.getSpeciesPest(indexid);
        try {
            String speciesId = MapUtils.getString(result, "speciesid");
            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setSpeciesid(speciesId);
            Page<Map<String, Object>> mapPage = speciesServce.listSpecies(speciesQuery);
            String specName = MapUtils.getString(mapPage.getRecordList().get(0), "fname");
            result.put("specName", specName);

        } catch (Exception e) {
            logger.error("获取物种名称异常", e);
        }
        return Response.successResult(result);
    }


    @GetMapping("/deleteSpeciesPest/{indexid}/{userId}")
    @ApiOperation(value="物理删物种虫害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesPest(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid, @ApiParam(required = true,value = "用户ID")@PathVariable String userId){
        //新增一条数据,后台审核
        Map<String, Object> result = speciesPestService.getSpeciesPest(indexid);
        SpeciesPestQuery pestHistory = new SpeciesPestQuery();
        try {
            speciesPestService.mapToSpeciesPestQuery(pestHistory, result);
        } catch (Exception e) {
            logger.error("转化对象异常", e);
        }
        pestHistory.setFcreatorid(userId);
        pestHistory.setIndexid("");
        pestHistory.setAuditstatus(3);
        pestHistory.setFid(MapUtils.getString(result, "fid"));
        pestHistory.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesPestQuery.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesPestQuery.setFid(MapUtils.getString(result, "fid"));
        //speciesPestQuery.setAuditstatus(3);
        speciesPestService.saveSpeciesPest(pestHistory);
        //speciesPestService.deleteSpeciesPest(indexid);
        return Response.successResult();
    }

    @PostMapping("/deletePestSpecies")
    @ApiOperation(value="物理删物种虫害接口", response = SpeciesPestListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deletePestSpecies( @ApiParam(required = true,value = "物种查询参数类") @RequestBody Map<String,Object> queryMap){
        List<Map<String,Object>> speciesPestQueryList = (List<Map<String,Object>>)queryMap.get("dataList");
        for(Map<String,Object> map :speciesPestQueryList){

            SpeciesPestQuery pestHistory = new SpeciesPestQuery();
            pestHistory.setIndexid(MapUtils.getString(map,"indexid"));
            pestHistory.setFisdelete(1);
            speciesPestService.updateSpeciesPest(pestHistory);
        }
        return Response.successResult();
    }


    @PostMapping("/updateSpeciesPestAudit")
    @ApiOperation(value="修改物种虫害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesPestAudit(@RequestBody SpeciesPestQuery speciesPestQuery){
        speciesPestService.updateSpeciesPest(speciesPestQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpeciesPest")
    @ApiOperation(value="修改物种虫害并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesPest(@RequestBody SpeciesPestQuery speciesPestQuery){
        //新增一条数据,后台审核
        speciesPestQuery.setAuditstatus(0);
        speciesPestService.saveSpeciesPest(speciesPestQuery);
        return Response.successResult();
    }

    @PostMapping("/updatePestPassAudit")
    @ApiOperation(value="修改物种虫害并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updatePestPassAudit(@RequestBody SpeciesPestQuery speciesPestQuery){
        //审核的indexid
        String auditIndexId = speciesPestQuery.getIndexid();
        //查询审核的数据
        Map<String, Object> auditPestInfo = speciesPestService.getSpeciesPest(auditIndexId);
        String speciesId = MapUtils.getString(auditPestInfo, "speciesid");

        SpeciesPestListQuery speciesPestListQuery = new SpeciesPestListQuery();
        speciesPestListQuery.setSpeciesId(speciesId);
        speciesPestListQuery.setFid(MapUtils.getString(auditPestInfo, "fid"));
        Page<Map<String, Object>> mapPage = speciesPestService.listSpeciesPest(speciesPestListQuery);

        //原始数据的indexId
        String originIndexId =  MapUtils.getString(mapPage.getRecordList().get(0), "indexid");
        auditPestInfo.put("indexid", originIndexId);
        speciesPestService.updatePestPassAudit(auditPestInfo);

        return Response.successResult();
    }


}
