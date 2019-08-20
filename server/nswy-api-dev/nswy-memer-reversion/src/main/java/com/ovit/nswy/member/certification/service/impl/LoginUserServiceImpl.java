package com.ovit.nswy.member.certification.service.impl;

import com.ovit.nswy.member.certification.model.LoginUser;
import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.perfectInfo.mapper.LoginUserMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    private Logger logger = LogManager.getLogger(LoginUserServiceImpl.class);

    @Override
    public LoginUser findByLoginName(String loginName) {
        return loginUserMapper.getLoginName(loginName);
    }

    /**
     * 更新认证步骤
     * @param params
     */
    @Override
    public void updateIdentityFlag(Map<String, Object> params) {

        loginUserMapper.updateIdentityFlag(params);

    }

    @Override
    public void saveOrUpdateStep(Map<String, Object> params){

        Map<String,Object> stepMap = loginUserMapper.findStep(params);
        BigDecimal saveStep = new BigDecimal("0");
        if (MapUtils.isNotEmpty(stepMap)) {
            saveStep=new BigDecimal(MapUtils.getString(stepMap, "step"));

        }
        BigDecimal step =new BigDecimal(MapUtils.getString(params, "step"));
        if (step.compareTo(saveStep) ==1 ) {
            loginUserMapper.saveOrUpdateStep(params);
        }
    }


    @Override
    public Map<String,Object> findStep(Map<String, Object> params){

        return loginUserMapper.findStep(params);
    }

    @Override
    public Map<String,Object> findEnableStep(Map<String, Object> params){

        return loginUserMapper.findEnableStep(params);
    }



}