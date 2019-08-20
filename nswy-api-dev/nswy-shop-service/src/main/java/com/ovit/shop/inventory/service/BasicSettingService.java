package com.ovit.shop.inventory.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BasicSettingService {

    List<Map<String, Object>> customFind(Map<String,Object> params);

    void customSave(Map<String,Object> params);

    List<Map<String, Object>> productCodeIsUse(Map<String,Object> params);

    void productSave(Map<String,Object> params);

    PageInfo<Map<String, Object>> productFind(Map<String, Object> params);

    void productDelete(Map<String, Object> params);

    void customDelete(Map<String, Object> params);

    Integer getStoreOrder(String account);

    void storeSave(Map<String,Object> params);

    PageInfo<Map<String, Object>> storeFind(Map<String, Object> params);

    void storeDelete(Map<String,Object> params);

    void billSave(Map<String, Object> params);

    Map<String,Object> getProductName(Map<String, Object> params);

    Map<String,Object> billFind(Map<String, Object> params);

    Integer getInStoreOrder(String account);

    void inStoreSave(Map<String,Object> params);

    PageInfo<Map<String, Object>> inStoreFind(Map<String, Object> params);

    List<Map<String, Object>> inStoreList(Map<String, Object> params);

    void inStoreDelete(Map<String, Object> params);

    Integer getOutStoreOrder(String account);

    void outStoreSave(Map<String,Object> params);

    PageInfo<Map<String, Object>> outStoreFind(Map<String, Object> params);

    List<Map<String, Object>> outStoreList(Map<String, Object> params);

    void outStoreDelete(Map<String, Object> params);

    List<Map<String, Object>> productCodeList(Map<String,Object> params);

    void enterStoreSave(Map<String,Object> params);

    PageInfo<Map<String, Object>> storeList(Map<String, Object> params);

    void warningSave(Map<String,Object> params);

    void exitStoreSave(Map<String,Object> params);

    String generateOrder(int type);

    PageInfo<Map<String, Object>> enterRecord(Map<String, Object> params);

    PageInfo<Map<String, Object>> exitRecord(Map<String, Object> params);

    Map<String,Object> enterOrder(Map<String, Object> params);

    Map<String,Object> exitOrder(Map<String, Object> params);

    PageInfo<Map<String, Object>> storeCombineList(Map<String, Object> params);

    void storeTransfer(Map<String,Object> params);

    List<Map<String, Object>> storeTypeList(Map<String,Object> params);

    PageInfo<Map<String, Object>> storeDetail(Map<String, Object> params);

    Map<String,Object> productStoreDetail(Map<String, Object> params);

    List<Map<String, Object>> inStoreIsUse(Map<String, Object> params);

    List<Map<String, Object>> outStoreIsUse(Map<String, Object> params);

    List<Map<String, Object>> storeIsUse(Map<String, Object> params);

    void storeSplit(Map<String,Object> params);

    void storeMerge(Map<String,Object> params);

    PageInfo<Map<String, Object>> mergeList(Map<String, Object> params);

    List<Map<String, Object>> productIsUse(Map<String, Object> params);

    List<Map<String, Object>> customIsUse(Map<String, Object> params);

    List<Map<String, Object>> productList(Map<String, Object> params);

    Map<String,Object> findStoreInfo(int id);

}
