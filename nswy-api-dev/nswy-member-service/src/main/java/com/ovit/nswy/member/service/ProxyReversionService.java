package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProxyReversionService {

    /**
     * 查询用户代理列表
     * @param params
     */
    Map<String,Object> proxyList(Map<String,Object> params);

    /**
     * 用户注册一个账号
     * @param params
     */
    void insertProxy(Map<String,Object> params);


    /**
     * 查询账号
     * @param params
     * @return
     */
    PageInfo<List<Map<String,Object>>> queryAccount(Map<String, Object> params);

    /**
     * 代理或取消代理
     * @param params
     */
    void proxyOrCancle(Map<String,Object> params);

    /**
     * 查询代理模板或取消代理模板
     * @param params
     */
    Map<String,Object> proxyTemplate(Map<String,Object> params);

    /**
     * 暂不代理
     * @param params
     */
    void noProxy(Map<String,Object> params);

    /**
     * 查询待审数据
     * @param params
     * @return
     */
    PageInfo<List<Map<String,Object>>> waitProxy(Map<String, Object> params);

    /**
     * 获取实名认证第一条资质认证的会员名称
     * @param params
     * @return
     */
    List<Map<String,Object>> findMemberName(Map<String, Object> params);

    /**
     * 我要代理
     * @param params
     */
    void toProxy(Map<String,Object> params);

}