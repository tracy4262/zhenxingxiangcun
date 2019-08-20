package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserInfo;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserInfoService {
    void updateUserInfo(UserInfo userInfo);

    void insert(UserInfo userInfo);

    UserInfo findUserInfo(String account);

    void insert(Map<String, Object> params, HttpSession session, LoginUser loginUser);
}