package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface AccommodationManagementMapper {


    List<Map<String,Object>> findRoomClass(Map<String, Object> params);

    Integer findRoomClassCount(Map<String, Object> params);

    void insertRoomClass(Map<String, Object> params);

    void updateRoomClass(Map<String, Object> params);

    void deleteRoomClass(Map<String, Object> params);


    List<Map<String,Object>> findRoomListManagementList(Map<String, Object> params);

    List<Map<String,Object>> findRoomList(Map<String, Object> params);

    Integer findRoomListCount(Map<String, Object> params);

    void insertRoomList(Map<String, Object> params);

    void updateRoomList(Map<String, Object> params);

    void deleteRoomList(Map<String, Object> params);
}
