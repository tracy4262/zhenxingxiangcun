package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 1
 *
 * @author haoWen
 * @create 2018-02-11 14:04
 **/

public class SystemDictInfo extends DataEntity<SystemDictInfo>{
    private String dictId;
    private String parentId;
    private String dictType;
    private String dictValue;
    private String dictName;
    private String pinyin;
    private String orderNo;
    private String remark;
    private String createTime;
    private String updateTime;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SystemDictInfo{" +
                "dictId='" + dictId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", dictType='" + dictType + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", dictName='" + dictName + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
