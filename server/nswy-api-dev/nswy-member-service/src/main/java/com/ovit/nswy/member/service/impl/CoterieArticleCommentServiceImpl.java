package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CoterieArticleCommentMapper;
import com.ovit.nswy.member.model.CoterieArticleComment;
import com.ovit.nswy.member.service.CoterieArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoterieArticleCommentServiceImpl implements CoterieArticleCommentService {
    @Autowired
    private CoterieArticleCommentMapper coterieArticleCommentDao;

    @Override
    public List<CoterieArticleComment> findAll() {
        return coterieArticleCommentDao.findAll();
    }

    @Override
    public PageInfo<CoterieArticleComment> query(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoterieArticleComment> list = coterieArticleCommentDao.findAll();
        return new PageInfo<>(list);
    }

}
