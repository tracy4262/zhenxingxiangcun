package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface MemberService {

    PageInfo<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String, Object>> findMemberType(Map<String, Object> params);
}