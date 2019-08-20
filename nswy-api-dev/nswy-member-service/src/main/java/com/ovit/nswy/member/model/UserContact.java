package com.ovit.nswy.member.model;

/**
 * 身份信息详情
 */
public class UserContact {
    private Integer id;

    private String nswyId;

    private String account;

    private Integer status2;

    private String qq;

    private String mobile;

    private String email;

    private String postalcode;

    private String domain;

    private Integer mobileStatus;

    private Integer postalcodeStatus;

    public Integer getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Integer mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public Integer getPostalcodeStatus() {
        return postalcodeStatus;
    }

    public void setPostalcodeStatus(Integer postalcodeStatus) {
        this.postalcodeStatus = postalcodeStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNswyId() {
        return nswyId;
    }

    public void setNswyId(String nswyId) {
        this.nswyId = nswyId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", nswyId='" + nswyId + '\'' +
                ", account='" + account + '\'' +
                ", status2=" + status2 +
                ", qq='" + qq + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", domain='" + domain + '\'' +
                ", mobileStatus=" + mobileStatus +
                ", postalcodeStatus=" + postalcodeStatus +
                '}';
    }
}