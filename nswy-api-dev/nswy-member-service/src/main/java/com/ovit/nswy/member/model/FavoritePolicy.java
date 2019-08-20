package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 关注政策
 * @author Administrator
 *
 */
public class FavoritePolicy {
	private Integer id;

    private String account;

    private Integer referSpecies;

    private Integer referService;

    private Integer referProduct;

    private Date createTime;

    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getReferSpecies() {
        return referSpecies;
    }

    public void setReferSpecies(Integer referSpecies) {
        this.referSpecies = referSpecies;
    }

    public Integer getReferService() {
        return referService;
    }

    public void setReferService(Integer referService) {
        this.referService = referService;
    }

    public Integer getReferProduct() {
        return referProduct;
    }

    public void setReferProduct(Integer referProduct) {
        this.referProduct = referProduct;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }
}