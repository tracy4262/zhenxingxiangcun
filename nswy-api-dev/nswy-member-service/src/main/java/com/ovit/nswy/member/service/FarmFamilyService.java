package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface FarmFamilyService {

 String insertGroup(Map<String,Object> params);

 String insertFarm(Map<String,Object> params);

 List<Map<String,Object>> findGroup(Map<String,Object> params);

 String insertTitle(Map<String,Object> params);

 void updateTitle(Map<String,Object> params);

 List<Map<String,Object>> findTitle(Map<String,Object> params);

 PageInfo<Map<String,Object>> findFarmFamilyList(Map<String,Object> params);

 void deleteFarmFamily(Map<String,Object> params);

 void updateGroup(Map<String,Object> params);

 Integer findCountFarm(Map<String,Object> params);

}
