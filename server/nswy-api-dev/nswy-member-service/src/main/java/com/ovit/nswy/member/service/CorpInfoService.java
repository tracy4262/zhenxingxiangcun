package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.CorpInfo;

import java.util.List;
import java.util.Map;

public interface CorpInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(CorpInfo record);

    CorpInfo selectByPrimaryKey(Integer id);

    List<Map<String, Object>> findAll();

    PageInfo<Map<String, Object>> query(int PageNum, int PageSize);

    int updateByPrimaryKey(CorpInfo record);

    void updateByPrimaryKeySelective(CorpInfo record);

    CorpInfo findByAccount(CorpInfo record);

    Integer getApproveStatus(String loginAccount);

    List<Map<String, Object>> queryCorpByLocation(String location);

    PageInfo<Map<String, Object>> selectByName(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> queryCoryNotProxy(Map<String, Object> params);
}