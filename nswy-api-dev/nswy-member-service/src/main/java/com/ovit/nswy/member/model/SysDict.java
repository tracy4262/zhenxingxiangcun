package com.ovit.nswy.member.model;


import java.io.Serializable;

/**
 * 数据字典
 * @author Administrator
 *
 */
public class SysDict implements Serializable{

    private String label;

    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}