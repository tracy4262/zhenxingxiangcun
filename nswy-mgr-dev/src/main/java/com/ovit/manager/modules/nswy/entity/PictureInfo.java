package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 图片
 *
 * @author haoWen
 * @create 2018-02-09 10:36
 **/
public class PictureInfo extends DataEntity<PictureInfo> {
    private String pictureId;
    private String pictureUrl;
    private String pictureName;
    private String pictureUseHome;
    private String pictureUseInformation;
    private String remark;
    private String createTime;
    private String updateTime;
    private String pictureProduct;


    public String getPictureProduct() {
        return pictureProduct;
    }

    public void setPictureProduct(String pictureProduct) {
        this.pictureProduct = pictureProduct;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUseHome() {
        return pictureUseHome;
    }

    public void setPictureUseHome(String pictureUseHome) {
        this.pictureUseHome = pictureUseHome;
    }

    public String getPictureUseInformation() {
        return pictureUseInformation;
    }

    public void setPictureUseInformation(String pictureUseInformation) {
        this.pictureUseInformation = pictureUseInformation;
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
        return "PictureInfo{" +
                "pictureId='" + pictureId + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", pictureName='" + pictureName + '\'' +
                ", pictureUseHome='" + pictureUseHome + '\'' +
                ", pictureUseInformation='" + pictureUseInformation + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
