package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface FarmFamilyMapper {

   void insertGroup(Map<String,Object> params);

   void insertTitle(Map<String,Object> params);

   void insertFarm(Map<String,Object> params);

   void insertLogin(Map<String,Object> params);

   List<Map<String,Object>> findGroup(Map<String,Object> params);

   List<Map<String,Object>> findTitle(Map<String,Object> params);

   List<Map<String,Object>> findLogin(Map<String,Object> params);

   List<Map<String,Object>> findFarmFamilyList(Map<String,Object> params);

   List<Map<String,Object>> findFarmFamily(Map<String,Object> params);

   void deleteFarmFamily(Integer farm_family_id);

   void updateGroup(Map<String,Object> params);

   void updateTitle(Map<String,Object> params);

   Integer findCountFamilyMember(Map<String,Object> params);

   Integer findCountFarmFamily(Map<String,Object> params);

   void insertCertification(Map<String,Object> params);


}