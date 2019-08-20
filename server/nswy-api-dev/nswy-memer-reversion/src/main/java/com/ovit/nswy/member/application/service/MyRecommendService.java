package com.ovit.nswy.member.application.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface MyRecommendService {

    /**
     * 查找服务列表
     * @param params
     * @return
     */
    Map<String,Object> serviceList(Map<String,Object> params);

    /**
     * 推荐服务或者取消推荐服务
     * @param params
     */
    void operation(Map<String,Object> params);

    /**
     * 查找基地列表
     * @param params
     * @return
     */
    Map<String,Object> baseList(Map<String,Object> params);

    /**
     * 查找专家列表
     * @param params
     * @return
     */
    PageInfo<List<Map<String, Object>>> expertList(Map<String,Object> params);

    /**
     * 查找产品列表
     * @param params
     * @return
     */
    Map<String,Object> productList(Map<String,Object> params);

}
