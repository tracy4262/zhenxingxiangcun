package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

public class RealName extends DataEntity<RealName> {
    private String realId;
    private String userId;
    private String memberClass;         //会员分类
    private String memberName;          //会员全称
    private String memberNamePinyin;    //全称拼音
    private String memberAbbreviation;  //名称简写
    private String abbreviationPinyin;  //简称拼音
    private String aptitudeName;        //资质名称
    private String aptitudeNumber;      //资质编号
    private String aptitudeImage;       //资质照片
    private String remark;              //说明

    private String contactName;         //联系人姓名
    private String card;                //身份证号
    private String seatPhone;           //座机电话
    private String phone;               //手机号码
    private String qqNumber;            //QQ号码
    private String wechatNumber;       //微信账号
    private String email;               //邮箱
    private String websiteUrl;          //网站地址
    private String postalCode;          //邮政编码
    private String image;               //个人照片
    private String location;            //所在位置
    private String address;             //详细地址
    private String houseNumber;         //门牌号

    private String name;                //姓名
    private String sex;                 //性别
    private String pesonPhone;
    private String pesonCard;
    private String job;                 //职务
    private String jobTitle;            //职称
    private String pesonEmail;
    private String duty;                //职责
    private String cardImage;           //身份证照片

    private String adminName;                //姓名
    private String adminSex;                 //性别
    private String adminPhone;
    private String adminCard;
    private String adminJob;                 //职务
    private String adminJobTitle;            //职称
    private String adminEmail;
    private String adminDuty;                //职责
    private String adminCardImage;           //身份证照片
    private String empowerImage;        //授权证书

    private String templateId;
    private String locationId;
    private String status; //0 删除   1

    public String getPesonPhone() {
        return pesonPhone;
    }

    public void setPesonPhone(String pesonPhone) {
        this.pesonPhone = pesonPhone;
    }

    public String getPesonCard() {
        return pesonCard;
    }

    public void setPesonCard(String pesonCard) {
        this.pesonCard = pesonCard;
    }

    public String getPesonEmail() {
        return pesonEmail;
    }

    public void setPesonEmail(String pesonEmail) {
        this.pesonEmail = pesonEmail;
    }

    public String getAdminSex() {
        return adminSex;
    }

    public void setAdminSex(String adminSex) {
        this.adminSex = adminSex;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminCard() {
        return adminCard;
    }

    public void setAdminCard(String adminCard) {
        this.adminCard = adminCard;
    }

    public String getAdminJob() {
        return adminJob;
    }

    public void setAdminJob(String adminJob) {
        this.adminJob = adminJob;
    }

    public String getAdminJobTitle() {
        return adminJobTitle;
    }

    public void setAdminJobTitle(String adminJobTitle) {
        this.adminJobTitle = adminJobTitle;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminDuty() {
        return adminDuty;
    }

    public void setAdminDuty(String adminDuty) {
        this.adminDuty = adminDuty;
    }

    public String getAdminCardImage() {
        return adminCardImage;
    }

    public void setAdminCardImage(String adminCardImage) {
        this.adminCardImage = adminCardImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberClass() {
        return memberClass;
    }

    public void setMemberClass(String memberClass) {
        this.memberClass = memberClass;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNamePinyin() {
        return memberNamePinyin;
    }

    public void setMemberNamePinyin(String memberNamePinyin) {
        this.memberNamePinyin = memberNamePinyin;
    }

    public String getMemberAbbreviation() {
        return memberAbbreviation;
    }

    public void setMemberAbbreviation(String memberAbbreviation) {
        this.memberAbbreviation = memberAbbreviation;
    }

    public String getAbbreviationPinyin() {
        return abbreviationPinyin;
    }

    public void setAbbreviationPinyin(String abbreviationPinyin) {
        this.abbreviationPinyin = abbreviationPinyin;
    }

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
    }

    public String getAptitudeNumber() {
        return aptitudeNumber;
    }

    public void setAptitudeNumber(String aptitudeNumber) {
        this.aptitudeNumber = aptitudeNumber;
    }

    public String getAptitudeImage() {
        return aptitudeImage;
    }

    public void setAptitudeImage(String aptitudeImage) {
        this.aptitudeImage = aptitudeImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSeatPhone() {
        return seatPhone;
    }

    public void setSeatPhone(String seatPhone) {
        this.seatPhone = seatPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getEmpowerImage() {
        return empowerImage;
    }

    public void setEmpowerImage(String empowerImage) {
        this.empowerImage = empowerImage;
    }
}