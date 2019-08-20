package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.BankAccountMapper;
import com.ovit.nswy.member.model.BankAccount;
import com.ovit.nswy.member.service.BankAccountService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountMapper BankAccountIao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BankAccount selectById(Integer id) {
        return BankAccountIao.selectById(id);
    }

    @Override
    public int insert(BankAccount account) {
        return BankAccountIao.insert(account);
    }

    /**
     * 保存账户信息
     * @param bankInfo
     */
    @Override
    public boolean saveBankInfo(Map<String, Object> bankInfo) {
        String code = MapUtils.getString(bankInfo,"code");
        String account = MapUtils.getString(bankInfo,"account");
        String phone = MapUtils.getString(bankInfo,"mobile");
        String bankCard = MapUtils.getString(bankInfo,"bankCard");
        String idCard = MapUtils.getString(bankInfo,"id");
        String password = MapUtils.getString(bankInfo,"password");
        String name = MapUtils.getString(bankInfo,"name");
        BankAccount bankAccountInfo = new BankAccount();
        bankAccountInfo.setBankaccount(bankCard);
        bankAccountInfo.setCreateTime(new Date());
        bankAccountInfo.setPhone(phone);
        bankAccountInfo.setAccount(account);
        bankAccountInfo.setIdcard(idCard);
        bankAccountInfo.setRealname(name);
        bankAccountInfo.setPassword(password);
        BankAccount historyBankInfo = BankAccountIao.selectByAccount(account);
        if (StringUtils.isBlank(code)) {
            //验证码为空，说明账户信息整个表单都不用填写
            if (historyBankInfo == null) {
                BankAccountIao.insert(bankAccountInfo);
            } else {
                BankAccountIao.updateByAccount(bankAccountInfo);
            }
            return true;

        } else {
            //判断验证码是否一样
            String randCode = redisTemplate.opsForValue().get(phone);
            if (StringUtils.equals(randCode, code)) {
                //填写的验证码正确
                if (historyBankInfo == null) {
                    BankAccountIao.insert(bankAccountInfo);
                } else {
                    BankAccountIao.updateByAccount(bankAccountInfo);
                }
                //插入数据之后删除缓存
                redisTemplate.delete(phone);
                return true;

            } else {
                //填写的验证码错误
                return false;
            }
        }
    }

    @Override
    public BankAccount selectByAccount(String account) {
        return BankAccountIao.selectByAccount(account);
    }
}
