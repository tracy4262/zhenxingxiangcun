package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserEducationExperience;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.service.UserEducationExperienceService;
import com.ovit.nswy.member.service.UserFullInfoService;
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
@RequestMapping("/userEducation")
public class UserEducationExperienceController {

    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserEducationExperienceService userEducationExperienceService;
    @Autowired
    private UserFullInfoService userFullInfoService;

    @RequestMapping(value = "/findUserEducation", method = RequestMethod.GET)
    public Result findUserEducation(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        UserEducationExperience userEducationExperience = new UserEducationExperience();
        userEducationExperience.setAccount(loginUser.getLoginAccount());
        UserEducationExperience user = userEducationExperienceService.findUserEducation(userEducationExperience);
        Result result = new Result();
        result.setData(user);
        return result;
    }

    /**
     * 修改教育经历
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserEducation", method = RequestMethod.POST)
    public Result updateUserEducation(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        UserEducationExperience userEducationExperience = new UserEducationExperience();
        Result result = new Result();
        try {
            userEducationExperience.setAccount(loginUser.getLoginAccount());
            userEducationExperience.setShcoolName(params.get("shcoolName").toString());
            userEducationExperience.setProfession(params.get("profession").toString());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            userEducationExperience.setEntranceDate(params.get("entranceDate").toString());
            userEducationExperience.setGraduationDate(params.get("graduationDate").toString());
            userEducationExperience.setStatus(Integer.valueOf(params.get("status").toString()));
            userEducationExperience.setDegree(params.get("degree").toString());
            UserFullInfo userFullInfo = new UserFullInfo();
            userFullInfo.setAccount(loginUser.getLoginAccount());
            userFullInfo.setEducation(params.get("userEducaitonContent").toString());
            userFullInfoService.updateUserFullInfo(userFullInfo);
            userEducationExperienceService.updateUserEducation(userEducationExperience);
            result.setData(1);
        } catch (Exception e) {
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
