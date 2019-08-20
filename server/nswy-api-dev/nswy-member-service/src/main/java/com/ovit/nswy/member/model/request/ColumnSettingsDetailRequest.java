package com.ovit.nswy.member.model.request;

public class ColumnSettingsDetailRequest {

    private boolean status;
    private String name;
    private int authority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }



}
