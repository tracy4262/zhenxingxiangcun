package com.ovit.nswy.member.common.enums;

public enum CertificationStepEnum {

    // 利用构造函数传参
    STEP_AUTHENTICATION(1),
    STEP_COLUMNSETTINGS(2),
    STEP_INDIVICATIONSETTINGS(3),
    STEP_APPSETTINGS(4),
    STEP_PEFECTINFO(5),
    STEP_WEBSITESETTINGS(6);

    // 定义私有变量
    private int stepNum;

    // 构造函数，枚举类型只能为私有
    CertificationStepEnum(int stepNum) {
        this.stepNum = stepNum;
    }

    public int getStepNum() {
        return stepNum;
    }

}
