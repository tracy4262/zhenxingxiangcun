package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;
import com.ovit.nswy.portal.core.enums.NormalEnum.ShopAccountType;
import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopOrderCommentDTO;
import com.ovit.nswy.portal.service.ShopOrderCommentService;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@RestController
@RequestMapping("/shop/order/comment")
@Api(tags = "/api/shop/order/comment", description = "订单评论管理")
public class ShopOrderCommentController {

    @Autowired
    private ShopOrderCommentService shopOrderCommentService;

    @PostMapping("/list")
    @ApiOperation(value = "订单评论列表接口", response = ShopOrderCommentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderCommentDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@ApiParam(required = true, value = "订单评论参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> query = MapUtils.getMap(params, "query");
        Map<String, Object> page = MapUtils.getMap(params, "page");
        PageInfo<ShopOrderCommentDTO> result = shopOrderCommentService.page(query,page,account);
        return Response.successResult(result);
    }

    @PostMapping("/order")
    @ApiOperation(value = "订单评论列表接口", response = ShopOrderCommentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderCommentDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response order(@ApiParam(required = true, value = "订单评论参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> query = MapUtils.getMap(params, "query");
        List<ShopOrderCommentDTO> result = shopOrderCommentService.list(query);
        return Response.successResult(result);
    }



    @PostMapping("/buyer")
    @ApiOperation(value = "订单评论添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response addByBuy(@ApiParam(required = true, value = "订单评论添加参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        List<ShopOrderComment> entitys=  JSON.parseArray(jsonString,ShopOrderComment.class);
        Integer result = shopOrderCommentService.add(entitys,account,ShopAccountType.Buy.ordinal());
        return Response.successResult(result);
    }

    @PostMapping("/seller")
    @ApiOperation(value = "订单评论添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response addBySell(@ApiParam(required = true, value = "订单评论添加参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        List<ShopOrderComment> entitys=  JSON.parseArray(jsonString,ShopOrderComment.class);
        Integer result = shopOrderCommentService.add(entitys,account,ShopAccountType.Sell.ordinal());
        return Response.successResult(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "订单评论修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "订单评论修改参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopOrderComment entity=  JSON.parseObject(jsonString,ShopOrderComment.class);
        Integer result = shopOrderCommentService.update(entity);
        return Response.successResult(result);
    }
}
