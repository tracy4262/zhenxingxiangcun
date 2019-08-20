package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.StandardComment;
import java.util.List;
import java.util.Map;

public interface StandardMapper {

    int insertStandardDetail(Map<String, Object> params);

    int insertStandardBase(Map<String, Object> params);

    List<Map<String,Object>> queryForMemberCenter(Map<String, Object> params);

    Map<String,Object> queryStandardDetail(Map<String, Object> params);

    Map<String,Object> queryNext(Map<String, Object> params);

    Map<String,Object> queryPrevious(Map<String, Object> params);

    List<Map<String,Object>> queryStandardComment(Map<String, Object> params);

    int queryStandardCommentCount(Map<String, Object> params);

    int publishComment(Map<String, Object> params);

    int updateBrowseNumber(Map<String, Object> params);

    int queryThumbNumByDetailId(Map<String, Object> params);

    String findSpeciesIdFromStandard(int id);

    List<Map<String,Object>> queryDetailCommentReply(Map<String, Object> params);

    List<Map<String, Object>> queryForNswyNavigation(Map<String, Object> params);

    List<Map<String, Object>> queryForNswyHome(Map<String, Object> params);

    List<Map<String,Object>>  findCommentHF(Map<String,Object> params);

    List<Map<String,Object>>  findCommentHFDetail(Map<String,Object> params);

    void deleteMyComment(Map<String,Object> params);
}
