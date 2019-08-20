package com.ovit.nswy.relationship.model.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class AgencyDTO {

    private Integer agencyId;
    /**
     * 代理机构类型
     */
    private Integer agencyType;

    /**
     * 被代理机构全称
     */
    private String agencyName;

    /**
     * 社会信用统一代码
     */
    private String creditCode;

    /**
     * 法定代表人姓名
     */
    private String legalPerson;

    /**
     * 机构联系方式
     */
    private String contactWay;

    /**
     * 机构注册地址
     */
    private String registeredAddress;

    /**
     * 代理协议图片
     */
    private String agreementImage;

    /**
     * 营业执照图片
     */
    private String licenseImage;

    /**
     * 法人代表身份证
     */
    private String legalIdcardImage;

    /**
     * 代理人身份证
     */
    private String agentIdcardImage;

    /**
     * 被代理账号
     */
    private String agentAccount;

    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
