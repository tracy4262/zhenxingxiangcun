package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.BrowseLogMapper;
import com.ovit.nswy.member.model.BrowseLog;
import com.ovit.nswy.member.service.BrowseLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "browseLogService")
public class BrowseLogServiceImpl implements BrowseLogService {

    @Resource
    private BrowseLogMapper browseLogMapper;

    @Override
    public PageInfo<BrowseLog> findBrowseLog(int pageNum, int pageSize, BrowseLog info) {
        PageHelper.startPage(pageNum, pageSize);
        List<BrowseLog> list = browseLogMapper.findBrowseLog(info);
        return new PageInfo<>(list);
    }

    @Override
    public void insertBrowseLog(BrowseLog info) {
        browseLogMapper.insert(info);
    }
}
