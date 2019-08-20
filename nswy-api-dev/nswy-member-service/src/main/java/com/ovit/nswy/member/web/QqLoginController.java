package com.ovit.nswy.member.web;

import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qqLogin")
public class QqLoginController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Result login() throws Exception {
		return null;
	}
}
