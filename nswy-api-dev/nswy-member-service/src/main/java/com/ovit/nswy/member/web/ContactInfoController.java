package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.ContactInfo;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserFullInfo;
import com.ovit.nswy.member.service.ContactInfoService;
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
@RequestMapping("/contactInfo")
public class ContactInfoController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ContactInfoService contactInfoService;
	@Autowired
	private UserFullInfoService userFullInfoService;

	/**
	 * 查询联系方式
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findContactInfo", method = RequestMethod.GET)
	public Result findContactInfo(HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setUserAccount(loginUser.getLoginAccount());
		ContactInfo info = contactInfoService.findContactInfo(contactInfo);
		Result result = new Result();
		result.setData(info);
		return result;
	}

	/**
	 * 修改联系方式
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateContactInfo", method = RequestMethod.POST)
	public Result updateContactInfo(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		ContactInfo contactInfo = new ContactInfo();
		Result result = new Result();
		try {
			contactInfo.setUserAccount(loginUser.getLoginAccount());
			contactInfo.setMobile(params.get("mobile").toString());
			contactInfo.setPhone(params.get("phone").toString());
			contactInfo.setQq(params.get("qq").toString());
			contactInfo.setPostcode(params.get("postcode").toString());
			contactInfo.setEmail(params.get("email").toString());
			contactInfo.setLocation(params.get("location").toString());
			contactInfo.setAddress(params.get("address").toString());
			contactInfo.setPosition(params.get("position").toString());
			UserFullInfo userFullInfo = new UserFullInfo();
			userFullInfo.setAccount(loginUser.getLoginAccount());
			userFullInfo.setContract(params.get("contactInfoContent").toString());
			userFullInfoService.updateUserFullInfo(userFullInfo);
			contactInfoService.updateContactInfo(contactInfo);
			result.setData(1);
		} catch (Exception e) {
			logger.error(e);
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}
