package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface FinanceService {

    Map<String,Object> findBankAccountInfo(Map<String, Object> params);

    void saveBankAccountInfo(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);

    void deleteBankAccountInfo(Map<String, Object> params);


    Map<String,Object> findAccountingSubjectsInfo(Map<String, Object> params);

    void saveAccountingSubjectsInfo(Map<String, Object> params);

    String deleteAccountingSubjectsInfo(Map<String, Object> params);

    Map<String,Object> findIncomeDistributionInfo(Map<String,Object> params);

    void saveIncomeDistributionInfo(Map<String, Object> params);

    void saveLiabilitiesTableInfo(Map<String,Object> params);


}
