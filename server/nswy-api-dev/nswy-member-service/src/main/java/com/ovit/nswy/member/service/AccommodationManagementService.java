package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface AccommodationManagementService {

    PageInfo<Map<String,Object>> findRoomClass(Map<String,Object> params);

    String insertRoomClass(Map<String,Object> params);

    String updateRoomClass(Map<String,Object> params);

    void deleteRoomClass(Map<String,Object> params);

    PageInfo<Map<String,Object>> findRoomList(Map<String,Object> params);

    String insertRoomList(Map<String,Object> params);

    String updateRoomList(Map<String,Object> params);

    void deleteRoomList(Map<String,Object> params);
}
