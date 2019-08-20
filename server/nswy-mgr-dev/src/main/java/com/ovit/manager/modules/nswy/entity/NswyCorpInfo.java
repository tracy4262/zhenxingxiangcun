package com.ovit.manager.modules.nswy.entity;

import java.util.Date;

import com.ovit.manager.common.persistence.DataEntity;

public class NswyCorpInfo extends DataEntity<NswyCorpInfo> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2546756764196198561L;
	private Integer corpInfoId;
    private String loginAccount;

    private String corpName;

    private String businessScope;

    private String creditCode;

    private String legalPerson;

    private String mobile;

    private String phone;

    private String identificationCard;

    private String email;

    private String identificationCardUrl;

    private Date createTime;

    private Integer approveStatus;

    private String legalPersonIntroduce;

    private String legalPersonUrl;

    private Double registeredCapital;

    private String establishDate;

    private String busnissTerm;

    private String industryType;

    private String location;

    private String companyType;

    private String logoUrl;

    private String businessLicenseUrl;


    private String coordinate;
    private String relatedSpecies;

    private String relatedProduct;

    private String relatedService;

    private String companyProfile;

    private String isHomeDisplay;

    private  String time;

    private String remark;

    private String addrDetail;

    private String isToPage;

    private  String isNavDisplay;

    private String orderNum;

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(String isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCorpInfoId() {
        return corpInfoId;
    }

    public void setCorpInfoId(Integer corpInfoId) {
        this.corpInfoId = corpInfoId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificationCardUrl() {
        return identificationCardUrl;
    }

    public void setIdentificationCardUrl(String identificationCardUrl) {
        this.identificationCardUrl = identificationCardUrl;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getLegalPersonIntroduce() {
        return legalPersonIntroduce;
    }

    public void setLegalPersonIntroduce(String legalPersonIntroduce) {
        this.legalPersonIntroduce = legalPersonIntroduce;
    }

    public String getLegalPersonUrl() {
        return legalPersonUrl;
    }

    public void setLegalPersonUrl(String legalPersonUrl) {
        this.legalPersonUrl = legalPersonUrl;
    }

    public Double getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Double registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getBusnissTerm() {
        return busnissTerm;
    }

    public void setBusnissTerm(String busnissTerm) {
        this.busnissTerm = busnissTerm;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(String relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    public String getRelatedProduct() {
        return relatedProduct;
    }

    public void setRelatedProduct(String relatedProduct) {
        this.relatedProduct = relatedProduct;
    }

    public String getRelatedService() {
        return relatedService;
    }

    public void setRelatedService(String relatedService) {
        this.relatedService = relatedService;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsHomeDisplay() {
        return isHomeDisplay;
    }

    public void setIsHomeDisplay(String isHomeDisplay) {
        this.isHomeDisplay = isHomeDisplay;
    }
}