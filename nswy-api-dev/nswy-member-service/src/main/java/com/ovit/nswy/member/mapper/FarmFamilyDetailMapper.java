package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface FarmFamilyDetailMapper {


   List<Map<String,Object>> findFamilyMember(Map<String,Object> params);

   List<Map<String,Object>> findOutputProduct(Map<String,Object> params);

   List<Map<String,Object>> findProvideServices(Map<String,Object> params);

   List<Map<String,Object>> findPurchaseIntention(Map<String,Object> params);

   List<Map<String,Object>> findHouseSituation(Map<String,Object> params);

   List<Map<String,Object>> findFamilySituation(Map<String,Object> params);

   void insertFamilyMember(Map<String,Object> params);

   void insertOutputProduct(Map<String,Object> params);

   void insertProvideServices(Map<String,Object> params);

   void insertPurchaseIntention(Map<String,Object> params);

   void insertHouseSituation(Map<String,Object> params);

   void insertFamilySituation(Map<String,Object> params);

   void deleteFamilyMember(Map<String,Object> params);

   void deleteOutputProduct(Map<String,Object> params);

   void deleteProvideServices(Map<String,Object> params);

   void deletePurchaseIntention(Map<String,Object> params);

   void deleteHouseSituation(Map<String,Object> params);

   void deleteFamilySituation(Map<String,Object> params);

}