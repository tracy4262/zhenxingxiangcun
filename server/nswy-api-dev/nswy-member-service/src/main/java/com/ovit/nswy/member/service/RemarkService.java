package com.ovit.nswy.member.service;

import java.util.Map;

public interface RemarkService {
    int insert(Map<String, Object> map);

    Map<String, Object> selectByAccount(String account);
}
