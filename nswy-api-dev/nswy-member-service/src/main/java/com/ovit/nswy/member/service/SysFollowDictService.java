package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.SysFollowDict;

import java.util.List;
import java.util.Map;

public interface SysFollowDictService {

	void insert(SysFollowDict sysFollowDict);

	public SysFollowDict findQuery(Map<String,Object> map);


	public List<Map<String,Object>>  findList(Map<String,Object> map);

	public void followInsert(List<Map<String,Object>> list, String account);

	public  List<List<Map<String,Object>>>  findFllowDict(Map<String,Object> map);

}
