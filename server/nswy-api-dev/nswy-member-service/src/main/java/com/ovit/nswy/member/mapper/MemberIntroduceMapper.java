package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Article;
import com.ovit.nswy.member.model.MemberIntroduce;
import com.ovit.nswy.member.model.MemberIntroduceDetail;

import java.util.List;
import java.util.Map;

public interface MemberIntroduceMapper {

    Map<String,Object> findUseTemplate(String account);

    Map<String,Object> findNewYearInfo(String account);

    Map<String,Object> findLoginUserInfo(Map<String, Object> params);


    void insertIntroduce(MemberIntroduce info);

    void insertArticle(Article articleo);

    List<Map<String,Object>> findMediaLibraryInfo(Map<String,Object> params);

    Map<String,Object> findMemberIntroduceInfo(Map<String,Object> params);

    void deleteMemberIntroduceInfo(Map<String,Object> params);

    void deleteMemberIntroduceDetailInfo(Map<String,Object> params);

    void deleteMemberIntroduceBookInfo(Map<String,Object> params);

    void deleteMemberIntroduceBookDetailInfo(Map<String,Object> params);

    void deleteByPrimaryKey(Map<String,Object> params);












}
