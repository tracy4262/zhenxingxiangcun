package com.ovit.nswy.relationship.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.relationship.model.request.DeleteFriendQuery;
import com.ovit.nswy.relationship.model.request.GetFriendRequestQuery;
import com.ovit.nswy.relationship.model.request.RelationshipFriendQuery;
import com.ovit.nswy.relationship.model.response.GetFriendRequestDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
public interface RelationshipFriendService {
    PageInfo<GetFriendRequestDTO> listRelationshipFriendRequest(GetFriendRequestQuery getFriendRequestQuery);
    Integer saveOrUpddate(RelationshipFriendQuery relationshipFriendQuery) throws ParamValidException;
    GetFriendRequestDTO getFriendRequest(Integer gruopId);
    Integer deleteRelationshipFriend(Integer friendId);
    Integer deleteFriend(DeleteFriendQuery deleteFriendQuery);
    Integer countFriendRequest(String account);
    Boolean getExistsFriend(String account,String fromAccount);

    List<String> queryFriendByAccount(String account);

    int deleteFriendByAccount(String account, String fromAccount);
}
