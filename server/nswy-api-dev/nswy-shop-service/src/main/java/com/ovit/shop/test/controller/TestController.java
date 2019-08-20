package com.ovit.shop.test.controller;

import com.ovit.shop.test.service.TestService;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Result test() {
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
