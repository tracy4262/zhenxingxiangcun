package com.ovit.nswy.relationship.service;

import com.ovit.nswy.relationship.model.response.ImUserDTO;
import com.ovit.nswy.relationship.model.response.InitImDTO;
import com.ovit.nswy.relationship.model.response.MemberDTO;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-12-6.
 */
public interface GroupFriendService {

    public InitImDTO getInitList(String  account);
    public MemberDTO getMembers(String account);
    public List<Integer> getUserIdByGroupId(String account, Integer groupId);
    public ImUserDTO getUserByAccount(Integer account);
    public List<Integer> getUserIdByGroupId(Integer groupId);

}
