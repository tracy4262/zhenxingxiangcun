package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;

public interface FishingServicesService {

    PageInfo<Map<String,Object>> findFishingService(Map<String, Object> params);


    String insertFishingService(Map<String, Object> params);

    void updateFishingService(Map<String, Object> params);

    void deleteFishingService(Map<String, Object> params);

    List<Map<String,Object>> findProductService(Map<String,Object> params);

    void saveProduct(Map<String,Object> params);

    void deleteProductManagementService(Map<String,Object> params);

    PageList findProductServiceList(Map<String,Object> params);

    List<Map<String,Object>> findProductServiceById(Map<String,Object> params);

    void saveComment(Map<String,Object> params);

    PageInfo<Map<String,Object>> findOrderList(Map<String,Object> params);

    void saveOrder(Map<String,Object> params);

    void updateOrderStatus(Map<String,Object> params);

    PageList findJoinServiceList(Map<String,Object> params);

    void saveJoinServiceInfo(Map<String, Object> params);

    void  deleteJoinServiceInfo(Map<String, Object> params);

    Map<String,Object> findAvatar(Map<String,Object> params);


    void updateOrderRefund(Map<String,Object> params);
}
