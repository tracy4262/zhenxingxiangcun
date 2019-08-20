package com.ovit.nswy.member.test.service.impl;

import com.ovit.nswy.member.test.mapper.TestMapper;
import com.ovit.nswy.member.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Map<String, Object> test() {
        return testMapper.test();
    }
}
