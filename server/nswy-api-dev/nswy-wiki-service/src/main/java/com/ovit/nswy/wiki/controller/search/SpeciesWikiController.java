package com.ovit.nswy.wiki.controller.search;


import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.frame.common.annotation.OperLog;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.model.request.SpeciesCatalogQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPropertyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPropertyQuery;
import com.ovit.nswy.wiki.model.request.SpeciesWikiQuery;
import com.ovit.nswy.wiki.search.manager.SpeciesWikiManager;
import com.ovit.nswy.wiki.search.service.SpeciesPropertyService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
@RestController
@BaseException
@Slf4j
@RequestMapping("/api/wiki")
@Api(tags = "/api/wiki",description="物种百科管理")
public class SpeciesWikiController {

    @Autowired
    private SpeciesWikiService speciesWikiService;
    @Autowired
    private SpeciesWikiManager speciesWikiManager;
    @Autowired
    private SpeciesPropertyService speciesPropertyService;

    @OperLog(value ="saveLikedCount")
    @GetMapping("/saveLikedCount/{indexId}")
    @ApiOperation(value="添加点赞接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveLikedCount(@ApiParam(required = true,value = "") @PathVariable String indexId) throws ParamValidException {
        Integer result= speciesWikiService.getLikedCount(indexId);
        return Response.successResult(result);
    }


    @GetMapping("/getLikedCount/{indexId}")
    @ApiOperation(value="获取点赞接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getLikedCount(@ApiParam(required = true,value = "索引Id")@PathVariable String indexId) throws ParamValidException {
        Integer result= speciesWikiService.getLikedCount(indexId);
        return Response.successResult(result);
    }


    @PostMapping("/listCatalog")
    @ApiOperation(value="类目列表接口", response = SpeciesCatalogQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listCatalog(@ApiParam(required = true,value = "类目参数类") @RequestBody SpeciesCatalogQuery speciesCatalogQuery){
        List<Map<String, Object>> result = speciesWikiService.listCatalog(speciesCatalogQuery);
        return Response.successResult(result);
    }

    @PostMapping("/saveCatalog")
    @ApiOperation(value="新增类目接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response  saveCatalog(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesWikiQuery speciesWikiQuery){
        Object result= speciesWikiService.saveCatalog(speciesWikiQuery);
        return Response.successResult(result);
    }


    /*@GetMapping("/deleteCatalog/{indexid}")
    @ApiOperation(value="删除类目接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteCatalog(@ApiParam(required = true,value = "索引Id") @PathVariable String indexid){

        //因此先查出对应的目录数据，得到speciesid和propertyid,查到对应的目录数据，删除目录的同时，需要将目录对应的数据也删除掉。
        Map<String, Object> result = speciesWikiService.getCatalog(indexid);
        String speciesId = MapUtils.getString(result, "species_id");
        String propertyId = MapUtils.getString(result, "propertyid");
        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesId);
        speciesPropertyListQuery.setPropertyid(propertyId);
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        if (propertyInfo.size() > 0) {
            String propertyIndex = MapUtils.getString(propertyInfo.get(0), "indexid");
            //删除目录数据
            speciesPropertyService.deleteSpeciesProperty(propertyIndex);
        }
        //删除目录
        speciesWikiService.deleteCatalog(indexid);
        return Response.successResult();
    }*/

    @GetMapping("/deleteCatalog/{indexid}")
    @ApiOperation(value="删除类目接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteCatalog(@ApiParam(required = true,value = "索引Id") @PathVariable String indexid){

        //因此先查出对应的目录数据，得到speciesid和propertyid,查到对应的目录数据，删除目录的同时，需要将目录对应的数据也删除掉。
        /*Map<String, Object> result = speciesWikiService.getCatalog(indexid);
        String speciesId = MapUtils.getString(result, "species_id");
        String propertyId = MapUtils.getString(result, "propertyid");
        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesId);
        speciesPropertyListQuery.setPropertyid(propertyId);
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        if (propertyInfo.size() > 0) {
            String propertyIndex = MapUtils.getString(propertyInfo.get(0), "indexid");
            //删除目录数据
            SpeciesPropertyQuery speciesPropertyQuery = new SpeciesPropertyQuery();
            speciesPropertyQuery.setSpeciesid(MapUtils.getString(propertyInfo.get(0), "speciesid"));
            speciesPropertyQuery.setPropertyid(MapUtils.getString(propertyInfo.get(0), "propertyid"));
            speciesPropertyQuery.setAuditstatus(3);
            speciesPropertyService.saveSpeciesProperty(speciesPropertyQuery);
            //speciesPropertyService.deleteSpeciesProperty(propertyIndex);
        }
        //删除目录
        SpeciesWikiQuery speciesWikiQuery = new SpeciesWikiQuery();
        speciesWikiQuery.setSpecies_id(speciesId);
        speciesWikiQuery.setPropertyid(propertyId);
        speciesWikiQuery.setAuditstatus(3);
        speciesWikiService.saveCatalog(speciesWikiQuery);*/

        speciesWikiService.deleteCatalog(indexid);
        return Response.successResult();
    }


    @GetMapping("/getCatalog/{indexid}")
    @ApiOperation(value="获取类目接口")
    public Response getCatalog(@ApiParam(required = true,value = "索引Id") @PathVariable String indexid ){
        Map<String, Object> result= speciesWikiService.getCatalog(indexid);
        return Response.successResult(result);
    }

    @PostMapping("/updateCatalog")
    @ApiOperation(value="修改类目接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response  updateCatalog(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesWikiQuery speciesWikiQuery){
        speciesWikiService.updateCatalog(speciesWikiQuery);
        return Response.successResult();
    }

    /*@PostMapping("/updateCatalog")
    @ApiOperation(value="修改目录名称接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response  updateCatalogAudit(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesWikiQuery speciesWikiQuery){
        //新增一条数据,后台审核
        speciesWikiQuery.setAuditstatus(0);
        speciesWikiService.saveCatalog(speciesWikiQuery);
        return Response.successResult();
    }*/

    @GetMapping("/searchSpecies/{name}")
    @ApiOperation(value="获取物种详情搜索接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response searchSpecies(@ApiParam(required = true,value = "物种名称") @PathVariable  String name){
        Object result=speciesWikiManager.searchSpecies(name);
        return Response.successResult(result);
    }

    @GetMapping("/existName/{type}/{name}")
    @ApiOperation(value="获取名称是否重复接口，结果集为【1，是存在，0 不存在】")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response existName(@ApiParam(required = true,value = "类型【1 物种，2 品种，3 病害，4 虫害】") @PathVariable Integer type,@ApiParam(required = true,value = "物种名称") @PathVariable  String name){
        Object result=speciesWikiManager.existName(type,name);
        return Response.successResult(result);
    }


}
