package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Expert;

import java.util.List;

public interface ExpertMapper {

    int insert(Expert record);

    Expert selectByPrimaryKey(Integer id);

    List<Expert> findAll();

    List<Expert> findExpertTitle(Expert record);

    int updateByPrimaryKey(Expert record);
}