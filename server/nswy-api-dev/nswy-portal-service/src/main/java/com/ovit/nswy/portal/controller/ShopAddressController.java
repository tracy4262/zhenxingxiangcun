package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.service.ShopAddressService;
import com.ovit.nswy.portal.util.BeanToMapUtil;
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
@RequestMapping("/shop/address")
@Api(tags = "/api/shop/address", description = "地址管理")
public class ShopAddressController {

    @Autowired
    private ShopAddressService shopAddressService;

    @PostMapping("/list")
    @ApiOperation(value = "地址列表接口", response = ShopAddress.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopAddress.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        Integer order = (MapUtils.getString(params, "order")==null)?0:1;
        List<ShopAddress> result = shopAddressService.list(account,order);
        return Response.successResult(result);
    }

    @PostMapping("/add")
    @ApiOperation(value = "地址添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "地址添加参数类") @RequestBody Map<String,Object> params) {

        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopAddress entity=  JSON.parseObject(jsonString,ShopAddress.class);
        entity.setAccount(account);
        Integer result = shopAddressService.add(entity);
        return Response.successResult(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "地址修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "地址修改参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopAddress entity=  JSON.parseObject(jsonString,ShopAddress.class);
        Integer result = shopAddressService.update(entity);
        return Response.successResult(result);
    }

    @PostMapping("/update/default")
    @ApiOperation(value = "地址默认接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateDefault(@ApiParam(required = true, value = "地址修改参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        Integer id = MapUtils.getInteger(params, "id");
       // String jsonString = MapUtils.getString(params, "entity");
        ShopAddress entity= new ShopAddress();// JSON.parseObject(jsonString,ShopAddress.class);
        entity.setAccount(account);
        entity.setIsDefault(true);
        entity.setId(id);
        Integer result = shopAddressService.updateDefault(entity);
        return Response.successResult(result);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "获取地址详情接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response get(@RequestBody Map<String,Object> params) {
        Integer id = MapUtils.getInteger(params, "id");
        ShopAddress result = shopAddressService.selectOne(id);
        return Response.successResult(result);
    }

    @PostMapping("/detail/default")
    @ApiOperation(value = "获取默认地址接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getDefault(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        ShopAddress result = shopAddressService.selectDefault(account);
        return Response.successResult(result);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "地址删除接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response delete(@RequestBody Map<String,Object> params) {
        Integer id = MapUtils.getInteger(params, "id");
        Integer result = shopAddressService.delete(id);
        return Response.successResult(result);
    }
}
