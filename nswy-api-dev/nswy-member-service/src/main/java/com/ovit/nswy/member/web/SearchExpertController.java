package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.request.ExpertQuery;
import com.ovit.nswy.member.service.EmployService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 搜索   专家团队controller
 */
@RestController
@RequestMapping("/searchExpert")
public class SearchExpertController {
    @Autowired
    private EmployService employService;
    @Autowired
    private LoginUserService loginUserService;
    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 搜索专家团队
     *
     * @param request
     * @param expertQuery
     * @return
     */
    @RequestMapping(value = "/expert", method = RequestMethod.POST)
    public Result searchExpert(HttpServletRequest request, @RequestBody ExpertQuery expertQuery) {
        Result result = new Result();
        try {
            result.setData(employService.findExpert(expertQuery));
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/person/{currentpage}/{pageSize}", method = RequestMethod.POST)
    public Result searchPerson(@PathVariable("currentpage") Integer currentpage, @PathVariable("pageSize") Integer pageSize) {
        Result result = new Result();
        try {
            result.setData(loginUserService.findAllPersonAccount(currentpage, pageSize));
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

}
