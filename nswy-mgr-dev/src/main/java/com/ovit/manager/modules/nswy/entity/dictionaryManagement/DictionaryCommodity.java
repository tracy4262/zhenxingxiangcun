package com.ovit.manager.modules.nswy.entity.dictionaryManagement;

import com.ovit.manager.common.persistence.DataEntity;

public class DictionaryCommodity extends DataEntity<DictionaryCommodity> {
    private String id;
    private String name;
    private Integer level;
    private String value;
    private String remark;
    private String pid;
    private String createTime;
    private String relatedSpeciesName;
    private String pinyin;
    private String alias;
    private String industryTypeId;//行业分类
    private String industryTypeName;
    private String relatedSpeciesId;//关联物种
    private String type;
    private String account;
    private String status;
    private String serviceTypeId;//服务分类
    private String serviceTypeName;
    private String productTypeId;//产品分类
    private String productTypeName;
    private String traceabilityType;//追溯类型
    private String isMainProduct;

    public String getIsMainProduct() {
        return isMainProduct;
    }

    public void setIsMainProduct(String isMainProduct) {
        this.isMainProduct = isMainProduct;
    }

    public String getTraceabilityType() {
        return traceabilityType;
    }

    public void setTraceabilityType(String traceabilityType) {
        this.traceabilityType = traceabilityType;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndustryTypeName() {
        return industryTypeName;
    }

    public void setIndustryTypeName(String industryTypeName) {
        this.industryTypeName = industryTypeName;
    }


    public String getRelatedSpeciesId() {
        return relatedSpeciesId;
    }

    public void setRelatedSpeciesId(String relatedSpeciesId) {
        this.relatedSpeciesId = relatedSpeciesId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIndustryTypeId() {
        return industryTypeId;
    }

    public void setIndustryTypeId(String industryTypeId) {
        this.industryTypeId = industryTypeId;
    }

    public String getRelatedSpeciesName() {
        return relatedSpeciesName;
    }

    public void setRelatedSpeciesName(String relatedSpeciesName) {
        this.relatedSpeciesName = relatedSpeciesName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
