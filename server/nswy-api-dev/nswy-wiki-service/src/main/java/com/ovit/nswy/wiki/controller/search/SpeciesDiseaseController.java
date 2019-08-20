package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseQuery;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;
import com.ovit.nswy.wiki.search.service.SpeciesDiseaseService;
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
@Api(tags = "/api/wiki",description="物种病害管理")
public class SpeciesDiseaseController {

    @Autowired
    private SpeciesDiseaseService speciesDiseaseService;

    @Autowired
    private SpeciesServce speciesServce;

    private Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping("/listSpeciesDisease")
    @ApiOperation(value="物种病害列表接口", response = SpeciesDiseaseListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesDisease( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesDiseaseListQuery speciesDiseaseListQuery){
        Page<Map<String, Object>> mapPage= speciesDiseaseService.listSpeciesDisease(speciesDiseaseListQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }

    @PostMapping("/findByDiseaseName")
    @ApiOperation(value="物种病害列表接口", response = SpeciesDiseaseListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response findByDiseaseName( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesDiseaseListQuery speciesDiseaseListQuery){
        List<Map<String, Object>> mapList= speciesDiseaseService.findByDiseaseName(speciesDiseaseListQuery);
        return Response.successResult(mapList);
    }

    @PostMapping("/getDiseaseList")
    @ApiOperation(value="物种病害列表全部信息接口", response = SpeciesDiseaseListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getDiseaseList(@ApiParam(required = true,value = "类目参数类") @RequestBody SpeciesDiseaseListQuery speciesDiseaseListQuery){
        List<Map<String, Object>> result= speciesDiseaseService.getDiseaseList(speciesDiseaseListQuery);
        return Response.successResult(result);
    }

    @PostMapping("/saveSpeciesDisease")
    @ApiOperation(value="新增物种病害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesDisease(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesDiseaseQuery speciesDiseaseQuery){
        Object result= speciesDiseaseService.saveSpeciesDisease(speciesDiseaseQuery);
        return Response.successResult(result);
    }


    @GetMapping("/getSpeciesDisease/{indexid}")
    @ApiOperation(value="获取物种病害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesDisease(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result=speciesDiseaseService.getSpeciesDisease(indexid);
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


    @GetMapping("/deleteSpeciesDisease/{indexid}/{userId}")
    @ApiOperation(value="物理删物物种病害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesDisease(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid, @ApiParam(required = true,value = "用户ID")@PathVariable String userId){
        //新增一条数据,后台审核
        Map<String, Object> result = speciesDiseaseService.getSpeciesDisease(indexid);
        SpeciesDiseaseQuery diseaseHistory = new SpeciesDiseaseQuery();
        try {
            speciesDiseaseService.mapToSpeciesDiseaseQuery(diseaseHistory, result);
        } catch (Exception e) {
            logger.error("转化对象异常", e);
        }
        diseaseHistory.setFcreatorid(userId);
        diseaseHistory.setIndexid("");
        diseaseHistory.setAuditstatus(3);
        diseaseHistory.setFid(MapUtils.getString(result, "fid"));
        diseaseHistory.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesDiseaseQuery.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesDiseaseQuery.setFid(MapUtils.getString(result, "fid"));
        //speciesDiseaseQuery.setAuditstatus(3);
        speciesDiseaseService.saveSpeciesDisease(diseaseHistory);
        //speciesDiseaseService.deleteSpeciesDisease(indexid);
        return Response.successResult();
    }

    @PostMapping("/deleteDiseaseSpecies")
    @ApiOperation(value="物理删物物种病害接口", response = SpeciesDiseaseListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteDiseaseSpecies( @ApiParam(required = true,value = "物种查询参数类") @RequestBody Map<String,Object> queryMap){
        List<Map<String,Object>> speciesQueryList = (List<Map<String,Object>>)queryMap.get("dataList");
        for(Map<String,Object> map :speciesQueryList){
            SpeciesDiseaseQuery speciesDiseaseQuery = new SpeciesDiseaseQuery();
            speciesDiseaseQuery.setIndexid(MapUtils.getString(map,"indexid"));
            speciesDiseaseQuery.setFisdelete(1);

            speciesDiseaseService.updateSpeciesDisease(speciesDiseaseQuery);

        }


        return Response.successResult();
    }


    @PostMapping("/updateSpeciesDiseaseAudit")
    @ApiOperation(value="修改物种病害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesDiseaseAudit(@RequestBody SpeciesDiseaseQuery speciesDiseaseQuery){
        speciesDiseaseService.updateSpeciesDisease(speciesDiseaseQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpeciesDisease")
    @ApiOperation(value="修改物种病害并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesDisease(@RequestBody SpeciesDiseaseQuery speciesDiseaseQuery){
        //新增一条数据,后台审核
        speciesDiseaseQuery.setAuditstatus(0);
        speciesDiseaseService.saveSpeciesDisease(speciesDiseaseQuery);
        return Response.successResult();
    }

    @PostMapping("/updateDiseasePassAudit")
    @ApiOperation(value="修改物种病害接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateDiseasePassAudit(@RequestBody SpeciesDiseaseQuery speciesDiseaseQuery){
        //审核的indexid
        String auditIndexId = speciesDiseaseQuery.getIndexid();
        //查询审核的数据
        Map<String, Object> auditDiseaseInfo = speciesDiseaseService.getSpeciesDisease(auditIndexId);
        String speciesId = MapUtils.getString(auditDiseaseInfo, "speciesid");

        SpeciesDiseaseListQuery speciesDiseaseListQuery = new SpeciesDiseaseListQuery();
        speciesDiseaseListQuery.setSpeciesId(speciesId);
        speciesDiseaseListQuery.setFid(MapUtils.getString(auditDiseaseInfo, "fid"));
        Page<Map<String, Object>> mapPage = speciesDiseaseService.listSpeciesDisease(speciesDiseaseListQuery);

        if(mapPage.getRecordList()==null||mapPage.getRecordList().size()==0){
            speciesDiseaseListQuery = new SpeciesDiseaseListQuery();
            speciesDiseaseListQuery.setSpeciesId(speciesId);
            speciesDiseaseListQuery.setAuditstatus(1);
            speciesDiseaseListQuery.setFname(MapUtils.getString(auditDiseaseInfo,"fname"));
            speciesDiseaseListQuery.setFcreatorid(MapUtils.getString(auditDiseaseInfo,"fcreatorid"));
            List<Map<String,Object>> list  = speciesDiseaseService.findByDiseaseName(speciesDiseaseListQuery);
            mapPage.setRecordList(list);
        }
        //原始数据的indexId
        String originIndexId =  MapUtils.getString(mapPage.getRecordList().get(0), "indexid");
        auditDiseaseInfo.put("indexid", originIndexId);
        speciesDiseaseService.updateDiseasePassAudit(auditDiseaseInfo);
        return Response.successResult();

    }

}
