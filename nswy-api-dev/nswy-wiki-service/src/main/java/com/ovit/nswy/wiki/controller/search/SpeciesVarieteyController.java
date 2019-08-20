package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyQuery;
import com.ovit.nswy.wiki.search.service.SpeciesServce;
import com.ovit.nswy.wiki.search.service.SpeciesVarieteyService;
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
@Api(tags = "/api/wiki",description="物种品种管理")
public class SpeciesVarieteyController {

    @Autowired
    private SpeciesVarieteyService speciesVarieteyService;

    @Autowired
    private SpeciesServce speciesServce;

    private Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping("/listSpeciesVarietey")
    @ApiOperation(value="物种品种列表接口", response = SpeciesVarieteyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesVarietey( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesVarieteyListQuery speciesVarieteyListQuery){
        Page<Map<String, Object>> mapPage= speciesVarieteyService.listSpeciesVarietey(speciesVarieteyListQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }

    @PostMapping("/findByVarieteyName")
    @ApiOperation(value="物种品种列表接口", response = SpeciesVarieteyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response findByVarieteyName( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesVarieteyListQuery speciesVarieteyListQuery){
        List<Map<String, Object>> mapList= speciesVarieteyService.findByVarieteyName(speciesVarieteyListQuery);
        return Response.successResult(mapList);
    }

    @PostMapping("/getVarieteyList")
    @ApiOperation(value="物种品种全部信息接口", response = SpeciesVarieteyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getPestList(@ApiParam(required = true,value = "类目参数类") @RequestBody SpeciesVarieteyListQuery speciesVarieteyListQuery){
        List<Map<String, Object>> result= speciesVarieteyService.getVarieteyList(speciesVarieteyListQuery);
        return Response.successResult(result);
    }

    @PostMapping("/saveSpeciesVarietey")
    @ApiOperation(value="新增物种品种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesVarietey(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesVarieteyQuery speciesVarieteyQuery) throws ParamValidException {
        Object result= speciesVarieteyService.saveSpeciesVarietey(speciesVarieteyQuery);
        return Response.successResult(result);
    }


    @GetMapping("/getSpeciesVarietey/{indexid}")
    @ApiOperation(value="获取物种品种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesVarietey(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result = speciesVarieteyService.getSpeciesVarietey(indexid);
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


    @GetMapping("/deleteSpeciesVarietey/{indexid}/{userId}")
    @ApiOperation(value="物理删物种品种种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesVarietey(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid, @ApiParam(required = true,value = "用户ID")@PathVariable String userId){
        //新增一条数据,后台审核
        Map<String, Object> result = speciesVarieteyService.getSpeciesVarietey(indexid);
        SpeciesVarieteyQuery varieteyHistory = new SpeciesVarieteyQuery();
        try {
            speciesVarieteyService.mapToSpeciesVarieteyQuery(varieteyHistory, result);
        } catch (Exception e) {
            logger.error("转化对象异常", e);
        }
        varieteyHistory.setFcreatorid(userId);
        varieteyHistory.setAuditstatus(3);
        varieteyHistory.setIndexid("");
        varieteyHistory.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //SpeciesVarieteyQuery speciesVarieteyQuery = new SpeciesVarieteyQuery();
        //speciesVarieteyQuery.setSpeciesid(MapUtils.getString(result, "speciesid"));
        //speciesVarieteyQuery.setFid(MapUtils.getString(result, "fid"));
        //speciesVarieteyQuery.setAuditstatus(3);
        speciesVarieteyService.saveSpeciesVarietey(varieteyHistory);
        return Response.successResult();
    }

    @PostMapping("/deleteVarietySpecies")
    @ApiOperation(value="物理删物种品种种接口", response = SpeciesVarieteyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteVarietySpecies(@ApiParam(required = true,value = "类目参数类") @RequestBody Map<String,Object> queryMap){
        List<Map<String,Object>> speciesVarieteyQueryList = (List<Map<String,Object>>)queryMap.get("dataList");
        for(Map<String,Object> map :speciesVarieteyQueryList){

            SpeciesVarieteyQuery varieteyHistory = new SpeciesVarieteyQuery();

            varieteyHistory.setIndexid(MapUtils.getString(map,"indexid"));
            varieteyHistory.setFisdelete(1);

            speciesVarieteyService.updateSpeciesVarietey(varieteyHistory);

        }

        return Response.successResult();
    }


    @PostMapping("/updateSpeciesVarieteyAudit")
    @ApiOperation(value="修改物种品种接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesVarieteyAudit(@RequestBody SpeciesVarieteyQuery speciesVarieteyQuery){
        speciesVarieteyService.updateSpeciesVarietey(speciesVarieteyQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpeciesVarietey")
    @ApiOperation(value="修改物种品种并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesVarietey(@RequestBody SpeciesVarieteyQuery speciesVarieteyQuery){
        //新增一条数据,后台审核
        speciesVarieteyQuery.setAuditstatus(0);
        speciesVarieteyService.saveSpeciesVarietey(speciesVarieteyQuery);
        return Response.successResult();
    }

    @PostMapping("/updateVarieteyPassAudit")
    @ApiOperation(value="修改物种品种并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateVarieteyPassAudit(@RequestBody SpeciesVarieteyQuery speciesVarieteyQuery){
        //审核的indexid
        String auditIndexId = speciesVarieteyQuery.getIndexid();
        //查询审核的数据
        Map<String, Object> auditVarieteyInfo = speciesVarieteyService.getSpeciesVarietey(auditIndexId);
        String speciesId = MapUtils.getString(auditVarieteyInfo, "speciesid");

        SpeciesVarieteyListQuery speciesVarieteyListQuery = new SpeciesVarieteyListQuery();
        speciesVarieteyListQuery.setSpeciesId(speciesId);
        speciesVarieteyListQuery.setFid(MapUtils.getString(auditVarieteyInfo, "fid"));
        Page<Map<String, Object>> mapPage = speciesVarieteyService.listSpeciesVarietey(speciesVarieteyListQuery);

        //原始数据的indexId
        String originIndexId =  MapUtils.getString(mapPage.getRecordList().get(0), "indexid");
        auditVarieteyInfo.put("indexid", originIndexId);
        speciesVarieteyService.updateVarieteyPassAudit(auditVarieteyInfo);

        return Response.successResult();
    }

    @GetMapping("/getIsVarieteyNameExist/{name}")
    @ApiOperation(value="获取输入品种名称是否存在")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getIsVarieteyNameExist(@ApiParam(required = true,value = "物种名称")@PathVariable String name){
        Object result=speciesVarieteyService.isNameExist(name);
        return Response.successResult(result);
    }

}
