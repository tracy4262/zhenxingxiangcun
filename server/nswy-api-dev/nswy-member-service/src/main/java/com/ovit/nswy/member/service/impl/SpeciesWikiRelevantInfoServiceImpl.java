package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.AirQualityMapper;
import com.ovit.nswy.member.mapper.SpeciesMapper;
import com.ovit.nswy.member.service.AirQualityService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.service.SpeciesWikiRelevantInfoService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpeciesWikiRelevantInfoServiceImpl implements SpeciesWikiRelevantInfoService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private PerfectInformationService perfectInformationService;


    /**
     * 根据物种名称查询相关产品信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> relevantProduct(String speciesName) {

        return speciesMapper.relevantProduct(speciesName);
    }

    /**
     * 根据物种名称查询相关专家信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> relevantExpert(String speciesName) {

        return speciesMapper.findExpert(speciesName);
    }

    /**
     * 根据物种名称查询相关企业信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> relevantCorp(String account) {

        return speciesMapper.relevantCorp(account);
    }
}
