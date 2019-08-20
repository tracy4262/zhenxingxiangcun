package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.NswyHomeMapper;
import com.ovit.nswy.member.service.NswyHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 无忧首页
 *
 * @author haoWen
 * @create 2018-02-12 09:33
 **/
@Service
public class NswyHomeServiceImpl implements NswyHomeService{

    @Autowired
    private NswyHomeMapper nswyHomeMapper;

    @Override
    public List<Map<String, Object>> queryPictureForBanner(Map<String, Object> params) {
        return nswyHomeMapper.queryPictureForBanner(params);
    }

    @Override
    public List<Map<String, Object>> queryReadingTop() {
        return nswyHomeMapper.queryReadingTop();
    }

    @Override
    public List<Map<String, Object>> readingImp() {
        return nswyHomeMapper.readingImp();
    }
}
