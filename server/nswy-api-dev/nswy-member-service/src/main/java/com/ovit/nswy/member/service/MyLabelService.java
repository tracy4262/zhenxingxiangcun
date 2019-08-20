package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface MyLabelService {
    int insert(List<Map<String, Object>> map);

    List<String> selectByAccount(String account);

    void deleteByAccount(String account);
}
