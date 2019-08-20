package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Article;
import com.ovit.nswy.member.model.Knowledge;

import java.util.List;
import java.util.Map;

public interface KnowledgeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

    List<Knowledge> findKnowLedge();

    List<Map<String, Object>> findShow();

    List<Knowledge> findKnowLedgeTitle(Knowledge record);

    List<Map<String, Object>> getBriefNews();

    Knowledge findKnowLedgeByDetailId(int id);

    void insertArticle(Article article);
}