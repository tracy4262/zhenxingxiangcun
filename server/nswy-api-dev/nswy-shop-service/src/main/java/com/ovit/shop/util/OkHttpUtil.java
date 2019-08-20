package com.ovit.shop.util;


import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    private static Logger logger = LogManager.getLogger(OkHttpUtil.class);

    //连接超时时间
    private static final int connectTimeout = 3;
    //读取超时时间
    private static final int readTimeout = 5;
    //写入超时时间
    private static final int writeTimeout = 5;
    //contentType
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private volatile static OkHttpUtil okHttpUtil = new OkHttpUtil();

    private OkHttpUtil() {

    }

    public static synchronized OkHttpUtil getInstance() {
        return okHttpUtil;
    }


    private Headers headers = Headers.of(new HashMap<String, String>() {
        {
            put("belong", "firPlat");
            put("reqType", "300001");
        }
    });

    //初始化客户端
    private OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            //.dispatcher(dispatcher)
            .build();

    //统一创建request 统一进行内容加密
    private Request buildRequest(String url, String content, HttpMethod method){
        String base64Content = new String(Base64.encodeBase64(content.getBytes()));
        RequestBody body = RequestBody.create(JSON, base64Content);
        //Request.Builder builder = new Request.Builder().url(url).post(body).headers(headers);
        Request.Builder builder = new Request.Builder().url(url).get();
        return builder.build();
    }

    //执行异步请求
    public void excuteAsyncHttp(String url, final String content, HttpMethod method) {
        Request request = buildRequest(url, content, method);
        Call call  = client.newCall(request);
        final Map<String, Object> map = new HashMap<>();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    //logger.info("excuteHttp response:{}", response.body().string());
                } catch (Exception e) {
                    logger.info("excuteHttp response error:{}", e);
                }
            }
            @Override
            public void onFailure(Call call, IOException ex) {
                logger.error("excuteHttp exception:{}", ex);
                logger.error("excuteHttp exception error content:{}", content);
            }
        });
    }

    public String excuteHttp(String url, final String content, HttpMethod method) {
        Request request = buildRequest(url, content, method);
        try (Response response = client.newCall(request).execute()) {
            //logger.info("{} excuteHttp response:{}", response.code(), response.body().string());
            return response.body().string();
        } catch (Exception e) {
            logger.info("excuteHttp exception error:{}", e);
            logger.error("excuteHttp exception error content:{}", content);
            return "error";
        }
    }

}



