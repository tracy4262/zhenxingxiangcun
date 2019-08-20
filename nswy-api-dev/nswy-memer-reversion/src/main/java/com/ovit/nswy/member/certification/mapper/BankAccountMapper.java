package com.ovit.nswy.member.certification.mapper;


import com.ovit.nswy.member.certification.model.BankAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BankAccountMapper {

    BankAccount selectById(Integer id);

    BankAccount selectByAccount(Map<String,Object> params);

    int insert(BankAccount account);

    void updateByAccount(BankAccount bankInfo);

}
