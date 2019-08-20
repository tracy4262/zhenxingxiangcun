package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CmsArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsArticle record);

    int insertSelective(CmsArticle record);

    List<CmsArticle> findArticle(CmsArticle cm);

    int updateByPrimaryKeySelective(CmsArticle record);

    int updateByPrimaryKey(CmsArticle record);
}