package com.ovit.nswy.relationship.model.response;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
public class GroupTypeDTO {

   private String gruopName;
    private Integer groupType;

    public GroupTypeDTO(Integer groupType, String gruopName){
        this.groupType=groupType;
        this.gruopName=gruopName;
    }

}
