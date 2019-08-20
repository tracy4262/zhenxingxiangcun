package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.IndustrialMapper;
import com.ovit.nswy.member.model.Industrial;
import com.ovit.nswy.member.service.IndustrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IndustrialServiceImpl implements IndustrialService {
    @Autowired
    private IndustrialMapper industrialMapper;

    @Override
    public List<Industrial> getIndustryType() {
        Industrial record = new Industrial();
        List<Industrial> list = industrialMapper.findIndustrial(record);
        return list;
    }

    @Override
    public List<Industrial> findIndustryByParentId(String parentId) {
        return industrialMapper.findIndustryByParentId(parentId);
    }

    @Override
    public List<Industrial> findIndustry(Industrial industrial) {
        return industrialMapper.findIndustrial(industrial);
    }

    @Override
    public List<Map<String,Object>> getIndustryByIdArray(List<String> relatedIndustryIdArray) {
        return industrialMapper.getIndustryByIdArray(relatedIndustryIdArray);
    }
}
