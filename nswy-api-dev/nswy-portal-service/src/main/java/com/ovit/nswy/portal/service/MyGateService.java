package com.ovit.nswy.portal.service;

import java.util.Map;

public interface MyGateService {

    Map<String,Object> queryLabel(Map<String, Object> params);

    Map<String,Object> queryUserTypeByAccount(Map<String, Object> params);

    Map<String,Object> queryModularByAccount(Map<String, Object> params);
}
