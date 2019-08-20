package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface CorpCertificationMapper {

    Map<String, Object> findCertificationByAccount(String account);

    void saveCertification(Map<String, Object> perfectInfo);

    void updateCertification(Map<String, Object> perfectInfo);

}