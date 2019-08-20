package com.ovit.nswy.member.model;

/**
 * 种养信息
 *
 */
public class UserFarmInfo {
    private Integer id;

    private String account;

    private String species;

    private Integer status;

    private String farmlan;

    public String getFarmlan() {
        return farmlan;
    }

    public void setFarmlan(String farmlan) {
        this.farmlan = farmlan;
    }

    private String types;

    public String getType() {
        return types;
    }

    public void setType(String type) {
        this.types = type;
    }

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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species == null ? null : species.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}