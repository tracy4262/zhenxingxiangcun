package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface BaiduMapMapper {

    List<Map<String, Object>> selectAll(Map<String, Object> params);

}
