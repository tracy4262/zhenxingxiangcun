package com.ovit.nswy.member.certification.service;


import com.ovit.nswy.member.certification.model.BankAccount;

import java.util.Map;

public interface BankAccountService {

    BankAccount selectById(Integer id);

    BankAccount selectByAccount(Map<String,Object> accountInfo);

    int insert(BankAccount account);

    boolean saveBankInfo(Map<String, Object> bankInfo);
}
