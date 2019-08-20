package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

public class ShopCommityInfo extends DataEntity<ShopCommityInfo> {

    private Integer shopId;

    private String commodityTypeInfoId;

    private String account;

    private String isFaithCommitment;

    private String faithCommitment;

    private Integer isNavDisplay;

    private Integer isShopDisplay;

    private String approveStatus;

    private Date createTime;

    private Date updateTime;

    private String commonProductName;//通用商品名

    private String commodityName;

    private String salesMarket;//销售市场

    private String relatedIndustry;//行业

    private String productType;//产品

    private String productTypeName;//产品

    private String relatedSpeciesId;

    private String relatedSpeciesName;//物种

    private String varietyId;

    private String breedName;//品种名称

    private String displayName;//用户名

    private String isHomeDisplay;//是否显示在首页

    private String orderNum;//排序

    private String timePrice;//价格

    private String commonProductPinyin;

    private String commodityAlias;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCommodityAlias() {
        return commodityAlias;
    }

    public void setCommodityAlias(String commodityAlias) {
        this.commodityAlias = commodityAlias;
    }

    public String getCommonProductPinyin() {
        return commonProductPinyin;
    }

    public void setCommonProductPinyin(String commonProductPinyin) {
        this.commonProductPinyin = commonProductPinyin;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getTimePrice() {
        return timePrice;
    }

    public void setTimePrice(String timePrice) {
        this.timePrice = timePrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getCommonProductName() {
        return commonProductName;
    }

    public void setCommonProductName(String commonProductName) {
        this.commonProductName = commonProductName;
    }

    public String getSalesMarket() {
        return salesMarket;
    }

    public void setSalesMarket(String salesMarket) {
        this.salesMarket = salesMarket;
    }

    public String getRelatedIndustry() {
        return relatedIndustry;
    }

    public void setRelatedIndustry(String relatedIndustry) {
        this.relatedIndustry = relatedIndustry;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIsHomeDisplay() {
        return isHomeDisplay;
    }

    public void setIsHomeDisplay(String isHomeDisplay) {
        this.isHomeDisplay = isHomeDisplay;
    }

    public String getCommodityTypeInfoId() {
        return commodityTypeInfoId;
    }

    public void setCommodityTypeInfoId(String commodityTypeInfoId) {
        this.commodityTypeInfoId = commodityTypeInfoId == null ? null : commodityTypeInfoId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getIsFaithCommitment() {
        return isFaithCommitment;
    }

    public void setIsFaithCommitment(String isFaithCommitment) {
        this.isFaithCommitment = isFaithCommitment == null ? null : isFaithCommitment.trim();
    }

    public String getFaithCommitment() {
        return faithCommitment;
    }

    public void setFaithCommitment(String faithCommitment) {
        this.faithCommitment = faithCommitment == null ? null : faithCommitment.trim();
    }

    public Integer getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(Integer isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public Integer getIsShopDisplay() {
        return isShopDisplay;
    }

    public void setIsShopDisplay(Integer isShopDisplay) {
        this.isShopDisplay = isShopDisplay;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
