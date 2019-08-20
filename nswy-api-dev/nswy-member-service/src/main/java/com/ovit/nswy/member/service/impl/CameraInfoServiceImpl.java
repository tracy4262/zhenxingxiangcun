package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CameraInfoMapper;
import com.ovit.nswy.member.service.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 摄像头信息service
 *
 * @author haoWen
 * @create 2017-12-22 15:35
 **/
@Service
public class CameraInfoServiceImpl implements CameraInfoService {
    @Autowired
    private CameraInfoMapper cameraInfoMapper;

    @Override
    public Integer saveCameraInfo(Map<String, Object> params) {
        return cameraInfoMapper.saveCameraInfo(params);
    }

    @Override
    public Integer deleteCameraInfoById(Map<String, Object> params) {
        return cameraInfoMapper.deleteCameraInfoById(params);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryCameraInfo(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("pageNum"))),Integer.parseInt(String.valueOf(params.get("pageSize"))));
        List<Map<String,Object>> cameraInfo = cameraInfoMapper.queryCameraInfo(params);
        return new PageInfo(cameraInfo);
    }

    @Override
    public List<Map<String,Object>> queryCameraByProductId(Map<String, Object> params) {
        return cameraInfoMapper.queryCameraInfo(params);
    }
}
