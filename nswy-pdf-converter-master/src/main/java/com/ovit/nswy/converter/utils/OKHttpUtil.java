package com.ovit.nswy.converter.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-09 下午4:15
 * created by fuck~
 **/
public class OKHttpUtil {
    private static Logger logger = LogManager.getLogger(OKHttpUtil.class);

    //连接超时时间
    private static final int connectTimeout = 3;
    //读取超时时间
    private static final int readTimeout = 5;
    //写入超时时间
    private static final int writeTimeout = 5;

    private volatile static OKHttpUtil okHttpUtil = new OKHttpUtil();

    private OKHttpUtil() {

    }

    public static synchronized OKHttpUtil getInstance() {
        return okHttpUtil;
    }

    //初始化客户端
    private OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            //.dispatcher(dispatcher)
            .build();

    public byte[] excuteHttpGet(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            byte[] respContent = response.body().bytes();
            //logger.info("{} excuteHttpGet response:{}", response.code(), respContent);
            logger.info("url:{} excuteHttpGet response code:{}", url, response.code());
            return respContent;
        } catch (Exception e) {
            logger.info("excuteHttpGet exception error:{}", e);
        }
        return null;
    }

}
