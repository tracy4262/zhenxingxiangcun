package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserCertification;
import com.ovit.nswy.member.service.UserCertificationService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/userCertification")
public class UserCertificationController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private UserCertificationService userCertificationService;

	@RequestMapping(value = "/findUserCertification", method = RequestMethod.GET)
	public Result findUserCertification(HttpServletRequest request) {
		UserCertification user = new UserCertification();
		String account =request.getParameter("uid");
		if(!StringUtils.hasText(account)){
			LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
			account = loginUser.getLoginAccount();
		}
		user.setAccount(account);
		Result result = new Result();
		UserCertification uc = userCertificationService.findUserCertification(user);
		result.setData(uc);
		return result;
	}

	/**
	 * 修改教育经历
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateUserCertification", method = RequestMethod.POST)
	public Result updateUserCertification(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		Result result = new Result();
		if (null == loginUser) {
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			return result;
		}
		UserCertification userCertification = new UserCertification();
		try {
			userCertification.setAccount(loginUser.getLoginAccount());
			userCertification.setSex(params.get("sex").toString());
			userCertification.setProfession(params.get("profession").toString());
			userCertification.setField(params.get("field").toString());
			userCertification.setTitle(params.get("title").toString());
			/*
			 * userCertification.setStatus(Integer.valueOf(params.get("title").
			 * toString()));
			 */
			/*
			 * UserFullInfo userFullInfo = new UserFullInfo();
			 * userFullInfo.setAccount(loginUser.getLoginAccount());
			 * userFullInfo.setEducation(params.get("userEducaitonContent").
			 * toString()); userFullInfoService.updateUserFullInfo(userFullInfo)
			 */;
			int amt = userCertificationService.updateUserCertificaiton(userCertification);
			if(amt == 0 ){
				amt = userCertificationService.save(userCertification);
			}
			result.setData(amt);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}
