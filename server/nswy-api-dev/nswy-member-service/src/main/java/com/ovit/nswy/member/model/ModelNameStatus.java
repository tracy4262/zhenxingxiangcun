package com.ovit.nswy.member.model;

import java.io.Serializable;

public class ModelNameStatus implements Serializable {

    private String model;

    private String name;

    private boolean status;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}