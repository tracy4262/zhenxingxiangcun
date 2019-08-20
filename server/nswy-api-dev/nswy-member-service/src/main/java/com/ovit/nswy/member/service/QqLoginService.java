package com.ovit.nswy.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface QqLoginService {
    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void afterLogin(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
