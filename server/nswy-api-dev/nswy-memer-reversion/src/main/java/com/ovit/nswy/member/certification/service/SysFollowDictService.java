package com.ovit.nswy.member.certification.service;


import com.ovit.nswy.member.certification.model.SysFollowDict;

import java.util.List;
import java.util.Map;

public interface SysFollowDictService {

	void insert(SysFollowDict sysFollowDict);

	SysFollowDict findQuery(Map<String, Object> map);

	List<Map<String,Object>>  findList(Map<String, Object> map);

	void followInsert(List<Map<String, Object>> list, Map<String,Object> accountInfo);

	List<List<Map<String,Object>>>  findFllowDict(Map<String, Object> map);

	Map<String,Object> findPushStatus(Map<String,Object> accountInfo);

	void savePushStatus(Map<String,Object> accountInfo);

	void deletePushStatus(Map<String,Object> accountInfo);

	void followDelete(Map<String,Object> accountInfo);

}
