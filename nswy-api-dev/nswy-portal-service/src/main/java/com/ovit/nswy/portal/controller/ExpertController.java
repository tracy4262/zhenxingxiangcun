package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.portal.model.request.ExpertQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import com.ovit.nswy.portal.model.response.ExpertDTO;
import com.ovit.nswy.portal.service.ExpertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@RestController
@Slf4j
@RequestMapping("/expert")
@Api(tags = "/expert",description="门户专家团队管理")
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    @PostMapping("/listExpert")
    @ApiOperation(value="专家团队列表接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ExpertDTO.class),
          })
    public Response listExpert(@RequestBody ExpertQuery expertQuery){
        PageInfo<ExpertDTO> result= expertService.listExpert(expertQuery) ;
        return Response.convertResult(result.getList(),
                result.getPageNum(),
                result.getPageSize(),
                result.getTotal());
    }
}
