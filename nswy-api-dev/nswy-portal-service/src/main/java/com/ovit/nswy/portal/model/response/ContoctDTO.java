package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ContoctDTO {

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String loginAccount ;
    /**
     * 企业法人
     */
    @ApiModelProperty(value = "企业法人")
    private String legalPerson;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String corpName;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 座机
     */
    @ApiModelProperty(value = "座机")
    private String phone;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;
    /**
     *地址
     */
    @ApiModelProperty(value = "地址")
    private String addr;
    /**
     *信用编码
     */
    @ApiModelProperty(value = "信用编码")
    private String postalCode;

    /**
     *
     */
    private String coordinate;

    private String qq;

    private Integer nameStatus;
    private Integer phoneStatus;
    private Integer addrStatus;
    private Integer coordinateStatus;
    private Integer status;
}
