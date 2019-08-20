package com.ovit.nswy.portal.mapper;

import java.util.List;
import java.util.Map;

public interface DynamicMapper {
    List<Map<String,Object>> queryDynamicInfo(Map<String, Object> params);

}
