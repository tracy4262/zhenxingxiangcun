package com.ovit.shop.test.service.impl;

import com.ovit.shop.test.mapper.TestMapper;
import com.ovit.shop.test.service.TestService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TestServiceImpl implements TestService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TestMapper testMapper;

    @Override
    public Map<String, Object> test() {
        return testMapper.test();
    }

}
