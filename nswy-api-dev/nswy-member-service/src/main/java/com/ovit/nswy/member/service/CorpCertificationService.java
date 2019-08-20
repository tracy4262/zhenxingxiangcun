package com.ovit.nswy.member.service;

import java.util.Map;

public interface CorpCertificationService {

    /**
     * 通过用户名查询企业的完善信息
     * @param account
     * @return
     */
    Map<String, Object> findCertificationByAccount(String account);

    /**
     * 保存企业完善数据
     * @param perfectInfo
     */
    void saveCertification(Map<String,Object> perfectInfo);

    /**
     * 更新企业完善数据
     * @param perfectInfo
     */
    void updateCertification(Map<String,Object> perfectInfo);
}