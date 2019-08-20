package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface PrivacyInfoService {


    Map<String, Object> findPrivacyInfo(Map<String,Object> params);

    void insertPrivacyInfo(Map<String,Object> params);

    void updatePrivacyInfoName(Map<String,Object> params);
}
