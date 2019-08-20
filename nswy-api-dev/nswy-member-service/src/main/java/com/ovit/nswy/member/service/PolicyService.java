package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Policy;
import com.ovit.nswy.member.model.PolicyComment;
import com.ovit.nswy.member.model.PolicyDetail;

import java.util.List;
import java.util.Map;

public interface PolicyService {

    List<PolicyDetail> newpolicy();

    PageInfo<Policy> findPolicy(int pageNum, int pageSize);

    List<Map<String,Object>> findPolicyDetail(String id);

    PageInfo<Policy> findPolicyTitle(int pageNum, Policy policy, int pageSize);

    PageInfo<PolicyComment> findPolicyComment(int pageNum, int pageSize, Integer informationDetailId);

    List<PolicyComment> findPolicyComments(Map<String, Object> map);

    List<PolicyComment> listComment(Map<String, Object> map);

    Integer findPolicyCommentCount(Integer id);

    PageInfo<PolicyComment> findMyComment(int pageNum, int pageSize, PolicyComment info);

    PolicyComment update(PolicyComment info);

    void insert(PolicyComment info);

    int insertPolicyDetail(PolicyDetail info);

    int insertPolicy(Policy info);

    PolicyDetail upvote(Integer id);

    PageInfo<Policy> findIndexPolicy(int pageNum, int pageSize, String docType);

    PageInfo<PolicyComment> replylist(int pageNum, int pageSize, Map<String, String> map);

    PageInfo<PolicyComment> replyToOther(int pageNum, int pageSize, Integer postId);

    PageInfo<PolicyDetail> findBySpeciesName(Map<String,Object> map, int pageNum, int pageSize);

    List<Map<String, Object>> getBriefNews();

    Map<String,Object> findUpAndNextPage(int id);

    void listCommentCounts(Map<String, Object> map);

    /**
     * 相关政策
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<PolicyDetail> relevantPolicy(Map<String,Object> map, int pageNum, int pageSize);
}
