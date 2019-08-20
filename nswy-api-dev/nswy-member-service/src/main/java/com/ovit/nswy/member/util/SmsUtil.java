package com.ovit.nswy.member.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ovit.nswy.member.web.base.Result;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Singleton
@Lazy(value = false)
public class SmsUtil {

    private static Logger logger = LogManager.getLogger(SmsUtil.class);

    @Value("${cloudmas.sms.host}")
    private String host;

    @Value("${cloudmas.sms.loginCode}")
    private String login_code;

    @Value("${cloudmas.sms.password}")
    private   String password;

    @Value("${cloudmas.sms.signNo}")
    private   String sign_no;

    @Value("${cloudmas.sms.codeTemplateName}")
    private String templateName;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static OkHttpClient client = new OkHttpClient();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信验证码
     * @param phone
     * @param yzm
     * @return
     */
    public Result sendSms(String phone, String yzm) {
        Result result = new Result();
            String[] mobiles = {phone};
            String[] templateParams = {yzm};
            sendTemplateSms12(mobiles, templateParams,templateName);//发送验证码
            result.setData(1);
        return result;
    }

    /**
     * 登录mas平台
     * @return
     */
    public Map<String, String> login() {
        logger.info("开始执行云信登录！！！");
        logger.info("登录信息，HOST：{}，账号：{}，密码：{}，签名：{}",host,login_code,password,sign_no);
        Request request = new Request.Builder()
                .url(host + "/v/1.0/login?login_code=" + login_code + "&passwd=" + password)
                .build();
        Map<String, String> result = new HashMap<>();
        try (Response response = client.newCall(request).execute()) {
            String loginBody = response.body().string();
            if (StringUtils.isNotEmpty(loginBody)) {
                logger.info("云信登录成功：{}", loginBody);
                Map<String, Map<String, String>> loginResult = JSON.parseObject(
                        loginBody,
                        new TypeReference<Map<String, Map<String, String>>>() {
                });
                String userId = loginResult.get("ovit_mas_ecuser_login_response").get("user_id");
                String accessToken = loginResult.get("ovit_mas_ecuser_login_response").get("access_token");
                result.put("userId", userId);
                result.put("accessToken", accessToken);
                String key = StringUtils.join("sms_login_info_",login_code);
                String value = JSON.toJSONString(result);
                redisTemplate.opsForValue().set(key, value);
                redisTemplate.expire(key,3600, TimeUnit.SECONDS);
                logger.info("设置redis缓存：key：{}，value：{}", key, value);
            } else {
                logger.error("云信登录失败：{}，错误码：{}",response,response.code());
            }
        } catch (Exception e) {
           logger.error("云信登录异常：{}",e);
        }
        return result;
    }

    /**
     * 发送模板短信方法
     * @param mobiles
     * @param templateParams
     * @param templateName
     * @return
     */
    public Map<String, Map<String, String>> sendTemplateSms12(String[] mobiles, String[] templateParams,String templateName) {
        Map<String, Map<String, String>> respMap = null;
        try {
            String redisKey = StringUtils.join("sms_login_info_",login_code);
            String loginInfo = redisTemplate.opsForValue().get(redisKey);
            Map<String, String> smsLoginInfo;
            if (StringUtils.isEmpty(loginInfo)) {
                smsLoginInfo = login();
            } else {
               smsLoginInfo = (Map<String, String>) JSONObject.parse(loginInfo);
            }
            logger.info("send sms params :{}",smsLoginInfo);
            String now = LocalDateTime.now().format(formatter);
            Map<String, String> params = new HashMap<>();
            params.put("user_id", smsLoginInfo.get("userId"));
            params.put("timestamp", now);
            params.put("sign_no", sign_no);
            params.put("mobiles", JSON.toJSONString(mobiles));
            params.put("templateName", templateName);
            params.put("templateParams", JSON.toJSONString(templateParams));
            OkHttpClient client = new OkHttpClient();
            logger.info("短信发送参数：{}" , params);
            String mac = macCa1(params, smsLoginInfo.get("accessToken"));
            params.put("mac", mac);
            // POST提交方式
            FormBody.Builder requestBody = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                requestBody.add(entry.getKey(), entry.getValue());
            }
            Request request = new Request.Builder()
                    .url(host + "/v/1.2/sendTemplateSms")
                    .post(requestBody.build())
                    .build();
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            respMap = JSON.parseObject(body, new TypeReference<Map<String, Map<String, String>>>() {});
            logger.info("发送模板短信1.2请求结果：{}" , body);
        } catch (IOException e) {
            logger.error("发送短信异常：{}",e);
        }
        return respMap;
    }

    /**
     * 计算MAC值
     * @param params
     * @param accessToken
     * @return
     * @throws IOException
     */
    private String macCa1(Map<String, String> params, String accessToken) throws IOException {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                query.append(key).append(value);
            }
        }
        // 第三步：使用MD5加密
        byte[] bytes;
        //accessToken是登录时发送给用户的Token，在这里添加
        query.append(accessToken);
        bytes = encryptMD5(query.toString());
        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    /**
     * MD5加密
     * @param inputStr
     * @return
     */
    private byte[] encryptMD5(String inputStr) {
        byte[] digest = null;
        try {
            MessageDigest mdi = MessageDigest.getInstance("MD5");
            mdi.update(inputStr.getBytes("UTF-8"));
            digest = mdi.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("无此加密方式，加密出错！");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码方式！");
            e.printStackTrace();
        }
        return digest;
    }

    /**
     * 二进制转大写十六进制
     * @param bytes
     * @return
     */
    private String byte2hex(byte[] bytes) {
        StringBuilder macString = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                macString.append("0");
            }
            macString.append(hex.toUpperCase());
        }
        return macString.toString();
    }
}
