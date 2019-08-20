package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.InfoComment;

import java.util.List;
import java.util.Map;

public interface InfoCommentMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(InfoComment record);

    int insertSelective(InfoComment record);

    InfoComment selectByPrimaryKey(Integer id);

    void updateThumbUpNum(InfoComment record);

    int updateByPrimaryKeySelective(InfoComment record);

    int updateByPrimaryKey(InfoComment record);

    List<InfoComment> findInfoComment(InfoComment record);

    List<InfoComment> findMyInfoComment(Map<String, Object> map);

    List<InfoComment> findInfoComments(Map<String, Object> map);

    List<InfoComment> listComment(Map<String, Object> map);

    List<InfoComment> listCommentDetail(Map<String, Object> map);

    List<InfoComment> findInfoCommentId(InfoComment record);

    List<InfoComment> findMyComment(InfoComment record);

    List<InfoComment> replylist(Map<String,String> map);

    List<InfoComment> replyToOther(Integer postId);

    List<Map<String,Object>>  findComment(Map<String,Object> params);

    List<Map<String,Object>>  findCommentHF(Map<String,Object> params);

    List<Map<String,Object>>  findCommentHFDetail(Map<String,Object> params);

    void deleteMyComment(Map<String,Object> params);
}