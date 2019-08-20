package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface CollectService {

	List<Map<String, Object>> queryList(Map<String, Object> params);

	List<Map<String, Object>> queryNext(Map<String, Object> params);

	void rename(Map<String, Object> params);

	void renameRemark(Map<String, Object> params);

	boolean del(Map<String, Object> params);

	void add(Map<String, Object> params);

	Map<String, Object> queryAll(String account);

	boolean isExist(Map<String, Object> params);

	void updata(Map<String, Object> params);

	List<Map<String, Object>> findByName(Map<String, Object> params);
}