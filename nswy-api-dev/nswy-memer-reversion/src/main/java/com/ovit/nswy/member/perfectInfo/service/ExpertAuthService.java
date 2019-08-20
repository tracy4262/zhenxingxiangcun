package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface ExpertAuthService {

    /**
     * 查询专业认证信息
     * @param params
     * @return
     */
    Map<String,Object> find(Map<String, Object> params);

    /**
     * 保存-专家认证信息
     * @param params
     * @return
     */
    void save(Map<String, Object> params);

    /**
     * 删除专家认证信息
     * @param params
     */
    void delete(Map<String, Object> params);

    Map<String,Object> findExpertInfo(Map<String, Object> params);


}
