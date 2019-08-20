package com.ovit.manager.modules.cms.entity;

/**
 * 服务
 *
 * @author haoWen
 * @create 2018-01-25 11:03
 **/

public class ServiceResultBean {
    private String pinyin;
    private String value;
    private String label;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
