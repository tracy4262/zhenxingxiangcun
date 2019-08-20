package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.BankAccount;

import java.util.Map;

public interface BankAccountService {
    BankAccount selectById(Integer id);

    BankAccount selectByAccount(String account);

    int insert(BankAccount account);

    boolean saveBankInfo(Map<String,Object> bankInfo);
}
