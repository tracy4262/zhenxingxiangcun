package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Certification;

import java.util.List;
import java.util.Map;

public interface CertificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Certification record);

    Certification selectByPrimaryKey(Integer id);

    Certification selectByAccount(String account);

    int updateByPrimaryKey(Certification record);

    void updateCertification(Certification certification);

    void UpdatePrivacyInfo(Map<String, Object> params);

    void updateFarmFamily(Map<String, Object> params);

    List<Map<String,Object>> findFarmFamily(Map<String, Object> params);
}