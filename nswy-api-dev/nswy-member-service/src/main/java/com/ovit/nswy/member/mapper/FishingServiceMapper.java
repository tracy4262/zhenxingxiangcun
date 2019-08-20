package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface FishingServiceMapper {

    List<Map<String,Object>> findFishingService(Map<String, Object> params);

    void insertFishingService(Map<String, Object> params);

    void updateFishingService(Map<String, Object> params);

    void deleteFishingService(Map<String, Object> params);

    String findMaxId();

    List<Map<String,Object>> findProductId(String communalId);

    List<Map<String,Object>> findRoomId(String communalId);


    void saveProduct(Map<String, Object> params);

    void saveRoom(Map<String, Object> params);

    void deleteProduct(String id);

    void deleteRoom(String id);

    Map<String,Object> findScenicList(String id);

    Map<String,Object> findRoomList(String id);


    Map<String,Object> findProductList(Map<String, Object> params);

    List<Map<String,Object>> findProductManagementService(Map<String, Object> params);

    void updateProductManagementService(Map<String, Object> params);

    void insertProductManagementService(Map<String, Object> params);

    void deleteProductManagementService(Map<String, Object> params);

    List<Map<String,Object>> findProductServiceList(Map<String, Object> params);

    Map<String,Object> findScenicDetailById(String id);

    Map<String,Object> findRoomListDetailById(Map<String, Object> params);

    Map<String,Object> findFoodDetailById(Map<String, Object> params);

    Map<String,Object> findCommentProbability(Map<String, Object> params);

    List<Map<String,Object>> findCommentList(Map<String, Object> params);

    void  saveComment(Map<String, Object> params);

    int findIsNotOrderId(String order_code);

    void  saveOrder(Map<String, Object> params);

    List<Map<String,Object>> findOrderList(Map<String, Object> params);

    Map<String,Object> findAccountByName(String account);

    Map<String,Object> findAccountByNames(String account);

    Map<String,Object> findInfoByName(Map<String, Object> params);

    Map<String,Object> findCommentProbabilityNum(Map<String, Object> params);

    void saveServiceOrderManagement(Map<String, Object> params);

    void  updateOrderStatus(Map<String, Object> params);

    List<Map<String,Object>> findTableRoomList(String management_id);

    int findOrderCount(Map<String, Object> params);

    void updateFishingServiceStatus(Map<String, Object> params);


    List<Map<String,Object>> findJoinServiceList(Map<String, Object> params);

    List<Map<String,Object>> findServiceJoinInfo(Map<String, Object> params);

    void saveJoinServiceInfo(Map<String, Object> params);

    void  deleteJoinServiceInfo(Map<String, Object> params);

    List<Map<String,Object>> findServiceVarietyInfo(Map<String, Object> params);

    void saveServiceVarietyInfo(Map<String, Object> params);

    void updateServiceVarietyInfo(Map<String, Object> params);

    void deleteServiceVarietyInfo(Map<String, Object> params);


    List<Map<String,Object>> findFishTimeChargeInfo(Map<String, Object> params);

    void saveFishTimeChargeInfo(Map<String, Object> params);

    void deleteFishTimeChargeInfo(Map<String, Object> params);

    List<Map<String,Object>> findFishVarietyChargeInfo(Map<String, Object> params);

    void saveFishVarietyChargeInfo(Map<String, Object> params);

    void deleteFishVarietyChargeInfo(Map<String, Object> params);

    Map<String,Object> findAvatar(Map<String, Object> params);

    String findMaxServiceVarietyId(Map<String, Object> param);

    List<Map<String,Object>> findAllServiceList(Map<String, Object> params);

    void saveConsultOrder(Map<String, Object> params);

    void saveConsultDetail(Map<String, Object> params);

    Map<String,Object> findConsultService(Map<String, Object> params);

    void updateOrderRefund(Map<String, Object> params);

    int findCousultOrderId(String orderCode);

    String findServiceById(Map<String, Object> params);

    Map<String,Object> isHadRecommend(Map<String, Object> params);

    void singleSaveRecommend(Map<String, Object> params);


}