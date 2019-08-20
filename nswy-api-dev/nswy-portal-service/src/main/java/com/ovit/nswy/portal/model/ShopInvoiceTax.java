package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_invoice_tax")
public class ShopInvoiceTax {
    @Id
    private Integer id;



    /**
     * 用户编号
     */
    private String account;

    /**
     * 单位名称
     */
    @Column(name = "unit_name")
    private String unitName;

    /**
     * 纳税人识别码
     */
    @Column(name = "identification_code")
    private String identificationCode;

    /**
     * 注册地址
     */
    @Column(name = "register_address")
    private String registerAddress;

    /**
     * 注册电话
     */
    @Column(name = "register_telephone")
    private String registerTelephone;

    /**
     * 开户银行
     */
    @Column(name = "account_bank")
    private String accountBank;

    /**
     * 银行账户
     */
    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }



    /**
     * 获取用户编号
     *
     * @return account - 用户编号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户编号
     *
     * @param account 用户编号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取单位名称
     *
     * @return unit_name - 单位名称
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 设置单位名称
     *
     * @param unitName 单位名称
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * 获取纳税人识别码
     *
     * @return identification_code - 纳税人识别码
     */
    public String getIdentificationCode() {
        return identificationCode;
    }

    /**
     * 设置纳税人识别码
     *
     * @param identificationCode 纳税人识别码
     */
    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode == null ? null : identificationCode.trim();
    }

    /**
     * 获取注册地址
     *
     * @return register_address - 注册地址
     */
    public String getRegisterAddress() {
        return registerAddress;
    }

    /**
     * 设置注册地址
     *
     * @param registerAddress 注册地址
     */
    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress == null ? null : registerAddress.trim();
    }

    /**
     * 获取注册电话
     *
     * @return register_telephone - 注册电话
     */
    public String getRegisterTelephone() {
        return registerTelephone;
    }

    /**
     * 设置注册电话
     *
     * @param registerTelephone 注册电话
     */
    public void setRegisterTelephone(String registerTelephone) {
        this.registerTelephone = registerTelephone == null ? null : registerTelephone.trim();
    }

    /**
     * 获取开户银行
     *
     * @return account_bank - 开户银行
     */
    public String getAccountBank() {
        return accountBank;
    }

    /**
     * 设置开户银行
     *
     * @param accountBank 开户银行
     */
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank == null ? null : accountBank.trim();
    }

    /**
     * 获取银行账户
     *
     * @return bank_account - 银行账户
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置银行账户
     *
     * @param bankAccount 银行账户
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}