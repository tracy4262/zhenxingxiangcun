package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.GovInfo;

import java.util.List;
import java.util.Map;

public interface GovInfoService {

    List<Map<String, Object>> findAll();

    PageInfo<Map<String, Object>> query(int PageNum, int PageSize);

    void insert(Map<String, Object> params);

    Integer getApproveStatus(String loginAccount);

    PageInfo<Map<String, Object>> selectByName(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> queryGovNotProxy(Map<String, Object> params);

    GovInfo findByAccount(String account);
}
