package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.request.PolicyQuery;
import com.ovit.nswy.portal.model.response.PolicyDTO;
import com.ovit.nswy.portal.service.RegulationOfPolicyService;
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
 * Created by ${huipei.x} on 2018-1-12.
 */
@RestController
@RequestMapping("/regulation")
@Api(tags = "/regulation",description="门户政策管理")
public class RegulationOfPolicyController {

    @Autowired
    private RegulationOfPolicyService regulationOfPolicyService;

    @PostMapping("/listPolicy")
    @ApiOperation(value="政策列表接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PolicyDTO.class),
    })
    public Response listPolicy(@RequestBody PolicyQuery policyQuery){
        PageInfo<PolicyDTO> result= regulationOfPolicyService.listPolicy(policyQuery) ;
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }

}
