package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CameraInfoService {
    Integer saveCameraInfo(Map<String, Object> params);

    Integer deleteCameraInfoById(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> queryCameraInfo(Map<String, Object> params);

    List<Map<String,Object>> queryCameraByProductId(Map<String, Object> params);
}
