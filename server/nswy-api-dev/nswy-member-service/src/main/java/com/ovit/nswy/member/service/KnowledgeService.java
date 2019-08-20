package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Knowledge;
import com.ovit.nswy.member.model.KnowledgeComment;
import com.ovit.nswy.member.model.KnowledgeDetail;

import java.util.List;
import java.util.Map;

public interface KnowledgeService {

    PageInfo<Knowledge> findKnowLedge(int pageNum, int pageSize);

    List<Map<String,Object>> findKnowLedgeDetail(String id);

    List<KnowledgeComment> listComment(Map<String, Object> map);

    PageInfo<Knowledge> findKnowLedgeTitle(int pageNum, int pageSize, Knowledge knowledge);

    PageInfo<KnowledgeComment> findKnowledgeComment(int pageNum, int pageSize, Integer id);

    List<KnowledgeComment> findKnowledgeComments(Map<String, Object> map);

    Integer findKnowledgeCommentCount(Integer id);

    PageInfo<KnowledgeComment> findMyComment(int pageNum, int pageSize, KnowledgeComment info);

    KnowledgeComment update(KnowledgeComment info);

    void insert(KnowledgeComment info);

    int insertKnowledgeDetail(KnowledgeDetail info);

    KnowledgeDetail upvote(Integer id);

    List<Map<String, Object>> findShow();

    PageInfo<KnowledgeComment> replylist(int pageNum, int pageSize, Map<String, String> map);

    void insertKnowledge(Knowledge information);

    PageInfo<KnowledgeComment> replyToOther(int pageNum, int pageSize, Integer postId);

    PageInfo<KnowledgeDetail> findBySpeciesName(Map<String,Object> map, int pageNum, int pageSize);

    List<KnowledgeDetail> newknowledge();

    List<Map<String, Object>> getBriefNews();

    Map<String,Object> findUpAndNextPage(int id );

    void listCommentCounts(Map<String, Object> map);

    /**
     * 相关知识
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<KnowledgeDetail> relevantKnowledge(Map<String,Object> map, int pageNum, int pageSize);

}
