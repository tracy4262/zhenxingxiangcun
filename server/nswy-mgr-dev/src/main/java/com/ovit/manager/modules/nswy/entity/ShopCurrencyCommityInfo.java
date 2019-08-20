package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

public class ShopCurrencyCommityInfo extends DataEntity<ShopCurrencyCommityInfo> {

    private Integer shopId;


    private String account;


    private String approveStatus;

    private Date createTime;

    private Date updateTime;

    private String commonProductName;//通用商品名


    private String relatedIndustry;//行业


    private String productTypeName;//产品

    private String relatedSpeciesId;

    private String relatedSpeciesName;//物种


    private String displayName;//用户名


    private String commonProductPinyin;

    private String commodityAlias;

    private String refuseReason;

    private String remark;

    private String quality;

    private String safety;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCommonProductName() {
        return commonProductName;
    }

    public void setCommonProductName(String commonProductName) {
        this.commonProductName = commonProductName;
    }

    public String getRelatedIndustry() {
        return relatedIndustry;
    }

    public void setRelatedIndustry(String relatedIndustry) {
        this.relatedIndustry = relatedIndustry;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getRelatedSpeciesId() {
        return relatedSpeciesId;
    }

    public void setRelatedSpeciesId(String relatedSpeciesId) {
        this.relatedSpeciesId = relatedSpeciesId;
    }

    public String getRelatedSpeciesName() {
        return relatedSpeciesName;
    }

    public void setRelatedSpeciesName(String relatedSpeciesName) {
        this.relatedSpeciesName = relatedSpeciesName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCommonProductPinyin() {
        return commonProductPinyin;
    }

    public void setCommonProductPinyin(String commonProductPinyin) {
        this.commonProductPinyin = commonProductPinyin;
    }

    public String getCommodityAlias() {
        return commodityAlias;
    }

    public void setCommodityAlias(String commodityAlias) {
        this.commodityAlias = commodityAlias;
    }
}
