package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.StandardComment;

import java.util.Map;

public interface StandardService {
    int insertStandard(Map<String, Object> params);

    PageInfo<Map<String,Object>> queryForMemberCenter(Map<String, Object> params);

    Map<String,Object> queryStandardDetail(Map<String, Object> params);

    PageInfo<Map<String,Object>>  queryStandardComment(Map<String, Object> params);

    int queryStandardCommentCount(Map<String, Object> params);

    int publishComment(Map<String, Object> params);

    int updateBrowseNumber(Map<String, Object> params);

    int queryThumbNumByDetailId(Map<String, Object> params);

    PageInfo<Map<String,Object>> queryDetailCommentReply(Map<String, Object> params);

    PageInfo<Map<String,Object>> queryForNswyNavigation(Map<String, Object> params);

    PageInfo<Map<String,Object>> queryForNswyHome(Map<String, Object> params);

    void deleteMyComment (Map<String, Object> params);
}
