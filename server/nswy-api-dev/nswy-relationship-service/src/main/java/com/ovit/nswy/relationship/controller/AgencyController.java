package com.ovit.nswy.relationship.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.relationship.model.request.AgencyAuditListQuery;
import com.ovit.nswy.relationship.model.request.AgencyAuditQuery;
import com.ovit.nswy.relationship.model.request.AgencyListQuery;
import com.ovit.nswy.relationship.model.request.AgencyQuery;
import com.ovit.nswy.relationship.model.response.AgencyAuditDTO;
import com.ovit.nswy.relationship.model.response.AgencyDTO;
import com.ovit.nswy.relationship.service.AgencyAuditService;
import com.ovit.nswy.relationship.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@RestController
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private AgencyAuditService agencyAuditService;

    @PostMapping("/listAgency")
    public Response listAgency(@RequestBody AgencyListQuery agencyListQuery){
        PageInfo<AgencyDTO> pageInfo= agencyService.listAgency(agencyListQuery);
        return Response.convertResult(pageInfo.getList(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal());
    }

    @PostMapping("/saveAgency")
    public Response saveAgency(@RequestBody AgencyQuery agencyQuery){
        Object result=agencyService.saveAgency(agencyQuery);
        return Response.successResult(result);
    }


    @PostMapping("/updateAgency")
    public Response updateAgency(@RequestBody AgencyQuery agencyQuery){
        Object result=agencyService.updateAgency(agencyQuery);
        return Response.successResult(result);
    }


    @PostMapping("/listAgencyAudit")
    public Response listAgencyAudit(@RequestBody AgencyAuditListQuery agencyAuditListQuery){
        PageInfo<AgencyAuditDTO> pageInfo=agencyAuditService.listAgencyAudit(agencyAuditListQuery);
        return Response.convertResult(pageInfo.getList(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal());
    }

    @PostMapping("/saveAgencyAudit")
    public Response saveAgencyAudit(@RequestBody AgencyAuditQuery agencyQuery){
        Integer result=agencyAuditService.saveAgencyAudit(agencyQuery);
        return Response.successResult(result);
    }

    @GetMapping("/updateAuditStatus/{auditId}/{auditStatus}")
    public Response updateAuditStatus(@PathVariable Integer auditId, @PathVariable Integer auditStatus){
        Integer result=agencyAuditService.updateAuditStatus(auditId,auditStatus);
        return Response.successResult(result);
    }

}
