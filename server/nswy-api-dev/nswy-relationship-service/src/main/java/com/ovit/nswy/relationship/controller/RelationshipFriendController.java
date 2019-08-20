package com.ovit.nswy.relationship.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.relationship.model.request.DeleteFriendQuery;
import com.ovit.nswy.relationship.model.request.GetFriendRequestQuery;
import com.ovit.nswy.relationship.model.request.RelationshipFriendQuery;
import com.ovit.nswy.relationship.model.response.GetFriendRequestDTO;
import com.ovit.nswy.relationship.service.RelationshipFriendService;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@RestController
@RequestMapping("/friend")
@Api(tags = "/api/friend",description="关系圈朋友管理")
public class RelationshipFriendController {

    @Autowired
    private RelationshipFriendService relationshipFriendService;

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="关系圈朋友添加和修改接口 []",tags = "添加无需传朋友Id,修改需要传朋友Id，好友的同意和忽略接口也是修改接口【忽略 1 同意 2】", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response saveOrUpdate(@ApiParam(required = true,value = "朋友添加和修改参数类") @RequestBody RelationshipFriendQuery relationshipFriendQuery ) throws ParamValidException {
        Integer result=relationshipFriendService.saveOrUpddate(relationshipFriendQuery);
        return Response.successResult(result);
    }


    @PostMapping("/listRelationshipFriendRequest")
    @ApiOperation(value="关系圈朋友好友请求列表接口 ", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GetFriendRequestDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response listRelationshipFriendRequest(@ApiParam(required = true,value = "朋友添加和修改参数类") @RequestBody GetFriendRequestQuery getFriendRequestQuery ){
        PageInfo<GetFriendRequestDTO> pageInfo=relationshipFriendService.listRelationshipFriendRequest(getFriendRequestQuery);
        return Response.convertResult(pageInfo.getList(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal());
    }


    @GetMapping("/getFriendRequest/{friendId}")
    @ApiOperation(value="获取关系圈朋友接口",tags = "通过朋友Id查询", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GetFriendRequestDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getFriendRequest(@ApiParam(required = true,value = "朋友Id")@PathVariable Integer friendId ){
        GetFriendRequestDTO result=relationshipFriendService.getFriendRequest(friendId);
        return Response.successResult(result);
    }

    @GetMapping("/deleteRelationshipFriend/{friendId}")
    @ApiOperation(value="解除关系圈朋友接口",tags = "通过朋友Id查询", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GetFriendRequestDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteRelationshipFriend(@ApiParam(required = true,value = "朋友Id")@PathVariable Integer friendId ){
        Integer result=relationshipFriendService.deleteRelationshipFriend(friendId);
        return Response.successResult(result);
    }

    @PostMapping("/deleteFriend")
    @ApiOperation(value="单个或批量解除关系圈朋友接口",tags = "通过朋友Id查询或id数组", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GetFriendRequestDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response deleteFriend(@ApiParam(required = true,value = "关系圈朋友删除参数类") @RequestBody DeleteFriendQuery deleteFriendQuery ){
        Integer result=relationshipFriendService.deleteFriend(deleteFriendQuery);
        return Response.successResult(result);
    }

    @PostMapping("/countFriendRequest")
    @ApiOperation(value="统计关系圈朋友好友请求接口", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GetFriendRequestDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response countFriendRequest(@ApiParam(required = true,value = "用户账号") String account ){
        Integer result=relationshipFriendService.countFriendRequest(account);
        return Response.successResult(result);
    }

    @GetMapping("/getExistsFriend/{account}/{fromAccount}")
    @ApiOperation(value="获取是否是好友接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public Response getExistsFriend(@ApiParam(required = true,value = "用户账号") @PathVariable String account,@ApiParam(required = true,value = "对方账号") @PathVariable  String fromAccount ){
        Boolean result=relationshipFriendService.getExistsFriend(account,fromAccount);
        return Response.successResult(result);
    }

    @RequestMapping("/getFriendByAccount")
    public Response getFriendByAccount(@RequestBody String account){
        List<String> friendList = relationshipFriendService.queryFriendByAccount(account);
        return Response.successResult(friendList);
    }

    /**
     * 解除好友关系
     */
    @RequestMapping("/deleteFriendByAccount")
    public Response deleteFriendByAccount(@RequestBody Map<String,Object> params){
        try {
            relationshipFriendService.deleteFriendByAccount(
                    MapUtils.getString(params,"account"),
                    MapUtils.getString(params,"fromAccount")
            );
            return Response.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.successResult(500,"删除失败");
        }
    }
}


