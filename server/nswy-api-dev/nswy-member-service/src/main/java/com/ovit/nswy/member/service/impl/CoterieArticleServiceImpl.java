package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CoterieArticleMapper;
import com.ovit.nswy.member.mapper.CoterieMapper;
import com.ovit.nswy.member.model.Coterie;
import com.ovit.nswy.member.model.CoterieArticle;
import com.ovit.nswy.member.service.CoterieArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoterieArticleServiceImpl implements CoterieArticleService {
    @Autowired
    private CoterieArticleMapper CoterieArticleDao;

    @Autowired
    private CoterieMapper IDao;

    @Override
    public List<CoterieArticle> findAll() {
        return CoterieArticleDao.findAll();
    }

    @Override
    public List<CoterieArticle> findDesc() {
        return CoterieArticleDao.findDesc();
    }

    /**
     *
     */
    @Override
    public List<CoterieArticle> findHot() {
        return CoterieArticleDao.findHot();
    }

    @Override
    public List<CoterieArticle> findDay() {
        return CoterieArticleDao.findDay();
    }

    @Override
    public List<Map<String, Object>> list() {
        return CoterieArticleDao.list();
    }

    @Override
    public List<Map<String, Object>> rank() {
        return CoterieArticleDao.rank();
    }

    @Override
    public PageInfo<CoterieArticle> findById(int id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoterieArticle> list = CoterieArticleDao.findById(id);
        return new PageInfo<CoterieArticle>(list);
    }

    @Override
    public PageInfo<Coterie> searchCoterie(Map<String, String> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, 5);
        List<Coterie> list = IDao.searchCoterie(map);
        return new PageInfo<Coterie>(list);
    }

}
