package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.CoterieArticleComment;

import java.util.List;

public interface CoterieArticleCommentService {
    List<CoterieArticleComment> findAll();

    PageInfo<CoterieArticleComment> query(int PageNum, int PageSize);

}
