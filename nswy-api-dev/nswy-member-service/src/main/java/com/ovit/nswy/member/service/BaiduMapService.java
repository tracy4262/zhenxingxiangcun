package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BaiduMapService {

    List<Map<String, Object>> selectAll(Map<String, Object> params);

}
