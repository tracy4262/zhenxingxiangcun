package com.ovit.nswy.portal.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.xphsc.collect.Maps;
import com.ovit.nswy.portal.manager.BaseManager;
import com.ovit.nswy.portal.mapper.IntroductionMapper;
import com.ovit.nswy.portal.service.IntroductionService;
import com.ovit.nswy.portal.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 我的门户之介绍
 *
 * @author haoWen
 * @create 2018-01-12 13:33
 **/
@Service
public class IntroductionServiceImpl implements IntroductionService {
    @Autowired
    private IntroductionMapper introductionMapper;
    @Autowired
    private BaseManager baseManager;
    @Override
    public Map<String, Object> getIntroduction(Map<String, Object> params) {
        return introductionMapper.getInfo(params);
    }

    @Override
    public PageInfo<List<Map<String,Object>>> getHonorInfoOrAptitude(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String,Object>> listPageInfo = introductionMapper.getHonorInfoOrAptitude(params);
        return new PageInfo(listPageInfo);
    }

    @Override
    public Map<String,Object> getGovBaseInfo(Map<String, Object> params) {
        return introductionMapper.getGovBaseInfo(params);
    }

    @Override
    public Map<String, Object> getOwnerBaseInfo(Map<String, Object> params) {
        return introductionMapper.getOwnerBaseInfo(params);
    }

    @Override
    public Map<String, Object> queryOwnerContact(Map<String, Object> params) {
        return introductionMapper.queryOwnerContact(params);
    }

    @Override
    public int queryMyFollow(Map<String, Object> params) {
        return introductionMapper.queryMyFollow(params);
    }

    @Override
    public int queryMyFans(Map<String, Object> params) {
        return introductionMapper.queryMyFans(params);
    }

    @Override
    public Map<String, Object> getSpecial(Map<String, Object> params) {
        Map map=introductionMapper.getSpecial(params);
        String name=baseManager.getIndustrialName(Maps.getString(map, "relatedIndustry"));
        map.put("relatedIndustry",name);
        return map;
    }

    @Override
    public List<Map<String, Object>> queryOwnerHonor(Map<String, Object> params) {
        return introductionMapper.queryOwnerHonor(params);
    }
}
