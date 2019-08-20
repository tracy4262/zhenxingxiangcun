package com.ovit.nswy.member.certification.service;


import com.ovit.nswy.member.certification.model.LoginUser;

import java.util.Map;

public interface LoginUserService {

    LoginUser findByLoginName(String loginName);

    /**
     * 更新认证步骤
     * @param params
     */
    void updateIdentityFlag(Map<String, Object> params);

    Map<String,Object> findStep(Map<String, Object> params);

    void saveOrUpdateStep(Map<String, Object> params);

    /**
     * 查询启用模版的认证步骤
     * @param params
     * @return
     */
    Map<String,Object> findEnableStep(Map<String, Object> params);

}