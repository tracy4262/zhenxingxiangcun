package com.ovit.nswy.portal.controller;

import com.github.xphsc.util.ObjectUtil;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.request.WebsiteQuery;
import com.ovit.nswy.portal.model.response.PolicyDTO;
import com.ovit.nswy.portal.model.response.WebsiteDTO;
import com.ovit.nswy.portal.service.WebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
@RestController
@RequestMapping("/website")
@Api(tags = "/website",description="门户设置管理")
public class WebsiteController {
    @Autowired
    private WebsiteService websiteService;

    @PostMapping("/getWebsite")
    @ApiOperation(value="门户设置接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WebsiteDTO.class),
    })
    public Response getWebsite(@RequestBody WebsiteQuery websiteQuery){
        Object result=websiteService.getWebsite(websiteQuery);
      return  Response.successResult(result);
    }
}
