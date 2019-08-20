package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.service.WeChatLoginService;
import com.ovit.nswy.member.util.CheckoutUtil;
import org.springframework.stereotype.Service;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class WeChatLoginServiceImpl extends HttpServlet implements WeChatLoginService {

    /**
     *
     */
    private static final long serialVersionUID = -919165874448483894L;

    @Override
    public void ownerCheck(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
