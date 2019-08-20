package com.fuck.test.im.server.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-11 上午11:12
 * created by fuck~
 **/
public class OKHttpsUtil {

    private static Logger logger = LogManager.getLogger(OKHttpsUtil.class);

    private final OkHttpClient client;

    //连接超时时间
    private static final int connectTimeout = 3;
    //读取超时时间
    private static final int readTimeout = 5;
    //写入超时时间
    private static final int writeTimeout = 5;

    private volatile static OKHttpsUtil okHttpsUtil = new OKHttpsUtil();

    public OKHttpsUtil() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        client = new OkHttpClient().newBuilder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                //.dispatcher(dispatcher)
                .build();
    }

    public static synchronized OKHttpsUtil getInstance() {
        return okHttpsUtil;
    }

    private X509TrustManager trustManagerForCertificates() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
    }

    public String excuteHttpGet(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String respContent = response.body().string();
            //logger.info("{} excuteHttpGet response:{}", response.code(), respContent);
            logger.info("url:{} excuteHttpGet response code:{}", url, response.code());
            return respContent;
        } catch (Exception e) {
            logger.info("excuteHttpGet exception error:{}", e);
        }
        return null;
    }

    /*public static void main(String[] args) {
        String result = OKHttpsUtil.getInstance().excuteHttpGet("https://192.168.7.61:20000/rooms");
        System.out.println(result);
    }*/
}
