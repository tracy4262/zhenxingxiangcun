package com.ovit.nswy.frame.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**调用REST API 工具类
 * Created by ${huipei.x} on 2017/2/3
 */
public class RestClient {


    private static class SingletonRestTemplate {
        static final RestTemplate INSTANCE = new RestTemplate();
    }

    public static RestTemplate getInstance() {
        return SingletonRestTemplate.INSTANCE;
    }

    /**
     *
     * 发送post请求
     * @param url 请求URL地址
     * @param data json数据
     */
    public static String post(String url, String data, String token)
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Accpet-Encoding", "gzip");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Token", token);
        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
        return RestClient.getInstance().postForObject(url, formEntity, String.class);
    }

    /**
     *
     * 发送post请求
     * @param url 请求URL地址
     * @param data json数据
     */
    public static String post(String url, String data, String token,String platformCode,String tenantCode)
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Accpet-Encoding", "gzip");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Token", token);
        headers.add("PlatformCode", platformCode);
        headers.add("TenantCode", tenantCode);
        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
        return RestClient.getInstance().postForObject(url, formEntity, String.class);
    }

    /**
     * get根据url获取对象
     */
    public static String get(String url) {
        return RestClient.getInstance().getForObject(url, String.class,
                new Object[]{});
    }


    public static String get(String url,String data) {
        return RestClient.getInstance().getForObject(url, String.class,
                data);
    }
    /**
     * getById根据ID获取对象
     */
    public <T> T getById(String url, String id) {
        T clazz=null;
        clazz= (T) RestClient.getInstance().getForObject(url, clazz.getClass(), id);
        return clazz;
    }


    /**
     * post提交对象
     */
    public static String post(String url, String data) {
        return RestClient.getInstance().postForObject(url, null,
                String.class, data);
    }


    public static <T> ResponseEntity<T> postForEntity(String url,Map<String, Object> param) {
        T clazz=null;
        ResponseEntity<T> entity = (ResponseEntity<T>) RestClient.getInstance().postForEntity(url,null,clazz.getClass(),param);
        return entity;
    }

    public static <T> ResponseEntity<T> getForEntity(String url,Map<String, Object> param) {
        T clazz=null;
        ResponseEntity<T> entity = (ResponseEntity<T>) RestClient.getInstance().getForEntity(url, clazz.getClass(), param);;
        return entity;
    }

    /**
     * put修改对象
     */
    public static void put(String url, String data) {
        RestClient.getInstance().put(url, null, data);
    }

    public static void put(String url,Map<String, Object> param) {
         RestClient.getInstance().put(url, null, param);
    }

    public static void put(String url,String jsondata ,Map<String, Object> param) {
        RestClient.getInstance().put(url, jsondata, param);
    }

    /**
     * delete根据ID删除对象
     */
    public static void delete(String url, String id) {
        RestClient.getInstance().delete(url, id);
    }


}
