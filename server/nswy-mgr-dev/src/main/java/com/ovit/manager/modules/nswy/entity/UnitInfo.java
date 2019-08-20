package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 单位
 *
 * @author haoWen
 * @create 2018-02-09 10:36
 **/
public class UnitInfo extends DataEntity<UnitInfo> {


    private Integer unitId;

    private String unitName;

    private String unitType;

    private String calculateUnit;

    private String modulus;

    private String unitTypeName;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }
}
