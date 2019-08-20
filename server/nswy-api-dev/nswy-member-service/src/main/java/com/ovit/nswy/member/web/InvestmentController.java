package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.Investment;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.InvestmentService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/investment")
public class InvestmentController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private InvestmentService investmentService;

    @RequestMapping(value = "/saveInvestment", method = RequestMethod.POST)
    public Result saveInvestment(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Investment investment = new Investment();
        investment.setLoginAccount(loginUser.getLoginAccount());
        investment.setTitle(params.get("title").toString());
        investment.setContent(params.get("content").toString());
        Result result = new Result();
        try {
            investmentService.insert(investment);
            result.setData(1);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error(e);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/editInvestment", method = RequestMethod.POST)
    public Result editInvestment(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Investment investment = new Investment();
        investment.setLoginAccount(loginUser.getLoginAccount());
        investment.setId(Integer.parseInt(params.get("id").toString()));
        investment.setTitle(params.get("title").toString());
        investment.setContent(params.get("content").toString());
        Result result = new Result();
        try {
            investmentService.edit(investment);
            result.setData(1);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error(e);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/queryInvestment", method = RequestMethod.GET)
    public Result queryInvestment(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String loginAccount = loginUser.getLoginAccount();
        Result result = new Result();
        try {
            Investment investment = investmentService.query(loginAccount);
            result.setData(investment);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error(e);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}