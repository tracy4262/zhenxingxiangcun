package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Card;

import java.util.List;

public interface CardMapper {

    int insert(Card card);

    int remove(Integer id);

    Card fingById(int id);

    List<Card> selectQuery(Card card);

}
