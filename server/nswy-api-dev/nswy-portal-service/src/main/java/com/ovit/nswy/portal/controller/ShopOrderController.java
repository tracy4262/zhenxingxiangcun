package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopComplaint;
import com.ovit.nswy.portal.model.ShopOrder;
import com.ovit.nswy.portal.model.response.ShopOrderAddDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDTO;
import com.ovit.nswy.portal.model.response.ShopOrderDetailDTO;
import com.ovit.nswy.portal.service.ShopOrderService;
import com.ovit.nswy.portal.util.BeanToMapUtil;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@RestController
@RequestMapping("/shop/order")
@Api(tags = "/api/shop/order", description = "订单管理")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @PostMapping("/list")
    @ApiOperation(value = "订单列表接口", response = ShopOrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> page = MapUtils.getMap(params, "page");
        Map<String, Object> query = MapUtils.getMap(params, "query");
       PageInfo<ShopOrderDTO> result = shopOrderService.list(query,page,account);
        return Response.successResult(result);
    }

    @PostMapping("/add")
    @ApiOperation(value = "订单添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "订单添加参数类") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String jsonString = MapUtils.getString(params, "entity");
            ShopOrderAddDTO entity=  JSON.parseObject(jsonString,ShopOrderAddDTO.class);
          //  ShopOrderDetailDTO entity = (ShopOrderDetailDTO)BeanToMapUtil.convertMap(ShopOrderDetailDTO.class, params);//转换位对象
            Integer result = shopOrderService.add(entity,account);
            return Response.successResult(result);
        }catch (Exception ex){
            return  Response.errorResult(ex.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "订单修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "订单修改参数类") @RequestBody Map<String, Object> params) {
        try {
          //  ShopOrderDetailDTO entity = (ShopOrderDetailDTO) BeanToMapUtil.convertMap(ShopOrderDetailDTO.class, params);//转换位对象
            String account = MapUtils.getString(params, "account");
            String jsonString = MapUtils.getString(params, "entity");
            ShopOrderDetailDTO entity=  JSON.parseObject(jsonString,ShopOrderDetailDTO.class);
            Integer result = shopOrderService.update(entity,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }


    @PostMapping("/detail")
    @ApiOperation(value = "获取订单详情接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response get(@ApiParam(required = true, value = "订单Id") @RequestBody Map<String,Object> params) {
        Integer id = MapUtils.getInteger(params, "id");
        ShopOrderDetailDTO result = shopOrderService.selectOne(id);
        return Response.successResult(result);
    }

    @PostMapping("/detail/code")
    @ApiOperation(value = "获取订单详情接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getByCode(@ApiParam(required = true, value = "订单Id") @RequestBody Map<String,Object> params) {
        String code = MapUtils.getString(params, "code");
        ShopOrderDetailDTO result = shopOrderService.selectOne(code);
        return Response.successResult(result);
    }


    @PostMapping("/sendout")
    @ApiOperation(value = "订单发货接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response sendOut (@ApiParam(required = true, value = "订单修改参数类") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String code = MapUtils.getString(params, "code");
            Integer result = shopOrderService.updateState(code,OrderStatusType.SendOut,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }

    @PostMapping("/takeover")
    @ApiOperation(value = "订单收货接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response TakeOver (@ApiParam(required = true, value = "订单参数") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String code = MapUtils.getString(params, "code");
            Integer result = shopOrderService.updateState(code,OrderStatusType.TakeOver,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }

    @PostMapping("/cancelover")
    @ApiOperation(value = "订单同意取消接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response CancelOver (@ApiParam(required = true, value = "订单参数") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String code = MapUtils.getString(params, "code");
            Integer result = shopOrderService.updateState(code,OrderStatusType.CancelOver,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }

    @PostMapping("/retreatover")
    @ApiOperation(value = "订单同意退货接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response RetreatOver (@ApiParam(required = true, value = "订单参数") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String code = MapUtils.getString(params, "code");
            Integer result = shopOrderService.updateState(code,OrderStatusType.RetreatOver,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }

    @PostMapping("/update/state")
    @ApiOperation(value = "订单状态修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateState(@ApiParam(required = true, value = "订单修改参数类") @RequestBody Map<String, Object> params) {
        try {
            String account = MapUtils.getString(params, "account");
            String jsonString = MapUtils.getString(params, "entity");
            ShopOrder entity=  JSON.parseObject(jsonString,ShopOrder.class);
          //  ShopOrderDetailDTO entity = (ShopOrderDetailDTO) BeanToMapUtil.convertMap(ShopOrderDetailDTO.class, params);//转换位对象
            Integer result = shopOrderService.update(null,account);
            return Response.successResult(result);
        } catch (Exception ex) {
            return Response.errorResult(ex.getMessage());
        }
    }

}
