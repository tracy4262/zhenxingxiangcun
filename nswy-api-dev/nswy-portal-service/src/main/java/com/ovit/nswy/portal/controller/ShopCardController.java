package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopCard;
import com.ovit.nswy.portal.model.ShopOrderComment;
import com.ovit.nswy.portal.model.response.ShopCardDTO;
import com.ovit.nswy.portal.model.response.ShopProductDTO;
import com.ovit.nswy.portal.service.ShopCardService;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxy
 */
@RestController
@RequestMapping("/shop/card")
@Api(tags = "/api/shop/card", description = "购物车管理")
public class ShopCardController {

    @Autowired
    private ShopCardService shopCardService;

    @PostMapping("/list")
    @ApiOperation(value = "购物车列表接口", response = ShopCardDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopCardDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        List<ShopCardDTO> result = shopCardService.list(account);
        return Response.successResult(result);
    }

    @PostMapping("/product")
    @ApiOperation(value = "购物车商品接口", response = ShopProductDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopProductDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getProduct(@RequestBody Map<String,Object> params) {
        Integer productId = MapUtils.getInteger(params, "productId");
        Integer productNum = MapUtils.getInteger(params, "productNum");
        ShopProductDTO entity = shopCardService.findProduct(productId);
        if(entity!=null){
            entity.setNum(productNum);
        }
        return Response.successResult(entity);
    }

    @PostMapping("/add")
    @ApiOperation(value = "购物车添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "购物车添加参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopCard entity=  JSON.parseObject(jsonString,ShopCard.class);
        entity.setAccount(account);
        Integer result = shopCardService.add(entity);
        if(result>=0) {
            return Response.successResult(result);
        }else{
            if(result==-1) {
                return Response.errorResult("本次购买量超过商品单次最大购买量，请调整购买数量");
            }else{
                return Response.errorResult("添加商品错误");
            }
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "购物车修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "购物车修改参数类") @RequestBody Map<String,Object> params) {
        String jsonString = MapUtils.getString(params, "entity");
        List<ShopCard> entitys=  JSON.parseArray(jsonString,ShopCard.class);

      //  ShopCard entity=  JSON.parseObject(jsonString,ShopCard.class);
        Integer result = shopCardService.update(entitys);
        return Response.successResult(result);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "购物车删除接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response delete(@ApiParam(required = true, value = "购物车Id") @RequestBody Map<String,Object> params) {
        String ids = MapUtils.getString(params, "delIds");
        List<Object> listIds =  Arrays.asList(JSON.parseArray(ids).toArray());
        Integer result = shopCardService.delete(listIds);
        return Response.successResult(result);
    }


    @PostMapping("/clear")
    @ApiOperation(value = "购物车清空接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response clear(@RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        Integer result = shopCardService.clear(account);
        return Response.successResult(result);
    }
}
