package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by  wangxy
 */
@Data
public class ShopOrderCommentDTO {
    private Integer id;
    private Integer fromAccount;
    private String account;
    private String realName;
    private String orderCodeId;
    private Integer commodityTypeInfoId;
    private Integer reputation;
    private Integer star;
    private String describeInfo;
    private String picUrl;
    private Integer approveStatus;
    private Date createTime;
    private Date updateTime;


}
