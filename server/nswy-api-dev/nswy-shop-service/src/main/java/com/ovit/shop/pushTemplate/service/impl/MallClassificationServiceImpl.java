package com.ovit.shop.pushTemplate.service.impl;

import com.ovit.shop.pushTemplate.mapper.MallClassificationInfoMapper;
import com.ovit.shop.pushTemplate.service.MallClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MallClassificationServiceImpl implements MallClassificationService {
    @Autowired
    private MallClassificationInfoMapper mallClassificationInfoMapper;

    @Override
    public List<Map<String,Object>> findMallClassification(Map<String,Object> params){
        return  mallClassificationInfoMapper.findMallClassification(params);
    }

}
