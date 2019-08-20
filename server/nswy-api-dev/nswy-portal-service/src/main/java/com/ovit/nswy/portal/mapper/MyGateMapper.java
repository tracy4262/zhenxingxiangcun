package com.ovit.nswy.portal.mapper;

import java.util.Map;

public interface MyGateMapper {

    Map<String,Object> queryLabel(Map<String, Object> params);

    Map<String,Object> queryModularByAccount(Map<String, Object> params);

    Map<String,Object> queryUserTypeByAccount(Map<String, Object> params);
}
