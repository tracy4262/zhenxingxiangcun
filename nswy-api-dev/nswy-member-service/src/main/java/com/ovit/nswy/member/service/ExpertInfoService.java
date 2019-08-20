package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.ExpertInfo;
import com.ovit.nswy.member.model.LoginUser;

import java.util.Map;

public interface ExpertInfoService {

    void insert(ExpertInfo expertInfo);

    Integer getApproveStatus(String loginAccount);

    ExpertInfo getExpertInfo(String loginAccount);

    void updateByPrimaryKeySelective(ExpertInfo expertInfo);

    PageInfo<Map<String, Object>> query(int pageNum, int pageSize);

    int deleteByPrimaryKey(Integer id);

    PageInfo<Map<String, Object>> findExpertTitle(Map<String, Object> map);

    void saveOrUpdate(LoginUser loginUser, Map<String, Object> params);
}
