package com.ovit.shop.pushTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.frame.common.Response;
import com.ovit.shop.core.enums.NormalEnum.*;
import com.ovit.shop.pushTemplate.service.ShopOrderOperateService;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangrongf
 */
@RestController
@RequestMapping("/shopOrderOperate")
public class ShopOrderOperateController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ShopOrderOperateService shopOrderOperateService;

    /*@PostMapping("/list")
    @ApiOperation(value = "订单操作列表接口", response = ShopOrderOperateDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderOperateDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@ApiParam(required = true, value = "订单操作参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Map<String, Object> query = MapUtils.getMap(params, "query");
        query.put("account",account);
        List<ShopOrderOperateDTO> result = shopOrderOperateService.list(query);
        if(result!=null&&result.size()>0){
            for(ShopOrderOperateDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/list/cancel")
    @ApiOperation(value = "订单操作列表接口", response = ShopOrderOperateDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderOperateDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response cancellList(@ApiParam(required = true, value = "订单操作参数类") @RequestBody Map<String, Object> params) {
        Map<String, Object> query = MapUtils.getMap(params, "query");
        query.put("type",OrderOperateType.Cancel.GetCode());//查看个人的卖家接口
        List<ShopOrderOperateDTO> result = shopOrderOperateService.list(query);
        if(result!=null&&result.size()>0){
            for(ShopOrderOperateDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/list/retreat")
    @ApiOperation(value = "订单操作列表接口", response = ShopOrderOperateDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderOperateDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response retreatlist(@ApiParam(required = true, value = "订单操作参数类") @RequestBody Map<String, Object> params) {
        Map<String, Object> query = MapUtils.getMap(params, "query");
        query.put("type",OrderOperateType.Retreat.GetCode());//查看个人的卖家接口
        List<ShopOrderOperateDTO> result = shopOrderOperateService.list(query);
        if(result!=null&&result.size()>0){
            for(ShopOrderOperateDTO item:result ){
                if(StringUtils.isNotBlank(item.getPicUrl())) {
                    item.setPicList(item.getPicUrl().split(","));
                }
            }
        }
        return Response.successResult(result);
    }

    @PostMapping("/add")
    @ApiOperation(value = "订单操作添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "订单操作添加参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopOrderOperate entity=  JSON.parseObject(jsonString,ShopOrderOperate.class);
        entity.setAccount(account);
        Integer result = shopOrderOperateService.add(entity);
        return Response.successResult(result);
    }
*/


    @RequestMapping(value = "/list/findById", method = RequestMethod.POST)
    public Result findById(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {

            Map<String,Object> map  = shopOrderOperateService.findById(params);
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("订单买家取消操作错误");
        }
        return result;
    }

    /**
     * 订单取消操作接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    public Result orderCancel(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

             shopOrderOperateService.add(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("订单买家取消操作错误");
        }
        return result;
    }




/*


    @PostMapping("/buyer/retreat")
    @ApiOperation(value = "订单买家退货操作接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response retreatByBuy(@ApiParam(required = true, value = "订单操作修改参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopOrderOperate entity=  JSON.parseObject(jsonString,ShopOrderOperate.class);
        entity.setFromAccount(ShopAccountType.Buy.ordinal());
        entity.setType(OrderOperateType.Retreat.GetCode());
        entity.setAccount(account);
        Integer result = shopOrderOperateService.add(entity);
        return Response.successResult(result);
    }



    @PostMapping("/update")
    @ApiOperation(value = "订单操作修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "订单操作修改参数类") @RequestBody Map<String,Object> params) {
        String account = MapUtils.getString(params, "account");
        String jsonString = MapUtils.getString(params, "entity");
        ShopOrderOperate entity=  JSON.parseObject(jsonString,ShopOrderOperate.class);
        Integer result = shopOrderOperateService.update(entity);
        return Response.successResult(result);
    }*/
}
