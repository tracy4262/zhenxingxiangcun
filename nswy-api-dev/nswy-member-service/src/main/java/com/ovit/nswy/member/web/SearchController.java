package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@RequestMapping(value = "/findSearch", method = RequestMethod.GET)
	public Result findSearch(HttpServletRequest request) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
		Map<String, Object> map = new HashMap<String, Object>();
		return null;
	}
}
