package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserFarmInfo;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.service.UserFarmInfoService;
import com.ovit.nswy.member.service.UserFullInfoService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
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
@RequestMapping("/userFarmInfo")
public class UserFarmInfoController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserFarmInfoService userFarmInfoService;
    @Autowired
    private UserFullInfoService userFullInfoService;

    @RequestMapping(value = "/findUserFarmInfo", method = RequestMethod.GET)
    public Result findUserFarmInfo(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        UserFarmInfo userFarmInfo = new UserFarmInfo();
        userFarmInfo.setAccount(loginUser.getLoginAccount());
        UserFarmInfo info = userFarmInfoService.findUserFarmInfo(userFarmInfo);
        Result result = new Result();
        result.setData(info);
        return result;
    }

    /**
     * 修改种养情况
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserFarmInfo", method = RequestMethod.POST)
    public Result updateUserFarmInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        UserFarmInfo userFarmInfo = new UserFarmInfo();
        Result result = new Result();
        try {
            userFarmInfo.setAccount(loginUser.getLoginAccount());
            userFarmInfo.setFarmlan(MapUtils.getString(params,"farmlan"));
            userFarmInfo.setSpecies(params.get("species").toString());
            userFarmInfoService.updateUserFarmInfo(userFarmInfo);
            UserFullInfo userFullInfo = new UserFullInfo();
            userFullInfo.setAccount(loginUser.getLoginAccount());
            userFullInfo.setFarmlan(params.get("userFarmInfoContent").toString());
            userFullInfoService.updateUserFullInfo(userFullInfo);
            result.setData(1);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
