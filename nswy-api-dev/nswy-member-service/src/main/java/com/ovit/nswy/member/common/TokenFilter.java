package com.ovit.nswy.member.common;

import com.ovit.nswy.member.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

//@Component
public class TokenFilter implements Filter{
    private static Logger logger = LogManager.getLogger(TokenFilter.class);
    @Value("${jwt.secret}")
    private String secret;

    private List<Pattern> excludeList = new ArrayList<Pattern>(5);

    private void setIncludeList(String include) {
        if (include != null) {
            String[] exStrs = include.split(";");
            if (exStrs != null) {
                for (String exStr : exStrs) {
                    excludeList.add(Pattern.compile(exStr.trim()));
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        setIncludeList("/login/login");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String ctxPath = req.getContextPath();
        String requestUri = req.getRequestURI().replaceFirst(ctxPath, "");
        boolean matcher = false;
        for (Pattern pattern : excludeList) {
            matcher = pattern.matcher(requestUri).matches();
            if (!matcher) {
                break;
            }
        }
        if(!matcher){
            // TODO 获取token 解析 判断是否超时
            String token = req.getHeader("token");
            logger.info("token-------->"+token);
            if(StringUtils.isEmpty(token)){
                //
                logger.info("IP:[{}]未授权。", req.getRemoteAddr());
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                res.flushBuffer();
                return;
            }
            Claims claims = JwtUtil.getClaimsFromToken(token, secret);
            Date date = claims.getExpiration();
            // 超时
            Calendar calendar = Calendar.getInstance();
            if(calendar.after(date)){
                // 超时
                logger.info("IP:[{}]超时后尝试访问系统，已设置超时标识。", req.getRemoteAddr());
                res.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT);
                res.flushBuffer();
                return;
            }else{
                String account = claims.get("account").toString();
            }
             // TODO 判断账号是否合法
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

}
