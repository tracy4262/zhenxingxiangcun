package com.ovit.nswy.member.schedule;

import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.member.util.SmsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsLoginRunner implements ApplicationRunner {

    private static Logger logger = LogManager.getLogger(SmsLoginRunner.class);
    @Autowired
    private SmsUtil smsUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //启动程序时执行登录云mas操作，将token写入redis
        Map<String, String> result = smsUtil.login();
        //redisTemplate.opsForValue().set("sms_login_info", JSONObject.toJSONString(result), 60, TimeUnit.HOURS);
        logger.info("启动执行，云信登录:{}", JSONObject.toJSONString(result));
    }
}