package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.IndividuationMapper;
import com.ovit.nswy.member.service.IndividuationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividuationServiceImpl implements IndividuationService {
    @Autowired
    private IndividuationMapper dao;

    @Override
    public List<String> selectByLeib(String list) {
        return dao.selectByLeib(list);
    }

    public String selectByName(String fieldName) {
        return dao.selectByName(fieldName);
    }
}