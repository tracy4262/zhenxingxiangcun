package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.Investment;

public interface InvestmentService {

    void insert(Investment investment);

    void edit(Investment investment);

    Investment query(String account);

}
