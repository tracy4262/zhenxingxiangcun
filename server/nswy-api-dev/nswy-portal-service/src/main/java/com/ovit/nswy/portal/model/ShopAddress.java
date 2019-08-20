package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_address")
public class ShopAddress {
    @Id
    private Integer id;

    /**
     * 用户编号
     */
    private String account;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址所在区域
     */
    @Column(name = "add_area")
    private String addArea;

    /**
     * 详细地址
     */
    @Column(name = "add_detail")
    private String addDetail;

    /**
     * 生成基础地址详细
     */
    @Column(name = "add_info")
    private String addInfo;

    /**
     * 地址别名
     */
    @Column(name = "add_alias")
    private String addAlias;

    /**
     * 默认
     */
    @Column(name = "is_default")
    private Boolean isDefault;

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
     * 获取联系人
     *
     * @return linkman - 联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 设置联系人
     *
     * @param linkman 联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取电话
     *
     * @return telephone - 电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置电话
     *
     * @param telephone 电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取地址所在区域
     *
     * @return add_area - 地址所在区域
     */
    public String getAddArea() {
        return addArea;
    }

    /**
     * 设置地址所在区域
     *
     * @param addArea 地址所在区域
     */
    public void setAddArea(String addArea) {
        this.addArea = addArea == null ? null : addArea.trim();
    }

    /**
     * 获取详细地址
     *
     * @return add_detail - 详细地址
     */
    public String getAddDetail() {
        return addDetail;
    }

    /**
     * 设置详细地址
     *
     * @param addDetail 详细地址
     */
    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail == null ? null : addDetail.trim();
    }

    /**
     * 获取生成基础地址详细
     *
     * @return add_info - 生成基础地址详细
     */
    public String getAddInfo() {
        return addInfo;
    }

    /**
     * 设置生成基础地址详细
     *
     * @param addInfo 生成基础地址详细
     */
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo == null ? null : addInfo.trim();
    }

    /**
     * 获取地址别名
     *
     * @return add_alias - 地址别名
     */
    public String getAddAlias() {
        return addAlias;
    }

    /**
     * 设置地址别名
     *
     * @param addAlias 地址别名
     */
    public void setAddAlias(String addAlias) {
        this.addAlias = addAlias == null ? null : addAlias.trim();
    }

    /**
     * 获取默认
     *
     * @return is_default - 默认
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置默认
     *
     * @param isDefault 默认
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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