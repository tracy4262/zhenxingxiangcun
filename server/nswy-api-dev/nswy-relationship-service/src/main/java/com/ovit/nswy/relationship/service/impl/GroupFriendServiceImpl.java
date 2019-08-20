package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.frame.common.util.ContextHolderUtil;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.mapper.ImGroupMapper;
import com.ovit.nswy.relationship.mapper.RelationshipFriendMapper;
import com.ovit.nswy.relationship.mapper.RelationshipGroupMapper;
import com.ovit.nswy.relationship.model.ImGroup;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.response.*;
import com.ovit.nswy.relationship.service.GroupFriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.List;


/**
 * Created by ${huipei.x} on 2017-12-6.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GroupFriendServiceImpl extends BaseService<RelationshipFriend> implements GroupFriendService {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private RelationshipGroupMapper relationshipGroupMapper;
    @Autowired
    RelationshipFriendMapper relationshipFriendMapper;
    @Autowired
    private ImGroupMapper imGroupMapper;

    @Override
    public InitImDTO getInitList(String  account){
        ContextHolderUtil.getSession().setAttribute("username", account);
        List<RelationshipGroup> groupList = Lists.newArrayList();
        List<RelationshipFriend> friendList = Lists.newArrayList();
        List<ImGroupDTO> imGroupDTOList=Lists.newArrayList();
        List<ImGroupDTO> imGroupDTOOneList=Lists.newArrayList();
        List<ImGroupDTO> imGroupList=Lists.newArrayList();
        Example exampl = new Example(ImGroup.class);
        exampl.createCriteria().andEqualTo("parentId", 0);
        List<ImGroup> imGroups = imGroupMapper.selectByExample(exampl);
        if(!exitsInitAdd(account)){
            initAdd(imGroups,account);
        }
        for(ImGroup imGroup:imGroups){
            ImGroupDTO imGroupO = new ImGroupDTO();
            imGroupO.setGroupname(imGroup.getGroupName());
            imGroupO.setId(imGroup.getImGroupId());
            imGroupO.setAvatar(imGroup.getAvatar());
            imGroupList.add(imGroupO);
        }
        ImUserDTO imUserDTO =getImUserDTO(account);
        Example example = new Example(RelationshipGroup.class);
        example.createCriteria().andEqualTo("account", account).andEqualTo("parentId", 0);
        List<RelationshipGroup> groupParentList = relationshipGroupMapper.selectByExample(example);
        for(RelationshipGroup relationshipGroup:groupParentList){
            ImGroupDTO imGroupDTOOne = new ImGroupDTO();
            imGroupDTOOne.setGroupname(relationshipGroup.getGruopName());
            imGroupDTOOne.setId(relationshipGroup.getGruopId());
            imGroupDTOOneList.add(imGroupDTOOne);
            Example exampleOne = new Example(RelationshipGroup.class);
            exampleOne.createCriteria().andEqualTo("account", account).andEqualTo("parentId", relationshipGroup.getGruopId());
            groupList = relationshipGroupMapper.selectByExample(exampleOne);
            for(RelationshipGroup group:groupList){
                ImGroupDTO imGroupDTO = new ImGroupDTO();
                imGroupDTO.setGroupname(group.getGruopName());
                imGroupDTO.setId(group.getGruopId());
                imGroupDTOList.add(imGroupDTO);
                }
            }
            for(ImGroupDTO imgroup:imGroupDTOList){
                List<ImUserDTO> imUserDTOList=Lists.newArrayList();
                Example exampleTwo = new Example(RelationshipFriend.class);
                exampleTwo.createCriteria().andEqualTo("account", account).andEqualTo("groupId", imgroup.getId());
                friendList = this.findByExample(exampleTwo);
                for(RelationshipFriend relationshipFriend:friendList){
                    ImUserDTO imUser =getImUserDTO(relationshipFriend.getFromAccount());
                    imUserDTOList.add(imUser);
            }
                if(Lists.isNotEmpty(imUserDTOList)){
                    imgroup.setList(imUserDTOList);
                }else{
                    imgroup.setList(null);
                }


        }
        InitImDTO initImDTO=new InitImDTO();
        initImDTO.setMine(imUserDTO);
        initImDTO.setFriend(imGroupDTOList);
        initImDTO.setGroup(imGroupList);
        return initImDTO;
    }

    @Override
    public MemberDTO getMembers(String account){
        List<RelationshipGroup> groupList = Lists.newArrayList();
        List<ImUserDTO> imUserDTOList=Lists.newArrayList();
        List<ImUserDTO> imUserDTOOneList=Lists.newArrayList();
        List<Integer> groupIdList=Lists.newArrayList();
        ImUserDTO imUser=getImUserDTO(account);
        String ids="";
        String idOnes="";
        Example example = new Example(RelationshipGroup.class);
        example.createCriteria().andEqualTo("account", account).andEqualTo("parentId", 0);
        List<RelationshipGroup> groupParentList = relationshipGroupMapper.selectByExample(example);
        for(RelationshipGroup relationshipGroup:groupParentList){
                ids +=relationshipGroup.getGruopId()+",";
            groupIdList.add(relationshipGroup.getGruopId());
        }
        ids = ids.substring(0, ids.length()-1);
        List<RelationshipFriend> friendList= relationshipFriendMapper.findByIds(ids);
        for(RelationshipFriend relationshipFriend:friendList){
            ImUserDTO imUserOne=getImUserDTO(relationshipFriend.getFromAccount());
            imUserDTOList.add(imUserOne);
        }
        MemberDTO memberDTO=new MemberDTO();
        memberDTO.setOwner(imUser);
        memberDTO.setMembers(imUserDTOList.size());
        memberDTO.setList(imUserDTOList);
        return memberDTO;
    }

    @Override
    public List<Integer> getUserIdByGroupId(String account, Integer groupId){
        List<ImUserDTO> imUserDTOList=Lists.newArrayList();
        List<Integer> userIdList=Lists.newArrayList();
        List<Integer> parentIdList=Lists.newArrayList(0);
        Example example = new Example(ImGroup.class);
        example.createCriteria().andEqualTo("imGroupId", groupId).andEqualTo("parentId", 0);
        List<ImGroup> imGroupList=imGroupMapper.selectByExample(example);
        if(Lists.isNotEmpty(imGroupList)){
            Integer  parentid=imGroupList.get(0).getImGroupId();
            Example examp = new Example(ImGroup.class);
            ImUserDTO  imUserone=getUserByAccount(Integer.valueOf(account));
            examp.createCriteria().andEqualTo("account", imUserone.getAccount()).andEqualTo("parentId", parentid).andNotIn("parentId", parentIdList);
            List<ImGroup> imList=imGroupMapper.selectByExample(examp);
            if(Lists.isNotEmpty(imGroupList)){
                Example exampl = new Example(RelationshipFriend.class);
                exampl.createCriteria().andEqualTo("groupId", imList.get(0).getImGroupId());
                List<RelationshipFriend> friendList=this.findByExample(exampl);
                for(RelationshipFriend relationshipFriend:friendList){
                    ImUserDTO imUser=getImUserDTO(relationshipFriend.getFromAccount());
                    imUserDTOList.add(imUser);
                }
                for(ImUserDTO imUserDTO:imUserDTOList){
                    userIdList.add(imUserDTO.getId()) ;
                }
            }
        }
        return userIdList;
    }

    @Override
    public List<Integer> getUserIdByGroupId(Integer groupId){
        List<ImUserDTO> imUserDTOList=Lists.newArrayList();
        List<Integer> userIdList=Lists.newArrayList();
        Example example = new Example(RelationshipFriend.class);
        example.createCriteria().andEqualTo("groupId", groupId);
        List<RelationshipFriend> friendList=this.findByExample(example);
        for(RelationshipFriend relationshipFriend:friendList){
            ImUserDTO imUser=getImUserDTO(relationshipFriend.getFromAccount());
            imUserDTOList.add(imUser);
        }
        for(ImUserDTO imUserDTO:imUserDTOList){
            userIdList.add(imUserDTO.getId()) ;
        }

        return userIdList;
    }

    @Override
    public ImUserDTO getUserByAccount(Integer account){
        GetUserQuery getUserQuery = new GetUserQuery();
        getUserQuery.setQueryType(2);
        getUserQuery.setId(account);
        UserDTO userDTOOne=getUserDTO(getUserQuery);
        ImUserDTO imUserOne = new ImUserDTO();
        imUserOne.setId(userDTOOne.getId());
        imUserOne.setAccount(userDTOOne.getLoginAccount());
        imUserOne.setAvatar(userDTOOne.getAvatar());
        imUserOne.setSign(userDTOOne.getSignaTure());
        imUserOne.setUsername(userDTOOne.getDisplayName());
        return imUserOne;

    }

    private UserDTO getUserDTO(GetUserQuery getUserQuery){
        Object rows= userFeign.getUserByQuery(getUserQuery);
        JSONObject jsonObject= JSONObject.parseObject(rows.toString());
        UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
        return userDTO;
    }

    private ImUserDTO getImUserDTO(String account){
        GetUserQuery getUserQuery = new GetUserQuery();
        getUserQuery.setQueryType(1);
        getUserQuery.setAccount(account);
        UserDTO userDTOOne=getUserDTO(getUserQuery);
        ImUserDTO imUserOne = new ImUserDTO();
        imUserOne.setId(userDTOOne.getId());
        imUserOne.setAccount(userDTOOne.getLoginAccount());
        if(StringUtil.isNotBlank(userDTOOne.getAvatar())){
            imUserOne.setAvatar(userDTOOne.getAvatar());
        }else{
            imUserOne.setAvatar("//192.168.7.61/00/49/wKgHPVpxbE6AdReyAAAGcMX5A0w481.png");
        }

        imUserOne.setSign(userDTOOne.getSignaTure());
        if(StringUtil.isNotBlank(userDTOOne.getDisplayName())){
            imUserOne.setUsername(userDTOOne.getDisplayName());
        }else{
            imUserOne.setUsername("未实名认证");
        }
        return imUserOne;
    }

    private Integer initAdd( List<ImGroup> imGroups,String account){
        Integer result=null;
        Example exampl = new Example(RelationshipGroup.class);
        exampl.createCriteria().andEqualTo("parentId", 0).andEqualTo("account", account);
        List<RelationshipGroup> relationshipGroupList = relationshipGroupMapper.selectByExample(exampl);
            ImGroup imGroup1=new ImGroup();
            for(ImGroup imGroup:imGroups ){
                for(RelationshipGroup group:relationshipGroupList) {
                    if(imGroup.getGroupName().equals(group.getGruopName())){
                        imGroup1.setImGroupId(group.getGruopId());
                        imGroup1.setParentId(imGroup.getImGroupId());
                        imGroup1.setAccount(account);
                        result=  imGroupMapper.insert(imGroup1);
                    }

                }
            }
        return    result;


    }

    private boolean  exitsInitAdd(String account){
      List<Integer> integers=Lists.newArrayList(0);
        Example exampl = new Example(ImGroup.class);
        exampl.createCriteria().andNotIn("parentId", integers).andEqualTo("account", account);
       List<ImGroup>  imGroupList=imGroupMapper.selectByExample(exampl);
        if(Lists.isNotEmpty(imGroupList)){
            return true;
        }else{
            return false;
        }
    }
}
