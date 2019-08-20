package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Card;

public interface CardService {

	int insert(Card card);

	int remove(int id);

	Card fingById(int id);

	PageInfo<Card> selectQuery(int PageNum, int PageSize, Card card);

}
