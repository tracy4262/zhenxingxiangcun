package com.ovit.nswy.relationship.manager;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Service
public class UserManager {

    @Autowired
    private UserFeign userFeign;

    public UserDTO getUserDTO(GetUserQuery getUserQuery){
        Object rows= userFeign.getUserByQuery(getUserQuery);
        JSONObject jsonObject= JSONObject.parseObject(rows.toString());
        UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
        return userDTO;
    }

}
