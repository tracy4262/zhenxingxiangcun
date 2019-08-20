package com.ovit.nswy.member.test.controller;

import com.ovit.nswy.member.test.service.TestService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Result login() {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> testMap = testService.test();
            logger.info("test: {}", testMap);
            result.setData(testMap);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
