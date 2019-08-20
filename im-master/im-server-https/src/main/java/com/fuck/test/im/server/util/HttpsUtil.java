package com.fuck.test.im.server.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
* DESCRIPTION:
*
* @author zouyan
* @create 2018/1/11-上午9:51
* created by fuck~
**/
public class HttpsUtil {

    private static Logger logger = LogManager.getLogger(HttpsUtil.class);

    /**
     * http客户端
     */
    private static CloseableHttpClient httpClient;

    /**
     * http链接池管理器
     */
    private static PoolingHttpClientConnectionManager cm;
    /**
     * 连接池里的最大连接数
     */
    public static final int MAX_TOTAL_CONNECTIONS = 100;
    /**
     * 每个路由的默认最大连接数
     */
    public static final int MAX_ROUTE_CONNECTIONS = 50;
    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT = 50000;

    /**
     * 套接字超时时间
     */
    public static final int SOCKET_TIMEOUT = 50000;

    /**
     * 配置网络环境
     */
    private static RequestConfig requestConfig;

    /**
     * 初始化http连接池
     */
    static {
        try {
            // 创建安全套接字对象
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            // 获取分层tls/ssl连接
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
            httpClient = HttpClients.custom().setConnectionManager(cm).build();
            requestConfig = RequestConfig.custom().setSocketTimeout
                    (SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String url) throws Exception {
        String body = null;
        CloseableHttpResponse httpResponse = null;
        HttpGet httpGet = null;
        try {
            // Get请求
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            httpResponse = httpClient.execute(httpGet);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            // 获取返回数据
            HttpEntity entity = httpResponse.getEntity();
            body = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("httpUtil post error", e);
            throw new Exception(e);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return body;
    }

    /*public static void main(String[] args) {
        try {
            String result = HttpsUtil.get("https://192.168.7.226:20000/rooms");
            System.out.println(result);
            Map<String, Object> map = JSON.parseObject(result, Map.class);
            System.out.println(map);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String roomId = entry.getKey();
                System.out.println(roomId);
                String userIds = JSON.toJSONString(entry.getValue());
                System.out.println(userIds);
                List<String> list = JSON.parseArray(userIds, String.class);
                System.out.println(list);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }*/
}
