package com.ovit.nswy.member.application.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ConsultService {

    /**
     * 咨询服务发布第一步
     * @param params
     * @return
     */
    void publishStepOne(Map<String, Object> params);

    /**
     * 咨询服务发布第一步查询
     * @param id
     * @return
     */
    Map<String,Object> findStepOne(Integer id);

    /**
     * 咨询服务发布第二步
     * @param params
     * @return
     */
    void publishStepTwo(Map<String, Object> params);

    /**
     * 咨询服务发布第二步查询
     * @param params
     * @return
     */
    Map<String,Object> findStepTwo(Map<String, Object> params);

    /**
     * 拉取会员实名认证中的联系方式
     * @param params
     * @return
     */
    List<Map<String,Object>> contactList(Map<String,Object> params);

    /**
     * 拉取完善信息中的营业网点信息
     * @param params
     * @return
     */
    List<Map<String,Object>> networkStationList(Map<String,Object> params);

    /**
     * 咨询服务发布第三步
     * @param params
     * @return
     */
    void publishStepThree(Map<String, Object> params);

    /**
     * 咨询服务发布第三步查询
     * @param params
     * @return
     */
    Map<String,Object> findStepThree(Map<String, Object> params);

    /**
     * 咨询服务发布第四步
     * @param params
     * @return
     */
    void publishStepFour(Map<String, Object> params);

    /**
     * 咨询服务发布第四步查询
     * @param params
     * @return
     */
    Map<String,Object> findStepFour(Map<String, Object> params);

    /**
     * 查询用户发布的咨询服务
     * @param params
     * @return
     */
    Map<String,Object> list(Map<String, Object> params);

    /**
     * 查询咨询服务详情
     * @param params
     * @return
     */
    Map<String,Object> detail(Map<String, Object> params);

    /**
     * 咨询服务删除
     * @param id
     * @return
     */
    void delete(int id);

    /**
     * 判断是否是专家
     * @param params
     * @return
     */
    Integer isExpert(Map<String,Object> params);

    /**
     * 咨询服务列表
     * @param params
     * @return
     */
    Map<String, Object> serviceList(Map<String, Object> params);

}
