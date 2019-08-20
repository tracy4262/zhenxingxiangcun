package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.GovInfoMapper;
import com.ovit.nswy.member.model.GovInfo;
import com.ovit.nswy.member.service.GovInfoService;
import com.ovit.nswy.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GovInfoServiceImpl implements GovInfoService {
    @Autowired
    private GovInfoMapper govInfoDao;

    @Override
    public List<Map<String, Object>> findAll() {
        return govInfoDao.findAll();
    }

    @Override
    public PageInfo<Map<String, Object>> query(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = govInfoDao.findAll();
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public void insert(Map<String, Object> params) {
        govInfoDao.insert(params);
    }

    @Override
    public Integer getApproveStatus(String loginAccount) {
        return govInfoDao.getApproveStatus(loginAccount);
    }

    @Override
    public PageInfo<Map<String, Object>> selectByName(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = govInfoDao.selectByName(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryGovNotProxy(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list=govInfoDao.queryGovNotProxy(params);
        return new PageInfo(list);
    }


    @Override
    public GovInfo findByAccount(String account) {
        return govInfoDao.findByAccount(account);
    }
}
