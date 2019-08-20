package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.wiki.model.request.BaseQuery;
import com.ovit.nswy.wiki.model.request.SpeciesClassListQuery;
import com.ovit.nswy.wiki.search.service.SpeciesclassifiedService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-25.
 */

@RestController
@BaseException
@Slf4j
@RequestMapping("/speciesclass")
@Api(tags = "/speciesclass",description="物种分类")
public class SpeciesclassifiedController {

    @Autowired
    private SpeciesclassifiedService speciesclassifiedService;

    @PostMapping("/listSpeciesclass")
    @ApiOperation(value="物种分类列表接口", response = BaseQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesclass(@ApiParam(required = true,value = "物种分类查询参数类") @RequestBody SpeciesClassListQuery speciesClassListQuery ){
        List<Map<String, Object>> mapList= speciesclassifiedService.listSpeciesclassified(speciesClassListQuery);
        return Response.successResult(mapList);
    }

    @GetMapping("/listAllSpeciesclass")
    @ApiOperation(value="查询全部物种分类列表接口", response = BaseQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listAllSpeciesclass(){
         Object result= speciesclassifiedService.listAllSpeciesclass();
        return Response.successResult(result);
    }


    @GetMapping("/findSpeciesList")
    @ApiOperation(value="查询全部物种分类及物种列表接口", response = BaseQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response findSpeciesList(){
        Object result= speciesclassifiedService.findSpeciesList();
        return Response.successResult(result);
    }

    @PostMapping("/saveSpeciesclass")
    @ApiOperation(value="新增物种分类接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesclass(@RequestBody Map params){
        speciesclassifiedService.saveSpeciesclassified(params);
        return Response.successResult();
    }

    @PostMapping("/insertSpeciesclass")
    @ApiOperation(value="新增物种分类接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response insertSpeciesclass(@RequestBody Map params){
        speciesclassifiedService.insertSpeciesclass(params);
        return Response.successResult();
    }

    @GetMapping("/getSpeciesclass/{indexid}")
    @ApiOperation(value="获取物种分类接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesclass(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> objectMap=speciesclassifiedService.getSpeciesclassified(indexid);
        return Response.successResult(objectMap);
    }


    @GetMapping("/deleteSpeciesclass/{indexid}")
    @ApiOperation(value="物理删除物种分类接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesclass(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        speciesclassifiedService.deleteSpeciesclassified(indexid);
        return Response.successResult();
    }
}
