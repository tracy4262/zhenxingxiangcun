package com.ovit.nswy.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NameLibraryMapper {

    List<Map<String,Object>> findWikiConllectionList(Map<String,Object> params);

    void saveWikiLibraryInfo(Map<String,Object> params);

    void deleteWikiLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findVarietyList(Map<String,Object> params);

    void saveVarietyLibraryInfo(Map<String,Object> params);

    void deleteVarietyLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findDiseaseList(Map<String,Object> params);

    void saveDiseaseLibraryInfo(Map<String,Object> params);

    void deleteDiseaseLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findPestList(Map<String,Object> params);

    void savePestLibraryInfo(Map<String,Object> params);

    void deletePestLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findCurrencyCommodityList(Map<String,Object> params);

    void saveCurrencyCommodityInfo(Map<String,Object> params);

    void deleteCurrencyCommodityLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findShopCurrencyCommodity(Map<String,Object> params);

    List<Map<String,Object>> findCurrencyServiceList(Map<String,Object> params);

    void saveCurrencyServiceInfo(Map<String,Object> params);

    void deleteCurrencyServiceLibraryInfo(Map<String,Object> params);



}
