package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.AgencyAudit;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.AgencyAuditService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agency")
public class AgencyAuditController {
    @Autowired
    private AgencyAuditService service;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        //String account = "yryy";
        String user = params.get("account").toString();
        String name = params.get("name").toString();
        String type = params.get("type").toString();
        List<String> addrs = (List<String>) params.get("imgAddr");
        AgencyAudit agency = new AgencyAudit();
        agency.setAccount(user);
        agency.setAgencyer(account);
        agency.setName(name);
        agency.setPicAddr(addrs.toString().substring(1, addrs.toString().length() - 1));
        agency.setCreateTime(new Date());
        agency.setType(type);
        int num = service.insert(agency);
        if (num == 1) {
            result.setData("OK");
        } else {
            result.setData("error");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
