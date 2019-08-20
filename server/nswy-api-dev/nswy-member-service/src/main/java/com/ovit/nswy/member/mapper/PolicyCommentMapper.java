package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.PolicyComment;

import java.util.List;
import java.util.Map;

public interface PolicyCommentMapper {

    List<PolicyComment> listComment(Map<String, Object> map);

    void insert(PolicyComment record);

    PolicyComment selectByPrimaryKey(Integer id);

    List<PolicyComment> findPolicyComment(PolicyComment record);

    List<PolicyComment> findPolicyComments(Map<String, Object> map);

    List<PolicyComment> findPolicyCommentId(PolicyComment record);

    List<PolicyComment> findMyComment(PolicyComment record);

    void updateThumbUpNum(PolicyComment record);

    List<PolicyComment> replylist(Map<String,String> map);

    List<PolicyComment> replyToOther(Integer postId);
}