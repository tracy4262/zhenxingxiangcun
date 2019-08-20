package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface PlantingGroupMapper {

    void add(Map<String, Object> params);

    Integer queryMaxLevel(Map<String, Object> params);

    void del(int id);

    void delUser(int id);

    void rename(Map<String, Object> params);

    void updateLevel(Map<String, Object> params);

    List<Map<String,Object>> getList(Map<String, Object> params);


}