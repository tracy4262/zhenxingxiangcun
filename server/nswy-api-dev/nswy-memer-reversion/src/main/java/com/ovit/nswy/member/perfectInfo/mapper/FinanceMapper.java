package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FinanceMapper {

  List<Map<String,Object>> findBankAccountInfo(Map<String, Object> params);

  void saveBankAccountInfo(Map<String, Object> params);

  void deleteBankAccountInfo(Map<String, Object> params);

  Map<String,Object> findFaninceStatusInfo(Map<String, Object> params);

  void saveFaninceStatusInfo(Map<String, Object> params);


  List<Map<String,Object>> findAccountingSubjectsInfo(Map<String, Object> params);

  void saveAccountingSubjectsInfo(Map<String, Object> params);

  void deleteAccountingSubjectsInfo(Map<String, Object> params);



  List<Map<String,Object>> findIncomeDistributionInfo(Map<String, Object> params);


  void saveIncomeDistributionInfo(Map<String, Object> params);

  Map<String,Object> findLiabilitiesTableHeadInfo(Map<String, Object> params);

  void saveLiabilitiesTableHeadInfo(Map<String, Object> params);

  void deleteLiabilitiesTableHeadInfo(Map<String, Object> params);


  Map<String,Object> findLiabilitiesTableSignInfo(Map<String, Object> params);

  void saveLiabilitiesTableSignInfo(Map<String, Object> params);

  void deleteLiabilitiesTableSignInfo(Map<String, Object> params);


}
