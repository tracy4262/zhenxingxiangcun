package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Coterie;
import com.ovit.nswy.member.model.CoterieArticle;

import java.util.List;
import java.util.Map;

public interface CoterieArticleService {
    List<CoterieArticle> findAll();

    List<CoterieArticle> findDesc();

    List<CoterieArticle> findHot();

    List<CoterieArticle> findDay();

    List<Map<String, Object>> list();

    List<Map<String, Object>> rank();

    PageInfo<CoterieArticle> findById(int id, int pageNum, int pageSize);

    PageInfo<Coterie> searchCoterie(Map<String, String> map, int pageNum, int pageSize);

}
