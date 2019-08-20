package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface FansMapper {

    List<Map<String, Object>> getFans(Map<String, Object> params);

    List<Map<String, Object>> getLike(Map<String, Object> params);

    void add(Map<String, Object> params);

    void del(Map<String, Object> params);

    Integer status(Map<String, Object> params);

    void cancel(Map<String, Object> params);

    Integer getFansTotal(Map<String,Object> params);

    Integer getLikeTotal(Map<String,Object> params);

}