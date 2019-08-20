package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.SysDict;

import java.util.List;

public interface SysDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Integer id);

    List<SysDict> findByName(String name);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
}