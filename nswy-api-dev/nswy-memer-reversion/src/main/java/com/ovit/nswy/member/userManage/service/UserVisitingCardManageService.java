package com.ovit.nswy.member.userManage.service;

import java.util.Map;

public interface UserVisitingCardManageService {

    Map<String,Object> findVisitingCard(Map<String,Object> params);

    void updateVisitingCard(Map<String,Object> params);

    Map<String,Object> findPhoneVisitingCard(Map<String,Object> params);
}
