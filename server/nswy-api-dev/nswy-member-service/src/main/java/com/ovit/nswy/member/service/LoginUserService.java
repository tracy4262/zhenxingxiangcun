package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.LoginUser;

import java.util.List;
import java.util.Map;

public interface LoginUserService {
    PageInfo<LoginUser> query(int pageNum, int pageSize, LoginUser record);

    void updateType(Map<String,Object> map);

    PageInfo<LoginUser> query(int pageNum, int pageSize);

    void deleteByPrimaryKey(Integer id);

    void insert(LoginUser record);

    LoginUser selectByPrimaryKey(Integer id);

    List<Map<String, Object>> findModeratorById(int id);

    LoginUser findByLoginName(String loginName);

    List<LoginUser> findFriendByLoginName(LoginUser record);

    List<LoginUser> findAll();

    void updateByPrimaryKey(LoginUser record);

    void updateByAccount(LoginUser record);

    LoginUser login(String username, String pwd);

    LoginUser findTourNumber();

    void updateIdentityFlag(String account, String flag);

    PageInfo<Map<String, Object>> showRecommended(Map<String, Object> param);

    PageInfo<LoginUser> findAllPersonAccount(Integer currentPage, Integer pageSize);

    int updatePassword(Map<String, Object> params);

    int insertProxyUser(LoginUser loginUser);

    void insertCertification(Map<String, Object> params,String username,LoginUser user );

    String generateNswyId();

    Map<String,Object> findUseTemplate(String account);

    Map<String,Object> findYearInfo(String account);

}