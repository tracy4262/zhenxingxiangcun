package com.ovit.nswy.relationship.controller;

import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;
import com.ovit.nswy.relationship.model.request.RelationshipGroupQuery;
import com.ovit.nswy.relationship.model.response.RelationshipDTO;
import com.ovit.nswy.relationship.model.response.RelationshipGroupDTO;
import com.ovit.nswy.relationship.service.RelationshipGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */

@RestController
@RequestMapping("/group")
@Api(tags = "/api/group",description="关系圈分组管理")
public class RelationshipGroupController {

    @Autowired
    private RelationshipGroupService relationshipGroupService;

    @GetMapping("/initRelationshipGroup/{account}")
    @ApiOperation(value="关系圈分组初始化接口", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response initRelationshipGroup(@PathVariable String account) throws ParamValidException {
        Integer result=relationshipGroupService.initRelationshipGroup(account);
        return Response.successResult(result);
    }

    @PostMapping("/listRelationshipGroup")
    @ApiOperation(value="关系圈分组列表接口", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RelationshipDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listRelationshipGroup(@ApiParam(required = true,value = "分组列表参数类") @RequestBody RelationshipGroupListQuery relationshipGroupListQuery) throws ParamValidException {
        RelationshipDTO result=relationshipGroupService.listRelationshipGroup(relationshipGroupListQuery);
        return Response.successResult(result);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="关系圈分组添加和修改接口",tags = "添加无需传分组Id,修改需要传分组Id", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveOrUpdate(@ApiParam(required = true,value = "分组添加和修改参数类") @RequestBody RelationshipGroupQuery relationshipGroupListQuery) throws ParamValidException {
        Integer result=relationshipGroupService.saveOrUpdate(relationshipGroupListQuery);
        return Response.successResult(result);
    }

    @GetMapping("/getRelationshipGroup/{gruopId}")
    @ApiOperation(value="关系圈分组获取接口",tags = "通过分组Id", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RelationshipGroupDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getRelationshipGroup(@ApiParam(required = true,value = "分组Id") @PathVariable Integer gruopId){
        RelationshipGroupDTO result=relationshipGroupService.getRelationshipGroup(gruopId);
        return Response.successResult(result);
    }

    @ApiOperation(value="关系圈分组删除接口",tags = "通过分组Id", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/deleteRelationshipGroup/{gruopId}")
    public Response deleteRelationshipGroup(@ApiParam(required = true,value = "分组Id")@PathVariable Integer gruopId) throws ParamValidException {
        Integer result=relationshipGroupService.deleteRelationshipGroup(gruopId);
        return Response.successResult(result);
    }


}
