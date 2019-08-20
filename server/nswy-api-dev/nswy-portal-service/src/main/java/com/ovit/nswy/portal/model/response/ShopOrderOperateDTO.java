package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by  wangxy
 */
@Data
public class ShopOrderOperateDTO {
    private Integer id;
    private Integer fromAccount;
    private Integer type;
    private String account;
    private String realName;
    private String orderCodeId;
    private String reason;
    private String describeInfo;
    private String picUrl;
    private Integer approveStatus;
    private Date createTime;
    private Date updateTime;
    private String[] picList;

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
