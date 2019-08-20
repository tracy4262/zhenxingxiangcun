package com.ovit.nswy.portal.controller;

import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.request.ContoctQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import com.ovit.nswy.portal.service.ContoctService;
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
 * Created by ${huipei.x} on 2018-1-14.
 */
@RestController
@RequestMapping("/contoct")
@Api(tags = "/contoct",description="门户联系管理")
public class ContoctController {

    @Autowired
    private ContoctService contoctService;

    @PostMapping("/getContoct")
    @ApiOperation(value="联系我们接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ContoctDTO.class),
    })
    public Response getContoct(@RequestBody ContoctQuery contoctQuery){
        ContoctDTO result= contoctService.getContoct(contoctQuery);
        return Response.successResult(result);
    }
}
