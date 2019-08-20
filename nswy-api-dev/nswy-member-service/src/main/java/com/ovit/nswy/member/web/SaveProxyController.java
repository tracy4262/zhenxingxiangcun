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
public class SaveProxyController {

	/**
	 * 
	 */
	private Logger logger = LogManager.getLogger(this.getClass());

	@RequestMapping(value = "/saveProxy", method = RequestMethod.POST)
	public Result save(HttpServletRequest request) {
		// param:{ page pagesize corpName}
		//PageInfo<T> pageInfo =
		//Result result = new Result();

		//TODO 保存被代理机构信息
		return null;
	}
}
