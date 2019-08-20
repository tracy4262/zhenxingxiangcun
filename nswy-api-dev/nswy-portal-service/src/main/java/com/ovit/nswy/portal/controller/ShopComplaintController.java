package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.ShopComplaint;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.response.ShopComplaintDTO;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import com.ovit.nswy.portal.service.ShopComplaintService;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@RestController
@RequestMapping("/shop/complaint")
@Api(tags = "/api/shop/complaint", description = "店铺投诉管理")
public class ShopComplaintController {

    @Autowired
    private ShopComplaintService shopComplaintService;

    @PostMapping("/list")
    @ApiOperation(value = "店铺投诉列表接口", response = ShopComplaintDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopComplaintDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@ApiParam(required = true, value = "店铺投诉参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        String code = MapUtils.getString(params, "orderCode");
        List<ShopComplaintDTO> result = shopComplaintService.list(code);
        if(result!=null&&result.size()>0){
            for(ShopComplaintDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/list/sell")
    @ApiOperation(value = "店铺投诉列表接口", response = ShopComplaintDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopComplaintDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listBySell(@ApiParam(required = true, value = "店铺投诉参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> query = MapUtils.getMap(params, "query");
        query.put("account", account);
        query.put("type", ShopAccountType.Sell.ordinal());//查看个人的卖家接口
        List<ShopComplaintDTO> result = shopComplaintService.list(query);
        if(result!=null&&result.size()>0){
            for(ShopComplaintDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/list/buy")
    @ApiOperation(value = "买家投诉列表接口", response = ShopComplaintDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopComplaintDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response listByBuy(@ApiParam(required = true, value = "店铺投诉参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> query = MapUtils.getMap(params, "query");
        query.put("account", account);
        query.put("type", ShopAccountType.Buy.ordinal());//查看个人的卖家接口
        List<ShopComplaintDTO> result = shopComplaintService.list(query);
        if(result!=null&&result.size()>0){
            for(ShopComplaintDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/add")
    @ApiOperation(value = "店铺投诉添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "店铺投诉添加参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopComplaint entity = JSON.parseObject(jsonString, ShopComplaint.class);
        entity.setBuyerAccount(account);
        Integer result = shopComplaintService.add(entity);
        return Response.successResult(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "店铺投诉修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "店铺投诉修改参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopComplaint entity = JSON.parseObject(jsonString, ShopComplaint.class);
        Integer result = shopComplaintService.update(entity);
        return Response.successResult(result);
    }
}
