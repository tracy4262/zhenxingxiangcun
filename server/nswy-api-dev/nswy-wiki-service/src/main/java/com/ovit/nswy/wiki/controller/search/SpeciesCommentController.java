package com.ovit.nswy.wiki.controller.search;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.annotation.BaseException;
import com.ovit.nswy.wiki.model.request.SpeciesCommentAddQuery;
import com.ovit.nswy.wiki.model.request.SpeciesCommentListQuery;
import com.ovit.nswy.wiki.search.service.SpeciesCommentService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@RestController
@BaseException
@Slf4j
@RequestMapping("/api/wiki")
@Api(tags = "/api/wiki",description="物种评论管理")
public class SpeciesCommentController {

    @Autowired
    private SpeciesCommentService speciesCommentService;

    @PostMapping("/listSpeciesComment")
    @ApiOperation(value="物种评论列表接口", response = SpeciesCommentListQuery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listSpeciesComment( @ApiParam(required = true,value = "物种查询参数类") @RequestBody SpeciesCommentListQuery SpeciesCommentListQuery){
        Page<Map<String, Object>> mapPage= speciesCommentService.listSpeciesComment(SpeciesCommentListQuery);
        return Response.convertResult(mapPage.getRecordList(),
                mapPage.getCurrentPage(),
                mapPage.getPageSize(),
                mapPage.getRecordCount());
    }


    @PostMapping("/saveSpeciesComment")
    @ApiOperation(value="新增物种评论接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveSpeciesComment(@ApiParam(required = true,value = "添加参数类") @RequestBody SpeciesCommentAddQuery speciesCommentAddQuery){
         speciesCommentService.saveSpeciesComment(speciesCommentAddQuery);
        return Response.successResult();
    }


    @GetMapping("/getSpeciesComment/{indexid}")
    @ApiOperation(value="获取物种评论接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getSpeciesComment(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        Map<String, Object> result=speciesCommentService.getSpeciesComment(indexid);
        return Response.successResult(result);
    }


    @GetMapping("/deleteSpeciesComment/{indexid}")
    @ApiOperation(value="物理删物物种评论接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteSpeciesComment(@ApiParam(required = true,value = "索引Id")@PathVariable String indexid){
        speciesCommentService.deleteSpeciesComment(indexid);
        return Response.successResult();
    }


    @PostMapping("/updateSpeciesComment")
    @ApiOperation(value="修改物种评论接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response updateSpeciesComment(@RequestBody Map params){
        speciesCommentService.updateSpeciesComment(params);
        return Response.successResult();
    }
}
