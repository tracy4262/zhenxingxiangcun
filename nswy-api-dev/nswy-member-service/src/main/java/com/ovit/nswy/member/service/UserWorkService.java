package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface UserWorkService {
   void insert(Map<String, Object> map);

   Map<String,Object> selectByAccount(Map<String,Object> map);

    void deleteByAccount(Map<String,Object> map);
}
