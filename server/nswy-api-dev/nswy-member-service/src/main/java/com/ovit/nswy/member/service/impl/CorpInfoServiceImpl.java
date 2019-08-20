package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CorpInfoMapper;
import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.service.CorpInfoService;
import com.ovit.nswy.member.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CorpInfoServiceImpl implements CorpInfoService {

    @Autowired
    private CorpInfoMapper corpInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(CorpInfo record) {

        return corpInfoMapper.insert(record);
    }

    @Override
    public CorpInfo selectByPrimaryKey(Integer id) {
        return corpInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(CorpInfo record) {
        return corpInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateByPrimaryKeySelective(CorpInfo record) {
        corpInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public CorpInfo findByAccount(CorpInfo record) {
        return corpInfoMapper.findByAccount(record);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return corpInfoMapper.findAll();
    }

    @Override
    public PageInfo<Map<String, Object>> query(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = corpInfoMapper.findAll();
        return new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public Integer getApproveStatus(String loginAccount) {
        return corpInfoMapper.getApproveStatus(loginAccount);
    }

    @Override
    public List<Map<String, Object>> queryCorpByLocation(String location) {
        return corpInfoMapper.queryCorpByLocation(location);
    }

    @Override
    public PageInfo<Map<String, Object>> selectByName(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list=corpInfoMapper.selectByName(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<List<Map<String, Object>>> queryCoryNotProxy(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> list = corpInfoMapper.queryCorpNotProxy(params);
        return new PageInfo(list);
    }

}
