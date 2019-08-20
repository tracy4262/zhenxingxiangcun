package com.ovit.nswy.member.util;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 统一API响应结果封装
 */
public class Result {

	private Logger logger = LogManager.getLogger(this.getClass());
	private Integer code;
	private String msg;
	private Object data;
	
	public Result(){
		this.code = ResultCode.SUCCESS.code;
	}

	public Result setCode(ResultCode resultCode) {
		this.code = resultCode.code;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public Result setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		if (null == code) {
			setCode(ResultCode.SUCCESS);
		}
		if(null == data){
			setData("");
		}
		String result = JSON.toJSONString(this);
		logger.info(result);
		return result;
	}
}
