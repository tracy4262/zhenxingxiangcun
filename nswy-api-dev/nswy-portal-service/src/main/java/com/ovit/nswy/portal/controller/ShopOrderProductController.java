package com.ovit.nswy.portal.controller;

import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.core.enums.NormalEnum.OrderOperateType;
import com.ovit.nswy.portal.core.enums.NormalEnum.ShopAccountType;
import com.ovit.nswy.portal.model.ShopOrderOperate;
import com.ovit.nswy.portal.model.ShopOrderProduct;
import com.ovit.nswy.portal.model.response.ShopOrderOperateDTO;
import com.ovit.nswy.portal.service.ShopOrderOperateService;
import com.ovit.nswy.portal.service.ShopOrderProductService;
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
@RequestMapping("/shop/order/product")
@Api(tags = "/api/shop/order/product", description = "订单商品管理")
public class ShopOrderProductController {

    @Autowired
    private ShopOrderProductService shopOrderProductService;

    @PostMapping("/list/code")
    @ApiOperation(value = "订单商品列表接口", response = ShopOrderProduct.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopOrderProduct.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response list(@ApiParam(required = true, value = "订单Id") @RequestBody Map<String,Object> params) {
        String code = MapUtils.getString(params, "code");
        List<ShopOrderProduct> result = shopOrderProductService.findByOrder(code);
        return Response.successResult(result);
    }

}
