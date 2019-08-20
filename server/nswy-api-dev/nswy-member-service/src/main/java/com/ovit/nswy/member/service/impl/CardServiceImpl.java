package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.CardMapper;
import com.ovit.nswy.member.model.Card;
import com.ovit.nswy.member.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardDao;

    @Override
    public int insert(Card card) {
        return cardDao.insert(card);
    }

    @Override
    public int remove(int id) {
        return cardDao.remove(id);
    }

    @Override
    public Card fingById(int id) {
        return cardDao.fingById(id);
    }

    @Override
    public PageInfo<Card> selectQuery(int pageNum, int pageSize, Card card) {
        PageHelper.startPage(pageNum, pageSize);
        List<Card> list = cardDao.selectQuery(card);
        return new PageInfo<Card>(list);
    }
}
