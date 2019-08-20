package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.FollowInfo;
import com.ovit.nswy.member.model.SysDict;
import com.ovit.nswy.member.model.SysFollowDict;

import java.util.List;
import java.util.Map;

public interface SysFollowDictMapper {

    void insert(SysFollowDict record);

    SysFollowDict findQuery(Map<String,Object> map);

    List<Map<String,Object>> findList(Map<String,Object> map);

    List<Map<String,Object>> findSysFollowDict(Map<String,Object> map);

    void insertFollow(FollowInfo followInfo);

    List<Map<String,Object>> findFllowDict(Map<String,Object> map);

    void deleteFollowDict(Map<String,Object> map);


}