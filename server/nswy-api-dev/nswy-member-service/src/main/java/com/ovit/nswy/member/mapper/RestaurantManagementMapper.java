package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface RestaurantManagementMapper {

    List<Map<String,Object>> findRestaurant(Map<String, Object> params);

    Integer findRestaurantCount(Map<String, Object> params);

    void insertRestaurant(Map<String, Object> params);

    void updateRestaurant(Map<String, Object> params);

    void deleteRestaurant(Map<String, Object> params);

    List<Map<String,Object>> findFoodManagementList(Map<String, Object> params);

    List<Map<String,Object>> findFood(Map<String, Object> params);

    Integer findFoodCount(Map<String, Object> params);

    void insertFood(Map<String, Object> params);

    void updateFood(Map<String, Object> params);

    void deleteFood(Map<String, Object> params);


    List<Map<String,Object>> findRoomManagementList(Map<String, Object> params);

    List<Map<String,Object>> findRoom(Map<String, Object> params);

    Integer findRoomCount(Map<String, Object> params);

    void insertRoom(Map<String, Object> params);

    void updateRoom(Map<String, Object> params);

    void deleteRoom(Map<String, Object> params);

    List<Map<String,Object>> findTable(Map<String, Object> params);

    Integer  findTableCount(Map<String, Object> params);

    void insertTable(Map<String, Object> params);

    void updateTable(Map<String, Object> params);

    void deleteTable(Map<String, Object> params);
}
