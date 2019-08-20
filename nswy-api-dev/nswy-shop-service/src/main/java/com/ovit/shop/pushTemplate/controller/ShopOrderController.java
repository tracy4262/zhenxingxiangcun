package com.ovit.shop.pushTemplate.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;

import com.ovit.shop.pushTemplate.service.ShopOrderService;
import com.ovit.shop.util.PageList;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
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
@RequestMapping("/shopOrder")
public class ShopOrderController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ShopOrderService shopOrderService;


    /**
     * 订单列表接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            Map<String, Object> page = MapUtils.getMap(params, "page");
            Map<String, Object> query = MapUtils.getMap(params, "query");
            PageList list = shopOrderService.list(query,page,account);
            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询订单错误");
        }
        return result;
    }

    /**
     * 获取订单详情接口
     * @param params
     * @return
     */
    /*@RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Result detail(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            Integer id = MapUtils.getInteger(params, "id");

            Map<String,Object> list = shopOrderService.selectOne(id);
            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询订单详情错误");
        }


        return result;
    }*/

    /**
     * 获取订单详情接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/detail/code", method = RequestMethod.POST)
    public Result getByCode( @RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            Map<String,Object> list = shopOrderService.selectOneDetail(params);
            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询订单详情错误");
        }

        return result;
    }

    /**
     * 添加订单
     * @param params
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            shopOrderService.add(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("添加订单错误");
        }
        return result;
    }

    /**
     * 订单状态修改
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    public Result updateState(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            shopOrderService.updateState(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("订单支付错误");
        }
        return result;
    }

    /*@PostMapping("/update")
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
*/
}
