package com.ovit.nswy.relationship.manager;

/**
 * Created by ${huipei.x} on 2017-12-28.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.request.ListFollowQuery;
import com.ovit.nswy.relationship.model.response.FansDTO;
import com.ovit.nswy.relationship.model.response.GetFriendRequestDTO;
import com.ovit.nswy.relationship.model.response.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FriendRequestManager {
    @Autowired
    private UserFeign userFeign;

    public List<GetFriendRequestDTO> getFriendRequest(List<GetFriendRequestDTO> list,Integer followType){
        ListFollowQuery listFollowQuery=new ListFollowQuery();
        if(Integers.isNotEmpty(followType)) {
            switch (followType) {
                case 1:
                  list.stream().filter(friendRequestDTO->StringUtil.isNotBlank(friendRequestDTO.getAccount())).map(friendRequestDTO->{
                                listFollowQuery.setAccount(friendRequestDTO.getAccount());
                                listFollowQuery.setFollowType(1);
                                Object data = userFeign.listfollow(listFollowQuery);
                                JSONObject jsonObject = JSONObject.parseObject(data.toString());
                                List<FansDTO> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), FansDTO.class);
                                List<String> stringList=Lists.newArrayList();
                                stringList=objectList.stream().filter(fansDTO -> StringUtil.isNotBlank(fansDTO.getAccount()))
                                .map(fansDTO -> fansDTO.getAccount())
                                .collect(Collectors.toList());
                                if (stringList.contains(friendRequestDTO.getFromAccount())) {
                                    friendRequestDTO.getFollowInfo().put("id", 1);
                                    friendRequestDTO.getFollowInfo().put("val", "已关注");
                                }else {
                                    friendRequestDTO.getFollowInfo().put("id", 2);
                                    friendRequestDTO.getFollowInfo().put("val", "未关注");
                                }
                                return friendRequestDTO;
                            }).collect(Collectors.toList());
                    break;
                case 2:
                   list.stream().map(friendRequestDTO -> {
                            listFollowQuery.setAccount(friendRequestDTO.getFromAccount());
                            listFollowQuery.setFromAccount(friendRequestDTO.getAccount());
                            listFollowQuery.setFollowType(2);
                            Object data = userFeign.listfollow(listFollowQuery);
                            JSONObject jsonObject = JSONObject.parseObject(data.toString());
                            List<FansDTO> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), FansDTO.class);
                            if (Lists.isNotEmpty(objectList)) {
                                friendRequestDTO.getMutualGollowInfo().put("id", 1);
                                friendRequestDTO.getMutualGollowInfo().put("val", "互相关注");
                            } else {
                                friendRequestDTO.getMutualGollowInfo().put("id", 2);
                                friendRequestDTO.getMutualGollowInfo().put("val", "非互相关注");
                            }
                            return friendRequestDTO;
                        }).collect(Collectors.toList());
                    break;

                case 3:
                   list.stream().filter(friendRequestDTO -> StringUtil.isNotBlank(friendRequestDTO.getFromAccount()))
                        .map(friendRequestDTO -> {
                            GetUserQuery getUserQuery=new GetUserQuery();
                            getUserQuery.setQueryType(1);
                            getUserQuery.setAccount(friendRequestDTO.getFromAccount());
                            Object data = userFeign.getUserByQuery(getUserQuery);
                            JSONObject jsonObject = JSONObject.parseObject(data.toString());
                            UserDTO userDTO = JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
                            friendRequestDTO.setAvatar(userDTO.getAvatar());
                            friendRequestDTO.setDisplayName(userDTO.getDisplayName());
                            return friendRequestDTO;
                        }).collect(Collectors.toList());
                    break;
                default:

            }
        }
     return list;
    }
}
