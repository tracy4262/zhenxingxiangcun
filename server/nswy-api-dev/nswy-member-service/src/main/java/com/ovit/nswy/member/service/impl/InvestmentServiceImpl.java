package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.InvestmentMapper;
import com.ovit.nswy.member.model.Investment;
import com.ovit.nswy.member.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    @Autowired
    private InvestmentMapper investmentMapper;

    @Override
    public void insert(Investment investment) {
        investmentMapper.insert(investment);
    }

    @Override
    public Investment query(String account) {
        return investmentMapper.selectByAccount(account);
    }

    @Override
    public void edit(Investment investment) {
        investmentMapper.updateByPrimaryKey(investment);
    }
}
