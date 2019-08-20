package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.BankAccount;

public interface BankAccountMapper {

    BankAccount selectById(Integer id);

    BankAccount selectByAccount(String account);

    int insert(BankAccount account);

    void updateByAccount(BankAccount bankInfo);

}
