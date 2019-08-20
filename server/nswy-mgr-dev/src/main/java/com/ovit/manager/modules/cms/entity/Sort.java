package com.ovit.manager.modules.cms.entity;

import java.util.List;

/**
 * 分类
 *
 * @author haoWen
 * @create 2018-01-23 10:48
 **/

public class Sort {
    private String value;
    private String label;
    private List<children> children;

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

    public List<children> getChildren() {
        return children;
    }

    public void setChildren(List<children> children) {
        this.children = children;
    }
}
