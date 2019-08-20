package com.ovit.nswy.member.service;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WeChatLoginService {

    void ownerCheck(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
