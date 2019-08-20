package com.ovit.shop.inventory.mapper;

import java.util.List;
import java.util.Map;

public interface BasicSettingMapper {

    List<Map<String, Object>> customFind(Map<String, Object> params);

    void customSave(Map<String,Object> params);

    List<Map<String, Object>> productFind(Map<String, Object> params);

    List<Map<String, Object>> productCodeIsUse(Map<String,Object> params);

    void productSave(Map<String,Object> params);

    Map<String, Object> findProductClassify(Map<String, Object> params);

    Map<String,Object> findCommonCommodity(Map<String,Object> params);

    void productDelete(Map<String,Object> params);

    void customDelete(Map<String,Object> params);

    Integer getStoreOrder(String account);

    void storeSave(Map<String,Object> params);

    List<Map<String, Object>> storeFind(Map<String, Object> params);

    void storeDelete(Map<String,Object> params);

    void billSave(Map<String,Object> params);

    Map<String,Object> billFind(Map<String, Object> params);

    Integer getInStoreOrder(String account);

    void inStoreSave(Map<String,Object> params);

    List<Map<String, Object>> inStoreFind(Map<String, Object> params);

    List<Map<String, Object>> inStoreWithSystem(Map<String, Object> params);

    Integer getOutStoreOrder(String account);

    void outStoreSave(Map<String,Object> params);

    List<Map<String, Object>> outStoreFind(Map<String, Object> params);

    List<Map<String, Object>> outStoreWithSystem(Map<String, Object> params);

    void inStoreDelete(Map<String, Object> params);

    void outStoreDelete(Map<String, Object> params);

    void enterStoreSave(Map<String,Object> params);

    Map<String,Object> storeManageFind(Map<String,Object> params);

    void storeManageSave(Map<String,Object> params);

    void overrideStoreManageSave(Map<String,Object> params);

    void storeManageUpdate(Map<String,Object> params);

    String getMemberName(String account);

    List<Map<String, Object>> manageFind(Map<String, Object> params);

    void warningSave(Map<String,Object> params);

    void exitStoreSave(List<Map<String,Object>> list);

    void outStoreManageUpdate(List<Map<String,Object>> list);

    List<Map<String, Object>> enterRecord(Map<String, Object> params);

    List<Map<String, Object>> exitRecord(Map<String, Object> params);

    Map<String,Object> enterOrder(Map<String, Object> params);

    List<Map<String, Object>> exitOrder(Map<String, Object> params);

    List<Map<String, Object>> storeCombineList(Map<String, Object> params);

    void transferInSave(List<Map<String,Object>> list);

    void transferOutSave(List<Map<String,Object>> list);

    List<Map<String, Object>> storeTypeList(Map<String,Object> params);

    List<Map<String, Object>> storeDetail(Map<String, Object> params);

    String getStoreTypeName(Map<String, Object> params);

    Map<String,Object> productDetail(Map<String, Object> params);

    String inStoreNumber(Map<String, Object> params);

    String outStoreNumber(Map<String, Object> params);

    List<Map<String, Object>> inStoreIsUse(Map<String, Object> params);

    List<Map<String, Object>> outStoreIsUse(Map<String, Object> params);

    List<Map<String, Object>> storeIsUse(Map<String, Object> params);

    void splitOutSave(Map<String,Object> params);

    void mergeRecordSave(List<Map<String,Object>> list);

    List<Map<String, Object>> mergeList(Map<String, Object> params);

    void mergeRecordUpdate(Map<String,Object> params);

    List<Map<String, Object>> productIsUse(Map<String, Object> params);

    List<Map<String, Object>> customIsUse(Map<String, Object> params);

    Map<String,Object> findMallClass(Map<String, Object> params);

    List<Map<String, Object>> productListOfByName(Map<String, Object> params);

    Map<String,Object> findCommodityById(Map<String, Object> params);

    Map<String,Object> findStoreInfo(int id);

}
