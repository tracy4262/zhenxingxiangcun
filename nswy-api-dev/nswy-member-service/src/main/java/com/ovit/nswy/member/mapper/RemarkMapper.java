package com.ovit.nswy.member.mapper;

import java.util.Map;


public interface RemarkMapper {
    int insert(Map<String, String> map);

    Map<String, String> selectByAccount(String account);
}
