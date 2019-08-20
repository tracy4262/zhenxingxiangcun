package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.TownMapper;
import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownServiceImpl implements TownService {
    @Autowired
    private TownMapper mapper;

    @Override
    public List<Town> findTownByParentId(String parentId) {
        return mapper.findTownByParentId(parentId);
    }

    @Override
    public List<Town> findAllTown() {
        return mapper.findAllTown();
    }
}
