package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.model.UserWorkExperience;
import com.ovit.nswy.member.service.UserFullInfoService;
import com.ovit.nswy.member.service.UserWorkExperienceService;
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
@RequestMapping("/userWork")
public class UserWorkExperienceController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private UserWorkExperienceService userWorkExperienceService;

	@Autowired
	private UserFullInfoService userFullInfoService;

	/**
	 * 查询联系方式
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findUserWork", method = RequestMethod.GET)
	public Result findUserWork(HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		UserWorkExperience userWorkExperience = new UserWorkExperience();
		userWorkExperience.setAccount(loginUser.getLoginAccount());
		UserWorkExperience info = userWorkExperienceService.findUserWork(userWorkExperience);
		Result result = new Result();
		result.setData(info);
		return result;
	}
	/**
	 * 修改工作经历
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserWork", method = RequestMethod.POST)
	public Result updateUserWork(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		UserWorkExperience userWork = new UserWorkExperience();
		Result result = new Result();
		userWork.setAccount(loginUser.getLoginAccount());
		userWork.setOrganazationName(params.get("organazationName").toString());
		userWork.setStartDate(params.get("startDate").toString());
		userWork.setEndDate(params.get("endDate").toString());
		userWork.setContent(params.get("content").toString());
		UserFullInfo userFullInfo = new UserFullInfo();
		userFullInfo.setAccount(loginUser.getLoginAccount());
		userFullInfo.setWork(params.get("userWorkContent").toString());
		try {
			userFullInfoService.updateUserFullInfo(userFullInfo);
			userWorkExperienceService.updateUserWork(userWork);
			result.setData(1);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}
