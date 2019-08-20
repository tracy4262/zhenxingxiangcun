package com.ovit.nswy.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

public interface RestaurantManagementService {


    PageInfo<Map<String,Object>> findRestaurant(Map<String,Object> params);

    String insertRestaurant(Map<String,Object> params);

    String updateRestaurant(Map<String,Object> params);

    void deleteRestaurant(Map<String,Object> params);

    PageInfo<Map<String,Object>> findFood(Map<String,Object> params);

    String insertFood(Map<String,Object> params);

    String updateFood(Map<String,Object> params);

    void deleteFood(Map<String,Object> params);


    PageInfo<Map<String,Object>> findRoom(Map<String,Object> params);

    String insertRoom(Map<String,Object> params);

    String updateRoom(Map<String,Object> params);

    void deleteRoom(Map<String,Object> params);


    PageInfo<Map<String,Object>> findTable(Map<String,Object> params);

    String insertTable(Map<String,Object> params);

    String updateTable(Map<String,Object> params);

    void deleteTable(Map<String,Object> params);

    List<Map<String,Object>> findTableDetail(Map<String,Object> params);



}
