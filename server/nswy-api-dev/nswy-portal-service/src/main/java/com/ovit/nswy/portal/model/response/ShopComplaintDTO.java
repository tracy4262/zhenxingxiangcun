package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by  wangxy
 */
@Data
public class ShopComplaintDTO {
    private Integer id;
    private String orderCodeId;
    private String seller;
    private String buyer;
    private String sellerAccount;
    private String buyerAccount;
    private String reason;
    private String describeInfo;
    private String mobile;
    private String picUrl;
    private String[] picList;

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    private Date createTime;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
