package com.ovit.nswy.member.model;

import java.util.Date;
import java.util.List;

/**
 * 专家信息
 */
public class ExpertInfo {
    private Integer id;

    private String loginAccount;

    private String expertName;

    private String expertType;

    private String adeptField;

    private String adeptSpecies;

    private String relatedSpecies;

    private String relatedIndustry;

    private String relatedProduct;

    private String relatedService;

    private String coordinate;

    private Integer approveStatus;

    private Date createTime;

    private List<String> listPic;

    private List<String> listHonor;

    private List<String> listCertificate;

    private String adeptId;

    private  String serviceId;

    private  String industryId;

    private  String productId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAdeptId() {
        return adeptId;
    }

    public void setAdeptId(String adeptId) {
        this.adeptId = adeptId;
    }

    private String speciesId;

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public List<String> getListPic() {
        return listPic;
    }

    public void setListPic(List<String> listPic) {
        this.listPic = listPic;
    }

    public List<String> getListHonor() {
        return listHonor;
    }

    public void setListHonor(List<String> listHonor) {
        this.listHonor = listHonor;
    }

    public List<String> getListCertificate() {
        return listCertificate;
    }

    public void setListCertificate(List<String> listCertificate) {
        this.listCertificate = listCertificate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    public String getAdeptField() {
        return adeptField;
    }

    public void setAdeptField(String adeptField) {
        this.adeptField = adeptField;
    }

    public String getAdeptSpecies() {
        return adeptSpecies;
    }

    public void setAdeptSpecies(String adeptSpecies) {
        this.adeptSpecies = adeptSpecies;
    }

    public String getRelatedSpecies() {
        return relatedSpecies;
    }

    public void setRelatedSpecies(String relatedSpecies) {
        this.relatedSpecies = relatedSpecies;
    }

    public String getRelatedIndustry() {
        return relatedIndustry;
    }

    public void setRelatedIndustry(String relatedIndustry) {
        this.relatedIndustry = relatedIndustry;
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }


}