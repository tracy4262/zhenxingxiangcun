package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.mapper.GroupMapper;
import com.ovit.nswy.relationship.model.Relationship.FriendList;
import com.ovit.nswy.relationship.model.Relationship.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;
import com.ovit.nswy.relationship.service.GroupService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关系圈分组service实现
 *
 * @author haoWen
 * @create 2018-03-05 14:22
 **/
@Service
public class GroupServiceIml implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserFeign userFeign;
    @Override
    public List<RelationshipGroup> queryGroupList(RelationshipGroupListQuery relationshipGroupListQuery) {
        return groupMapper.queryGroupList(relationshipGroupListQuery);
    }

    @Override
    public List<FriendList> queryFriendList(String groupId) {
        List<FriendList> friendLists = groupMapper.queryFriendList(groupId);
        List<FriendList> lists = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(friendLists)) {
            for (FriendList friendList : friendLists) {
                Map<String,Object> map = new HashMap<>();
                map.put("account",friendList.getAccount());
                map.put("fromAccount",friendList.getFromAccount());
                Object data = userFeign.getRealNameAndStatus(map);
                JSONObject jsonObject = JSONObject.parseObject(data.toString());
                Map<String,Object> statusMap = (Map<String,Object>)jsonObject.get("data");
                friendList.setStatus(MapUtils.getString(statusMap,"status"));
                friendList.setDisplayName(MapUtils.getString(statusMap,"realName"));
                friendList.setAvatar(MapUtils.getString(statusMap,"avatar"));
                friendList.setUserType(MapUtils.getString(statusMap,"userType"));
                lists.add(friendList);
            }
        }
        return lists;
    }
}
