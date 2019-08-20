package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.wiki.model.request.*;
import com.ovit.nswy.wiki.search.service.SpeciesPropertyService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@BaseException
@Slf4j
@RequestMapping("/api/property")
@Api(tags = "/api/property",description="物种百科自定义目录管理")
public class SpeciesPropertyController {

    @Autowired
    private SpeciesPropertyService speciesPropertyService;

    @Autowired
    private SpeciesWikiService speciesWikiService;

    @PostMapping("/saveSpeciesProperty")
    @ApiOperation(value="新增物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesProperty(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesPropertyQuery speciesPropertyQuery){
        Map<String, Object> result = speciesPropertyService.saveSpeciesProperty(speciesPropertyQuery);
        return Response.successResult(result);
    }

    @GetMapping("/getSpeciesProperty/{propertyid}")
    @ApiOperation(value="获取物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesProperty(@ApiParam(required = true,value = "索引Id")@PathVariable String propertyid){

        //Map<String, Object> result = speciesPropertyService.getSpeciesProperty(indexid);
        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setPropertyid(propertyid);
        List<Map<String, Object>> propertyList = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        if (propertyList.size() > 0) {
            return Response.successResult(propertyList.get(0));
        } else {
            return Response.successResult();
        }

    }

    @GetMapping("/getPropertyAudit/{indexid}")
    @ApiOperation(value="获取待审核物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getPropertyAudit(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result = speciesPropertyService.getSpeciesProperty(indexid);
        return Response.successResult(result);
    }

    @PostMapping("/listSpeciesProperty")
    @ApiOperation(value="物种自定义目录列表接口", response = SpeciesPropertyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesProperty( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesPropertyListQuery speciesPropertyListQuery){
        List<Map<String, Object>> propertyList = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        return Response.successResult(propertyList);
    }

    @PostMapping("/propertyPageList")
    @ApiOperation(value="物种自定义目录列表接口,带分页", response = SpeciesPropertyListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response propertyPageList( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesPropertyListQuery speciesPropertyListQuery){
        Page<Map<String, Object>> mapPage= speciesPropertyService.propertyPageList(speciesPropertyListQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }

    @GetMapping("/deleteSpeciesProperty/{indexid}")
    @ApiOperation(value="删除物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesProperty(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){

        speciesPropertyService.deleteSpeciesProperty(indexid);
        return Response.successResult();
    }

    /*@GetMapping("/deletePropertyById/{propertyid}")
    @ApiOperation(value="删除物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deletePropertyById(@ApiParam(required = true,value = "索引Id")@PathVariable String propertyid){
        //无species_id的版本

        //删除自定义目录，先新增一条数据，进行审核
        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setPropertyid(propertyid);
        //查询审核通过的原始数据
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        //新增一条删除待审核的数据
        SpeciesPropertyQuery speciesPropertyQuery = new SpeciesPropertyQuery();
        speciesPropertyQuery.setPropertyid(propertyid);
        speciesPropertyQuery.setSpeciesid(MapUtils.getString(propertyInfo.get(0), "speciesid"));
        speciesPropertyQuery.setAuditstatus(3);
        speciesPropertyService.saveSpeciesProperty(speciesPropertyQuery);

        return Response.successResult();
    }*/

    @PostMapping("/deletePropertyById")
    @ApiOperation(value="删除物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deletePropertyById(@RequestBody SpeciesPropertyQuery speciesPropertyQuery){
        //带species_id的版本

        //删除自定义目录，先新增一条数据，进行审核
        speciesPropertyQuery.setAuditstatus(3);
        speciesPropertyService.saveSpeciesProperty(speciesPropertyQuery);

        return Response.successResult();
    }

    /*@PostMapping("/updateSpeciesPropertyAudit")
    @ApiOperation(value="修改物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesPropertyAudit(@RequestBody SpeciesPropertyQuery speciesPropertyQuery){

        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesPropertyQuery.getSpeciesid());
        speciesPropertyListQuery.setPropertyid(speciesPropertyQuery.getPropertyid());
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        String propertyIndex = MapUtils.getString(propertyInfo.get(0), "indexid");
        speciesPropertyQuery.setIndexid(propertyIndex);
        speciesPropertyService.updateSpeciesProperty(speciesPropertyQuery);

        //更新目录对应数据后，若修改了标题名称，那么还要同步修改species_wiki索引中的目录名称
        *//*String propertytitle = speciesPropertyQuery.getPropertytitle();
        String catalogName = MapUtils.getString(propertyInfo.get(0), "propertytitle");
        if (!catalogName.equals(propertytitle)) {
            SpeciesCatalogQuery speciesCatalogQuery = new SpeciesCatalogQuery();
            speciesCatalogQuery.setPropertyid(speciesPropertyQuery.getPropertyid());
            speciesCatalogQuery.setSpecies_id(speciesPropertyQuery.getSpeciesid());
            List<Map<String, Object>> wikiList = speciesWikiService.listCatalog(speciesCatalogQuery);
            String wikiIndex = MapUtils.getString(wikiList.get(0), "indexid");
            SpeciesWikiQuery speciesWikiQuery = new SpeciesWikiQuery();
            speciesWikiQuery.setIndexid(wikiIndex);
            speciesWikiQuery.setCatalog_name(propertytitle);
            speciesWikiService.updateCatalog(speciesWikiQuery);
        }*//*

        return Response.successResult();
    }*/

    @PostMapping("/updateSpeciesPropertyAudit")
    @ApiOperation(value="修改物种自定义目录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesPropertyAudit(@ApiParam(required = true,value = "修改参数类") @RequestBody SpeciesPropertyQuery speciesPropertyQuery){
        speciesPropertyService.updateSpeciesProperty(speciesPropertyQuery);
        return Response.successResult();
    }

    @PostMapping("/updateSpeciesProperty")
    @ApiOperation(value="修改物种自定义目录并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesProperty(@RequestBody SpeciesPropertyQuery speciesPropertyQuery){

        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesPropertyQuery.getSpeciesid());
        speciesPropertyListQuery.setPropertyid(speciesPropertyQuery.getPropertyid());
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        String propertyIndex = MapUtils.getString(propertyInfo.get(0), "indexid");
        speciesPropertyQuery.setIndexid(propertyIndex);
        //新增一条数据,后台审核
        speciesPropertyQuery.setAuditstatus(0);
        speciesPropertyService.saveSpeciesProperty(speciesPropertyQuery);

        return Response.successResult();
    }

    @PostMapping("/updatePropertyPassAudit")
    @ApiOperation(value="修改物种自定义目录并审核接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updatePropertyPassAudit(@RequestBody SpeciesPropertyQuery speciesPropertyQuery){

        //审核的indexid
        String auditIndexId = speciesPropertyQuery.getIndexid();
        //查询审核的数据
        Map<String, Object> auditPropertyInfo = speciesPropertyService.getSpeciesProperty(auditIndexId);
        String speciesId = MapUtils.getString(auditPropertyInfo, "speciesid");
        String propertyId = MapUtils.getString(auditPropertyInfo, "propertyid");

        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesId);
        speciesPropertyListQuery.setPropertyid(propertyId);
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);

        //原始数据的indexId
        String originIndexId =  MapUtils.getString(propertyInfo.get(0), "indexid");
        auditPropertyInfo.put("indexid", originIndexId);
        speciesPropertyService.updatePropertyPassAudit(auditPropertyInfo);

        return Response.successResult();
    }

}
