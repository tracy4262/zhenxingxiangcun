package com.ovit.nswy.member.application.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EmployService {

    /**
     * 查找专家
     * @param params
     * @return
     */
    PageInfo<List<Map<String, Object>>> findExpert(Map<String, Object> params);

    /**
     * 聘请管理
     * @param params
     * @return
     */
    PageInfo<List<Map<String, Object>>> manage(Map<String, Object> params);

    /**
     * 邀请专家
     * @param params
     * @return
     */
    void invite(Map<String, Object> params);

    /**
     * 取消邀请
     * @param params
     * @return
     */
    void cacelInvite(Map<String, Object> params);

    /**
     * 解除关系
     * @param params
     * @return
     */
    void relieve(Map<String, Object> params);

    /**
     * 受邀管理
     * @param params
     * @return
     */
    PageInfo<List<Map<String, Object>>> beInvited(Map<String, Object> params);

    /**
     * 处理邀请
     * @param params
     * @return
     */
    void dealInvite(Map<String, Object> params);

    /**
     * 受聘管理
     * @param params
     * @return
     */
    PageInfo<List<Map<String, Object>>> beEmployed(Map<String, Object> params);

    /**
     * 订单详情
     * @param id
     * @return
     */
    Map<String,Object> orderDetail(Integer id);

}
