package com.ovit.nswy.member.web;

import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proxy")
public class QueryProxyAuditProcessController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@RequestMapping(value = "/queryProcess", method = RequestMethod.GET)
	public Result process(HttpServletRequest request) {
		//TODO 查看代理审核进度列表
		return null;
	}
}
