package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.web.base.Result;
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
@RequestMapping("/findUser")
public class FindUserController {

    @Autowired
    private LoginUserService loginUserService;
    private Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping(value = "/showRecommended", method = RequestMethod.POST)
    public Result insert(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        logger.info("查询推荐的好友:{}", params);
        Result result = new Result();
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        String account = loginUser.getLoginAccount();
        params.put("login_account", account);
        // TODO 根据条件查找用户
        PageInfo<Map<String, Object>> list = loginUserService.showRecommended(params);
        result.setData(list);
        logger.info("查询推荐的好友结果:{}", result);
        return result;
    }

}
