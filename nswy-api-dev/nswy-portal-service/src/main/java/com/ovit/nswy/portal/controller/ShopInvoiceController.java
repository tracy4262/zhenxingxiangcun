package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.ShopAddress;
import com.ovit.nswy.portal.model.ShopInvoicePersonal;
import com.ovit.nswy.portal.model.ShopInvoiceTax;
import com.ovit.nswy.portal.model.response.ShopInvoiceDTO;
import com.ovit.nswy.portal.service.ShopAddressService;
import com.ovit.nswy.portal.service.ShopInvoicePersonalService;
import com.ovit.nswy.portal.service.ShopInvoiceTaxService;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ovit.nswy.portal.core.enums.NormalEnum.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@RestController
@RequestMapping("/shop/invoice")
@Api(tags = "/api/shop/invoice", description = "发票管理")
public class ShopInvoiceController {

    @Autowired
    private ShopInvoiceTaxService shopInvoiceTaxService;

    @Autowired
    private ShopInvoicePersonalService shopInvoicePersonalService;


    @PostMapping("/add")
    @ApiOperation(value = "发票添加接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response add(@ApiParam(required = true, value = "发票添加参数类") @RequestBody Map<String, Object> params) {

        String account = MapUtils.getString(params, "account");
        Integer invoiceType = MapUtils.getInteger(params, "invoiceType");
        String jsonString = MapUtils.getString(params, "entity");
        if (invoiceType != null) {
            Integer result = -1;
            if (invoiceType == InvoiceType.Personal.GetCode()) {
                ShopInvoicePersonal invoicePersonal = JSON.parseObject(jsonString, ShopInvoicePersonal.class);

                if (invoicePersonal != null) {
                    invoicePersonal.setAccount(account);//当前登录人信息
                    result = shopInvoicePersonalService.add(invoicePersonal);
                }
            } else if (invoiceType == InvoiceType.Tax.GetCode()) {
                ShopInvoiceTax invoiceTax = JSON.parseObject(jsonString, ShopInvoiceTax.class);

                if (invoiceTax != null) {
                    invoiceTax.setAccount(account);//当前登录人信息
                    result = shopInvoiceTaxService.add(invoiceTax);
                }
            }
            return Response.successResult(result);
        } else {
            return Response.errorResult("发票类型不明确");
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "发票修改接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response update(@ApiParam(required = true, value = "发票修改参数类") @RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        Integer invoiceType = MapUtils.getInteger(params, "invoiceType");
        String jsonString = MapUtils.getString(params, "entity");
        if (invoiceType != null) {
            Integer result = -1;
            if (invoiceType == InvoiceType.Personal.GetCode()) {
                ShopInvoicePersonal invoicePersonal = JSON.parseObject(jsonString, ShopInvoicePersonal.class);

                if (invoicePersonal != null) {
                    invoicePersonal.setAccount(account);//当前登录人信息
                    result = shopInvoicePersonalService.update(invoicePersonal);
                }
            } else if (invoiceType == InvoiceType.Tax.GetCode()) {
                ShopInvoiceTax invoiceTax = JSON.parseObject(jsonString, ShopInvoiceTax.class);
                if (invoiceTax != null) {
                    invoiceTax.setAccount(account);//当前登录人信息
                    result = shopInvoiceTaxService.update(invoiceTax);
                }
            }
            return Response.successResult(result);
        } else {
            return Response.errorResult("发票类型不明确");
        }
    }


    @PostMapping("/default")
    @ApiOperation(value = "获取默认发票接口", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Response.class),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getDefault(@RequestBody Map<String, Object> params) {
        String account = MapUtils.getString(params, "account");
        ShopInvoicePersonal invoicePersonal = shopInvoicePersonalService.selectByAccount(account);
        ShopInvoiceTax invoiceTax = shopInvoiceTaxService.selectByAccount(account);
        ShopInvoiceDTO dto = new ShopInvoiceDTO();
        dto.setInvoicePersonal(invoicePersonal);
        dto.setInvoiceTax(invoiceTax);
        return Response.successResult(dto);
    }
}
