package com.ovit.nswy.member.certification.service;

import java.util.List;
import java.util.Map;

public interface CollectService {

	List<Map<String,Object>> findCollectList(Map<String,Object> params);

	List<Map<String, Object>> queryList(Map<String, Object> params);

	List<Map<String, Object>> queryNext(Map<String, Object> params);

	void rename(Map<String, Object> params);

	void renameRemark(Map<String, Object> params);

	boolean del(Map<String, Object> params);

	void add(Map<String, Object> params);

	Map<String, Object> queryAll(Map<String,Object> accountInfo);

	boolean isExist(Map<String, Object> params);

	void updata(Map<String, Object> params);

	List<Map<String, Object>> findByName(Map<String, Object> params);

	void addUser(Map<String, Object> params);

	List<Map<String, Object>> queryFavorite(Map<String,Object> accountInfo);

	List<Map<String,Object>> isExistData(Map<String, Object> params);
}