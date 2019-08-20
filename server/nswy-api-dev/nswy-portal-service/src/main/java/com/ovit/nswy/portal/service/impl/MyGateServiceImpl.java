package com.ovit.nswy.portal.service.impl;

import com.ovit.nswy.portal.mapper.MyGateMapper;
import com.ovit.nswy.portal.service.MyGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我的门户
 *
 * @author haoWen
 * @create 2018-01-12 11:43
 **/
@Service
public class MyGateServiceImpl implements MyGateService {
    @Autowired
    private MyGateMapper myGateMapper;

    @Override
    public Map<String,Object> queryLabel(Map<String, Object> params) {
        return myGateMapper.queryLabel(params);
    }

    @Override
    public Map<String, Object> queryUserTypeByAccount(Map<String, Object> params) {
        return myGateMapper.queryUserTypeByAccount(params);
    }

    @Override
    public Map<String, Object> queryModularByAccount(Map<String, Object> params) {
        return myGateMapper.queryModularByAccount(params);
    }
}
