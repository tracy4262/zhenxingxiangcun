package com.ovit.nswy.member.service;


import java.util.List;
import java.util.Map;

public interface PlantingGroupService {
    void add(Map<String, Object> params);

    void del(int id);

    void rename(Map<String, Object> params);

    void sorting(List<Map<String, Object>> list);

    List<Map<String, Object>> getList(Map<String, Object> params);

}