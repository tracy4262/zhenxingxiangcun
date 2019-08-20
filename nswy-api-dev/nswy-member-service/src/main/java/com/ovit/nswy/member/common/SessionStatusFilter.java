package com.ovit.nswy.member.common;

import com.ovit.nswy.member.model.LoginUser;
import org.apache.log4j.MDC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 会话超时管理
 * <p>替代Spring Security默认的超时管理，
 * 解决ajax请求超时后不能正常跳转到登录页面的问题?
 * <strong>此过滤器在web.xml中的位置必须在Spring Security过滤器之前</strong>
 * 
 * @author Xie Shaohu
 */
public class SessionStatusFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(SessionStatusFilter.class);
	private static List<String> excludeList = new ArrayList<String>(5);
	
	/**
	 * @param exclude
	 */
	private static void setExcludeList(String exclude) {
		if (exclude != null) {
			String[] exStrs = exclude.split(";");
			if (exStrs != null){
				for (String exStr : exStrs){
					excludeList.add(exStr.trim());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setExcludeList(filterConfig.getInitParameter("exclude"));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 判断是否在排除列表
		String ctxPath = req.getContextPath();
		String requestUri = req.getRequestURI().replaceFirst(ctxPath, "");
		boolean matcher = false;
		for (String pattern : excludeList) {
			matcher = requestUri.startsWith(pattern);
			//logger.trace("pattern: {}, {}, {}", new Object[]{pattern.toString(), requestUri, matcher});
			if (matcher) break;
		}
		// 如果不在排除列表，则执行操作检查
		//logger.trace("Session timeout exclude url matcher result: {}", matcher);
		LoginUser loginUser = (LoginUser) req.getSession().getAttribute("currentUser");
		if (!matcher
				&& (req.getSession(false) == null || loginUser == null)) {
			response.setContentType("text/html;charset=UTF-8");
			// 异步Ajax请求超时后，发送timeout标识
			if ("XMLHttpRequest".equalsIgnoreCase(req.getHeader("x-requested-with"))) {
				PrintWriter out = response.getWriter();
				out.write("{timeout:true}");
				//out.flush();
			}
			//request.getRequestDispatcher("/app/timeout").forward(request, response);
			logger.info("IP:[{}]超时后尝试访问系统，已设置超时标识。", req.getRemoteAddr());
			resp.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT);
			resp.flushBuffer();
			return;
		}		
		// LOG4j保存当前用户系统，用于跟踪用户信息
		if (loginUser != null) {
			MDC.put("USER_ID", loginUser.getLoginAccount());
			MDC.put("USER_NAME", loginUser.getDisplayName());
			MDC.put("REMOTE_IP", req.getRemoteHost());
			MDC.put("USER_AGENT", req.getHeader("User-Agent"));
		} else {
			MDC.put("USER_ID", "");
			MDC.put("USER_NAME", "");
		}		
		chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		excludeList.clear();
	}
}
