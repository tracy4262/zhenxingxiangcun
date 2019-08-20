package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.SysDict;

import java.util.List;

public interface SysDictService {

	List<SysDict> findByName(String name);

}
