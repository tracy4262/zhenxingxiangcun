package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface CameraInfoMapper {
    Integer deleteCameraInfoById(Map<String, Object> params);

    Integer saveCameraInfo(Map<String, Object> params);

    List<Map<String,Object>> queryCameraInfo(Map<String, Object> params);

    Map<String,Object> queryCameraInfoByProduct(Map<String, Object> params);
}
