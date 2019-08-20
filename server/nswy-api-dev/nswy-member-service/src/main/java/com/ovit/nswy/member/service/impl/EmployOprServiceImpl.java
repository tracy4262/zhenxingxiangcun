package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.EmployOprMapper;
import com.ovit.nswy.member.service.EmployOprService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 聘请管理操作日志
 *
 * @author haoWen
 * @create 2018-01-27 14:26
 **/
@Service
public class EmployOprServiceImpl implements EmployOprService{

    @Autowired
    private EmployOprMapper employOprMapper;

    @Override
    public int saveEmployOprInfo(Map<String, Object> params) {
        return employOprMapper.saveEmployOprInfo(params);
    }
}
