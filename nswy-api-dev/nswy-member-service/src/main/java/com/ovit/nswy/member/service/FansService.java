package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface FansService {
    PageInfo<Map<String, Object>> getFans(Map<String, Object> params);

    PageInfo<Map<String, Object>> getLike(Map<String, Object> params);

    void add(Map<String, Object> params);

    void del(Map<String, Object> params);

    Boolean status(Map<String, Object> params);

    void cancel(Map<String, Object> params);

    void batchLike(Map<String,Object> params);

}