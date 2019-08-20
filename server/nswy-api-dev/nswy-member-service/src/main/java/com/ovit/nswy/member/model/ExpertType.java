package com.ovit.nswy.member.model;


import java.io.Serializable;

/**
 * 数据字典
 * @author Administrator
 *
 */
public class ExpertType implements Serializable{

    private String label;

    private String value;

    private String pid;

    private String id;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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