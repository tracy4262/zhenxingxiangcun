package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CoterieArticleComment;

import java.util.List;

public interface CoterieArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoterieArticleComment record);

    List<CoterieArticleComment> findAll();

    CoterieArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(CoterieArticleComment record);
}