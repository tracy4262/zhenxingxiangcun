package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.member.model.request.MediaLibraryListQuery;
import com.ovit.nswy.member.model.request.MediaLibraryQuery;
import com.ovit.nswy.member.model.response.MediaLibraryDTO;
import com.ovit.nswy.member.service.MediaLibraryService;
import com.ovit.nswy.member.service.ProductMediaLibraryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@RestController
@RequestMapping("/media")
@Api(tags = "/api/media", description = "媒体库管理")
public class MediaLibraryController {

    @Autowired
    private MediaLibraryService mediaLibraryService;

    @Autowired
    private ProductMediaLibraryService productMediaLibraryService;

    @PostMapping("/special-media-library-list")
    public Response specialMediaLibraryList(@RequestBody MediaLibraryListQuery mediaLibraryListQuery) {
        PageInfo<Map<String,Object>> result = productMediaLibraryService.specialMediaLibraryList(mediaLibraryListQuery);
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/listMediaLibrary")
    @ApiOperation(value = "媒体库列表接口", response = MediaLibraryDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaLibraryDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listMediaLibrary(@ApiParam(required = true, value = "媒体库列表参数类") @RequestBody MediaLibraryListQuery mediaLibraryListQuery) {
        PageInfo<MediaLibraryDTO> result = mediaLibraryService.listMediaLibrary(mediaLibraryListQuery);
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

    @PostMapping("/saveMediaLibrary")
    @ApiOperation(value = "媒体库添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response saveMediaLibrary(@ApiParam(required = true, value = "媒体库添加参数类") @RequestBody MediaLibraryQuery mediaLibraryQuery) throws ParamValidException {
        Integer result = mediaLibraryService.saveMediaLibrary(mediaLibraryQuery);
        return Response.successResult(result);
    }

    @PostMapping("/updateMediaLibrary")
    @ApiOperation(value = "媒体库修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateMediaLibrary(@ApiParam(required = true, value = "媒体库修改参数类") @RequestBody MediaLibraryQuery mediaLibraryQuery) {

        Integer result = mediaLibraryService.updateMediaLibrary(mediaLibraryQuery);
        return Response.successResult(result);
    }


    @GetMapping("/getMediaLibrary/{mediaId}")
    @ApiOperation(value = "通过媒体库ID获取媒体库接口", response = MediaLibraryDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaLibraryDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getMediaLibrary(@ApiParam(required = true, value = "媒体库Id") @PathVariable Integer mediaId) {
        MediaLibraryDTO result = mediaLibraryService.getMediaLibrary(mediaId);
        return Response.successResult(result);
    }

    @GetMapping("/deleteMediaLibrary/{mediaId}")
    @ApiOperation(value = "媒体库删除接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response deleteMediaLibrary(@ApiParam(required = true, value = "媒体库Id") @PathVariable Integer mediaId) {
        Integer result = mediaLibraryService.deleteMediaLibrary(mediaId);
        return Response.successResult(result);
    }
}
