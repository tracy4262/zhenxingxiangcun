package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.Expert;

import java.util.List;

public interface ExpertService {
    List<Expert> findAll();

    PageInfo<Expert> query(int pageNum, int pageSize);

    PageInfo<Expert> findExpertTitle(int pageNum, int pageSize, String title);

}
