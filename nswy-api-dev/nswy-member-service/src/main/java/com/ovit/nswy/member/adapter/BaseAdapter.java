package com.ovit.nswy.member.adapter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BaseAdapter {

    private final static Integer connTimeout = 2000;
    private final static Integer readTimeout = 2000;
    private final static String charset = "UTF-8";
    private static Logger logger = LogManager.getLogger(BaseAdapter.class);
    private static HttpClient client = null;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String doGet(String url, Map<String, Object> map) {
        String address = url;
        address += "?";
        int count = 1;
        for (Entry<String, Object> elem : map.entrySet()) {
            address += elem.getKey() + "=" + elem.getValue();
            if (count < map.keySet().size()) {
                address += "&";
            }
            count++;
        }
        logger.info("address：{}", address);
        HttpGet get = new HttpGet(address);
        String result = "";
        try {
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            get.setConfig(customReqConf.build());
            HttpResponse res = null;
            // 执行 Http 请求.
            res = client.execute(get);
            result = IOUtils.toString(res.getEntity().getContent(), charset);
        } catch (IOException ex) {
            logger.error(ex);
            result = ex.getMessage();
        } finally {
            get.releaseConnection();
        }
        logger.info("调用接口：{}，\r\n参数：{}, \r\n结果：{}", url, map, result);
        return result;
    }

    public String doPost(String url, Map<String, Object> map) {
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {
            // 设置参数
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            for (Entry<String, Object> elem : map.entrySet()) {
                list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
            result = ex.getMessage();
        } finally {
            httpPost.releaseConnection();
        }
        logger.info("调用接口：{}，\r\n参数：{}, \r\n结果：{}", url, map, result);
        return result;
    }
}
