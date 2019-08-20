package com.ovit.nswy.relationship.model.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by ${huipei.x} on 2018-1-2.
 */
@Data
public class ImGroupDTO {

    private int id;
    /** 在线人数 */
    private int online;

    /** 分组名称 */
    private String groupname;
    private String avatar;
    private List<ImUserDTO> list= Lists.newArrayList();
}
