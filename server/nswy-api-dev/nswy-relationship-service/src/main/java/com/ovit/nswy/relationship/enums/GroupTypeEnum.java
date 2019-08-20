package com.ovit.nswy.relationship.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
public enum GroupTypeEnum {

    WORK_TYPE(1,"工作圈"),
    FRIEND_TYPE(2,"好友圈"),
    PUBLIC_TYPE(3,"公众圈");
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private GroupTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public enum   WorkGroupTypeEnum{
        WORK_GROUP__ONE(1,"员工"),
        WORK_GROUP_TWO(2,"合作社社员"),
        WORK_GROUP_THREE(3,"乡村村民"),
        WORK_GROUP_FOUR(4,"附属单位"),
        WORK_GROUP_FIVE(5,"家庭成员");
        @Getter
        @Setter
        private int code;
        @Getter
        @Setter
        private String name;

        private WorkGroupTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

    }

    public enum   FriendGroupTypeEnum{

        FRIEND_GROUP__ONE(1,"商品卖方"),
        FRIEND_GROUP_TWO(2,"商品供应方"),
        FRIEND_GROUP_THREE(3,"家庭成员"),
        FRIEND_GROUP_FOUR(4,"服务供应方"),
        FRIEND_GROUP_FIVE(5,"服务需求方"),
        FRIEND_GROUP_SEX(6,"专家");

        @Getter
        @Setter
        private int code;
        @Getter
        @Setter
        private String name;

        private FriendGroupTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

    }

    enum   PublicGroupTypeEnum{


    }


    public static String GroupTypeEscape(int s){
        String info="";
        switch (s){
            case 1:info=GroupTypeEnum.WORK_TYPE.getName();break;
            case 2:info=GroupTypeEnum.FRIEND_TYPE.getName();break;
            case 3:info=GroupTypeEnum.PUBLIC_TYPE.getName();break;
        }
        return info;
    }

}
