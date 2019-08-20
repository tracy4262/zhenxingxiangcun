package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.KnowledgeComment;

import java.util.List;
import java.util.Map;

public interface KnowledgeCommentMapper {


    List<KnowledgeComment> listComment(Map<String, Object> map);

    void insert(KnowledgeComment record);

    KnowledgeComment selectByPrimaryKey(Integer id);

    List<KnowledgeComment> findKnowledgeComment(KnowledgeComment record);

    List<KnowledgeComment> findKnowledgeComments(Map<String, Object> map);

    List<KnowledgeComment> findKnowledgeCommentId(KnowledgeComment record);

    List<KnowledgeComment> findMyComment(KnowledgeComment record);

    void updateThumbUpNum(KnowledgeComment record);

    List<KnowledgeComment> replylist(Map<String,String> map);

    List<KnowledgeComment> replyToOther(Integer postId);
}