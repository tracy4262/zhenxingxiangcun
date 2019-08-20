//package com.ovit.nswy.member.schedule;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ovit.nswy.member.util.SmsUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class SmsTokenSchedule {
//    private static Logger logger = LogManager.getLogger(SmsTokenSchedule.class);
//
//    @Autowired
//    private SmsUtil smsUtil;
//
//    //@Scheduled(cron="0 0/2  * * * ?")
//    public void executeTokenTask() {
//        // 间隔2分钟,执行云信登录操作
//        Map<String, String> result = smsUtil.login();
//        logger.info("ScheduledTest.executeUploadBackTask 用户登录信息:{}", JSONObject.toJSONString(result));
//    }
//
//}
