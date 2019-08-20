package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.member.model.request.MediaLibraryDetailListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryDetailQuery;
import com.ovit.nswy.member.model.response.MediaLibraryDetaiDTO;
import com.ovit.nswy.member.service.MediaLibraryDetailService;
import com.ovit.nswy.member.web.base.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@RestController
@RequestMapping("/media")
@Api(tags = "/api/media", description = "媒体库详情管理")
public class MediaLibraryDetailCintroller {

    @Autowired
    private MediaLibraryDetailService mediaLibraryDetailService;

    @PostMapping("/listMediaLibraryDetail")
    @ApiOperation(value = "媒体库详情列表接口", response = MediaLibraryDetaiDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaLibraryDetaiDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listMediaLibraryDetail(@ApiParam(required = true, value = "媒体库详情列表参数类") @RequestBody MediaLibraryDetailListQuery mediaLibraryDetailListQuery) {
        PageInfo<MediaLibraryDetaiDTO> result = mediaLibraryDetailService.listMediaLibraryDetail(mediaLibraryDetailListQuery);
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/findMediaBookList")
    @ApiOperation(value = "媒体库详情列表接口", response = MediaLibraryDetaiDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaLibraryDetaiDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response findMediaBookList(@ApiParam(required = true, value = "媒体库详情列表参数类") @RequestBody Map<String,Object> params) {
        PageInfo<Map<String,Object>> result = mediaLibraryDetailService.findMediaBookList(params);
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/saveMediaLibraryDetail")
    @ApiOperation(value = "媒体库详情添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response saveMediaLibraryDetail(@ApiParam(required = true, value = "媒体库详情添加参数类") @RequestBody MediaLibraryDetailQuery mediaLibraryDetailQuery) {
        Integer result = mediaLibraryDetailService.saveMediaLibraryDetail(mediaLibraryDetailQuery);
        return Response.successResult(result);
    }

    @PostMapping("/saveBookInfo")
    @ApiOperation(value = "媒体库详情添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response saveBookInfo(@ApiParam(required = true, value = "媒体库详情添加参数类") @RequestBody Map<String,Object> params) {

        mediaLibraryDetailService.saveBookInfo(params);
        return Response.successResult();
    }

    @PostMapping("/updateMediaLibraryDetail")
    @ApiOperation(value = "媒体库详情修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateMediaLibraryDetail(@ApiParam(required = true, value = "媒体库详情修改参数类") @RequestBody MediaLibraryDetailQuery mediaLibraryDetailQuery) {
        Integer result = mediaLibraryDetailService.updateMediaLibraryDetail(mediaLibraryDetailQuery);
        return Response.successResult(result);
    }

    @GetMapping("/getMediaDetail/{id}")
    @ApiOperation(value = "获取媒体库详情接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getMediaDetail(@ApiParam(required = true, value = "媒体库详情Id") @PathVariable Integer id) {
        MediaLibraryDetaiDTO result = mediaLibraryDetailService.gettMediaDetail(id);
        return Response.successResult(result);
    }

    @GetMapping("/deleteMediaLibraryDetail/{id}")
    @ApiOperation(value = "媒体库详情删除接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response deleteMediaLibraryDetail(@ApiParam(required = true, value = "媒体库详情Id") @PathVariable Integer id) {
        Integer result = mediaLibraryDetailService.deleteMediaLibraryDetail(id);
        return Response.successResult(result);
    }

    @GetMapping("/deleteMediaBook/{id}")
    @ApiOperation(value = "单个图书删除接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response deleteMediaBook(@ApiParam(required = true, value = "图书Id") @PathVariable Integer id) {
        mediaLibraryDetailService.deleteMediaBook(id);
        return Response.successResult();
    }
}
