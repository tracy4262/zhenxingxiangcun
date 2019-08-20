package com.ovit.nswy.member.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletResponseFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse rsp = (HttpServletResponse) response;
		rsp.setHeader("Access-Control-Allow-Origin", getAddress());
		// response.setHeader("Access-Control-Allow-Headers",
		// "X-Requested-With,token");
		// 加入支持自定义的header 加入元素 token 前端就可以发送自定义token过来
		rsp.setHeader("Access-Control-Allow-Headers", "Content-Type,token,X-Requested-With");
		rsp.setHeader("Access-Control-Allow-Credentials", "true");
		rsp.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		rsp.setHeader("X-Powered-By", " 3.2.1");
		rsp.setHeader("Content-Type", "application/json;charset=utf-8");
		chain.doFilter(request, response);
	}

	private String address;

	private String getAddress() {
		return address;
	}

	private void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void init(FilterConfig arg) throws ServletException {
		setAddress(arg.getInitParameter("address"));
	}
}