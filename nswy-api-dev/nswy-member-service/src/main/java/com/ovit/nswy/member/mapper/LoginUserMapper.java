package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

import com.ovit.nswy.member.model.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginUserMapper {

    void deleteByPrimaryKey(Integer id);

    void updateType(Map<String,Object> map);

    int insert(LoginUser record);
    
    List<LoginUser> findAll();
    
    List<Map<String, Object>> findModeratorById(int id);
    
    LoginUser selectByPrimaryKey(Integer id);

    LoginUser findByLoginName(String loginName);
    
    List<LoginUser> findFriendByLoginName(LoginUser record);
    
    int updateByPrimaryKey(LoginUser record);

    void updateByAccount(LoginUser record);
    
    LoginUser findTourNumber(String userId);

	LoginUser login(Map<String, Object> map);

	void updateIdentityFlag(Map<String, Object> param);

    List<Map<String,Object>> showRecommended(Map<String, Object> param);

    List<LoginUser> findAllPersonAccount();


    int updatePassword(Map<String, Object> params);

    int insertProxyUser(LoginUser loginUser);

    List<Map<String, Object>> listLoginUser(Map<String, Object> params);

    Map<String,Object> findUseTemplate(String account);

    Map<String,Object> findYearInfo(String account);

}