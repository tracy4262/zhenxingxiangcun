package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.UserWebsite;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserWebsiteService {
    int deleteByAccount(String account);

    int insert(UserWebsite record);

    int updateByAccount(UserWebsite record);

    UserWebsite selectByAccount(String account);

    String chineseToPinyin(String chinese);

    void saveOrUpdateWebsite(Map<String, Object> params, LoginUser loginUser, HttpSession session);
}
