package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 代理
 * @author Administrator
 *
 */
public class ProxySetting {
    private Integer id;

    private String account;

    private String myProxy;

    private Date createTime;

    private String corpName;

    private String legalMan;

    private Integer approveStatus;

    private Date approveTime;

    private String approveAccount;

    private String attachment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMyProxy() {
        return myProxy;
    }

    public void setMyProxy(String myProxy) {
        this.myProxy = myProxy == null ? null : myProxy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getLegalMan() {
        return legalMan;
    }

    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan == null ? null : legalMan.trim();
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveAccount() {
        return approveAccount;
    }

    public void setApproveAccount(String approveAccount) {
        this.approveAccount = approveAccount == null ? null : approveAccount.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }
}