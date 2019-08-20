package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.member.mapper.FollowManageMapper;
import com.ovit.nswy.member.mapper.RelationshipCircleMapper;
import com.ovit.nswy.member.mapper.StaffGatewayMapper;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.RelationshipCircleService;
import com.ovit.nswy.member.util.TreeUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationshipCircleServiceImpl implements RelationshipCircleService {

    @Autowired
    private RelationshipCircleMapper relationshipCircleMapper;

    @Autowired
    private FollowManageMapper followManageMapper;

    @Autowired
    private StaffGatewayMapper staffGatewayMapper;

    @Value("${register.image}")
    private String image;


    @Override
    public List<Map<String,Object>> findGroupList(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        map.put("account", MapUtils.getString(params,"account"));
        map.put("templateId",MapUtils.getString(params,"templateId"));
        map.put("isDefault","0");
        List<Map<String,Object>> circleList = relationshipCircleMapper.findRelationshipDictInfo(new HashMap<>());

        List<Map<String,Object>> circleTree = new TreeUtil().treeNodeList(circleList);

        List<Map<String,Object>> groupList = relationshipCircleMapper.findGroupList(params);
        if(groupList.size() ==0 || groupList == null){
            for(Map<String,Object> circle :circleTree){
                String id = UUID.randomUUID().toString().trim().replaceAll("-", "");
                map.put("pid", MapUtils.getString(circle,"pid"));
                map.put("id",id);
                map.put("groupName",MapUtils.getString(circle,"groupName"));
                relationshipCircleMapper.saveGroup(map);
                List<Map<String,Object>> list = (List<Map<String,Object>>) circle.get("children");
                for(Map<String,Object> childMap : list){
                    childMap.put("pid",id);
                    childMap.put("id",UUID.randomUUID().toString().trim().replaceAll("-", ""));
                    childMap.put("isDefault","0");
                    childMap.put("account", MapUtils.getString(params,"account"));
                    childMap.put("templateId",MapUtils.getString(params,"templateId"));
                    relationshipCircleMapper.saveGroup(childMap);
                }


            }
        }

        groupList = relationshipCircleMapper.findGroupList(params);

        return new TreeUtil().treeNodeList(groupList);
    }

    @Override
    public void saveOrUpdateGroup(Map<String,Object> params){
        if(MapUtils.getString(params,"id").equals("0")){
            params.put("id", UUID.randomUUID().toString().trim().replaceAll("-", ""));

        }
        relationshipCircleMapper.saveGroup(params);
    }

    @Override
    public void deleteGroup(Map<String,Object> params){
        relationshipCircleMapper.deleteGroup(params);
    }


    @Override
    public PageInfo<Map<String,Object>> findLoginByCircle(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));

        List<Map<String,Object>> list = new ArrayList<>();

        if(MapUtils.getString(params,"memberClass").equals("专家")){
            list =  followManageMapper.findExpertInfo(params);
        }else{

            list = followManageMapper.findLoginByMember(params);
        }

        Map<String,Object> group = new HashMap<>();

        group.put("account",MapUtils.getString(params,"account"));


        List<Map<String,Object>> memberInfo  = relationshipCircleMapper.findGroupFriendInfo(group);

        for(Map<String,Object> follow :list){
            for(Map<String,Object> map : memberInfo){
                if(MapUtils.getString(follow,"account").equals(MapUtils.getString(map,"friendAccount"))){
                    if(MapUtils.getString(map,"invite").equals("0")){
                        follow.put("followType","1");//已邀请
                    }else{
                        follow.put("followType","2");//已接受
                    }


                }

            }

        }

        return new PageInfo<>(list);
    }


    @Override
    public PageList findGroupFriendList(Map<String,Object> params){
        //PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list = new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("0")){
            Map<String,Object> groupMap = new HashMap<>();
            groupMap.put("account",MapUtils.getString(params,"account"));
            groupMap.put("templateId",MapUtils.getString(params,"templateId"));
            groupMap.put("groupId",MapUtils.getString(params,"groupId"));
            groupMap.put("keyword",MapUtils.getString(params,"keyword"));
            groupMap.put("invite",MapUtils.getString(params,"invite"));
            findForEachGroupFriend(list,groupMap);

        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("groupFriendAccount",MapUtils.getString(params,"account"));
            map.put("invite",MapUtils.getString(params,"invite"));
            list= relationshipCircleMapper.findGroupFriendList(map);
        }
        return PageList.pageInfoList(params,list);
    }

    @Override
    public List<Map<String,Object>> findGroupFriendOfIm(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();

        //查询得到所有的好友
        List<Map<String,Object>> memberInfo  = relationshipCircleMapper.findGroupFriendInfo(params);
        String id = "";
        Map<String,Object> friendGroup = new HashMap<>();
        Map<String,Object> workGroup = new HashMap<>();
        List<Map<String,Object>> friendLists = new ArrayList<>();
        List<Map<String,Object>> workLists = new ArrayList<>();
        friendGroup.put("id",0);
        friendGroup.put("groupname","好友圈");
        workGroup.put("id",1);
        workGroup.put("groupname","工作圈");
        if(memberInfo.size()>0){
            for(Map<String,Object> map : memberInfo){

                params.put("groupId",MapUtils.getString(map,"groupId"));
                Map<String,Object> groupFriend = new HashMap<>();
                groupFriend.put("id",MapUtils.getString(map,"groupId"));
                findParentId(groupFriend);
                Map<String,Object> login = relationshipCircleMapper.findLoginByAccount(map);
                //findForEachGroupFriend(list,params);
                login.put("username",MapUtils.getString(map,"memberName"));
                //用户自身 初始化时默认在线
                login.put("status", "online");//在线状态 online：在线、hide：隐身
                //用户好友 默认离线
                //user.put("status", "offline");//在线状态 若值为offline代表离线，online或者不填为在线

                if(StringUtils.isBlank(MapUtils.getString(login,"avatar"))){
                    login.put("avatar",image);
                }

                if(MapUtils.getString(groupFriend,"groupname").equals("好友圈")){
                    friendLists.add(login);
                    friendGroup.put("groupname",MapUtils.getString(groupFriend,"groupname"));
                    if(MapUtils.getString(groupFriend,"pid").equals("0")){
                        friendGroup.put("id",MapUtils.getString(groupFriend,"id"));
                    }else{
                        friendGroup.put("id",MapUtils.getString(groupFriend,"pid"));
                    }

                    friendGroup.put("list",friendLists);

                }
                if(MapUtils.getString(groupFriend,"groupname").equals("工作圈")){
                    workLists.add(login);
                    workGroup.put("groupname",MapUtils.getString(groupFriend,"groupname"));
                    if(MapUtils.getString(groupFriend,"pid").equals("0")){
                        workGroup.put("id",MapUtils.getString(groupFriend,"id"));
                    }else{
                        workGroup.put("id",MapUtils.getString(groupFriend,"pid"));
                    }

                    workGroup.put("list",workLists);

                }

            }
        }else{

            friendGroup.put("list",friendLists);

            workGroup.put("list",workLists);


        }

        list.add(friendGroup);
        list.add(workGroup);

        return list;
    }

    private void findParentId(Map<String,Object> map){
        map.put("id",MapUtils.getString(map,"id"));
        List<Map<String,Object>> list = relationshipCircleMapper.findGroupList(map);
        String pid = MapUtils.getString(list.get(0),"pid");

        if(!pid.equals("0")){
            map.put("pid",pid);
            Map<String,Object> pidMap = relationshipCircleMapper.findGroupByPid(map);
            if(MapUtils.getString(pidMap,"pid").equals("0")){
                map.put("groupname",MapUtils.getString(pidMap,"groupName"));
            }
            findParentId(pidMap);
        }else{
            map.put("pid",pid);
            map.put("groupname",MapUtils.getString(list.get(0),"groupName"));
        }

    }

    private void findForEachGroupFriend(List<Map<String,Object>> list,Map<String,Object> params){
        List<Map<String,Object>> fruendlist = relationshipCircleMapper.findGroupFriendList(params);
        if(fruendlist.size() >0 && fruendlist != null ){
            list.addAll(fruendlist);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",MapUtils.getString(params,"groupId"));
        List<Map<String,Object>> groupList = relationshipCircleMapper.findGroupById(map);
        if(groupList.size()>0){
            for(Map<String,Object> group:groupList){
                params.put("groupId",MapUtils.getString(group,"id"));
                findForEachGroupFriend(list,params);
            }

        }

    }


    @Override
    public void insertGroupFriendInfo(Map<String,Object> params){
        if(MapUtils.getString(params,"invite").equals("1")){
            //邀请人
            Map<String,Object> map = new HashMap<>();
            map.put("id",MapUtils.getString(params,"inviteGroupId"));
            addNumber(map,false);
            //被邀请人
            Map<String,Object> beMap = new HashMap<>();
            beMap.put("id",MapUtils.getString(params,"beInvitedGroupId"));
            addNumber(beMap,false);
            relationshipCircleMapper.updateGroupFriendInfo(params);

            params.put("memberName",MapUtils.getString(params,"groupFriendAccountName"));//被邀请人名字
        }

        relationshipCircleMapper.insertGroupFriendInfo(params);


    }

    private void addNumber(Map<String,Object> map,Boolean status){
        map.put("id",MapUtils.getString(map,"id"));
        List<Map<String,Object>> list = relationshipCircleMapper.findGroupList(map);
        String number = MapUtils.getString(list.get(0),"number");
        String pid = MapUtils.getString(list.get(0),"pid");
        Integer count = Integer.parseInt(number)+1;
        map.put("number",count);
        relationshipCircleMapper.updateGroup(map);
        if(status){
            relationshipCircleMapper.updateGroupStatus(map);
        }
        if(!pid.equals("0")){
            map.put("pid",pid);
            Map<String,Object> pidMap = relationshipCircleMapper.findGroupByPid(map);
            addNumber(pidMap,status);


        }

    }

    @Override
    public void deleteGroupFriendInfo(Map<String,Object> params){
        //邀请人
        Map<String,Object> map = new HashMap<>();
        map.put("id",MapUtils.getString(params,"groupId"));
        deleteNumber(map);
        relationshipCircleMapper.deleteGroupFriendInfo(params);

        //被邀请人
        Map<String,Object> beInvitemap = new HashMap<>();
        beInvitemap.put("account",MapUtils.getString(params,"groupFriendAccount"));
        beInvitemap.put("groupFriendAccount",MapUtils.getString(params,"account"));
        List<Map<String,Object>> list = relationshipCircleMapper.findGroupFriendList(beInvitemap);
        params.put("id",MapUtils.getString(list.get(0),"id"));

        Map<String,Object> beMap = new HashMap<>();
        beMap.put("id",MapUtils.getString(list.get(0),"groupId"));
        deleteNumber(beMap);
        relationshipCircleMapper.deleteGroupFriendInfo(params);
    }

    @Override
    public void deleteFriendInfo(Map<String,Object> params){
        relationshipCircleMapper.deleteGroupFriendInfo(params);
    }


    private void deleteNumber(Map<String,Object> map){
        map.put("id",MapUtils.getString(map,"id"));
        List<Map<String,Object>> list = relationshipCircleMapper.findGroupList(map);
        String number = MapUtils.getString(list.get(0),"number");
        String pid = MapUtils.getString(list.get(0),"pid");
        Integer count = Integer.parseInt(number)-1;
        map.put("number",count);
        relationshipCircleMapper.updateGroup(map);
        if(!pid.equals("0")){
            map.put("pid",pid);
            Map<String,Object> pidMap = relationshipCircleMapper.findGroupByPid(map);
            deleteNumber(pidMap);


        }

    }

    @Override
    public void moveGroupFriendInfo(Map<String,Object> params){
        /**
         * 更新原先数据的人数， 在更新移动后的人数
         */
        Map<String,Object> map = new HashMap<>();
        map.put("id",MapUtils.getString(params,"oldGroupId"));

        deleteNumber(map);

        Map<String,Object> moveMap = new HashMap<>();
        moveMap.put("id",MapUtils.getString(params,"newGroupId"));
        addNumber(moveMap,false);

        relationshipCircleMapper.moveGroupFriendInfo(params);
    }

    @Override
    public void insertStaff(Map<String,Object> params){
        Map<String,Object> employMap = new HashMap<>();
        employMap.put("yearId",MapUtils.getString(params,"yearId"));
        employMap.put("account",MapUtils.getString(params,"account"));
        employMap.put("templateId",MapUtils.getString(params,"templateId"));

        List<Map<String,Object>> employList = staffGatewayMapper.findEmployeeRoster(employMap);
        if(employList.size()>0){
            for(Map<String,Object> map :employList){
                map.put("groupId",MapUtils.getString(params,"groupId"));
                map.put("invite","1");
                relationshipCircleMapper.insertGroupFriendInfo(map);

                Map<String,Object> groupMap = new HashMap<>();
                groupMap.put("id",MapUtils.getString(params,"groupId"));

                addNumber(groupMap,true);

                //将用户添加为员工好友
                Map<String,Object> friendMap = new HashMap<>();
                friendMap.put("account",MapUtils.getString(map,"friendAccount"));
                friendMap.put("templateId","0");
                friendMap.put("isDefault","0");
                findGroupList(friendMap);
                friendMap.put("groupName","工作圈");
                Map<String,Object> group = relationshipCircleMapper.findGroupByGroupName(friendMap);
                Map<String,Object> newGroupFriend = new HashMap<>();
                newGroupFriend.put("groupId",MapUtils.getString(group,"id"));
                newGroupFriend.put("invite","1");

                newGroupFriend.put("account",MapUtils.getString(map,"friendAccount"));
                newGroupFriend.put("friendAccount",MapUtils.getString(map,"account"));
                relationshipCircleMapper.insertGroupFriendInfo(newGroupFriend);


            }

        }else{
            employMap.put("groupName","工作圈");
            Map<String,Object> group = relationshipCircleMapper.findGroupByGroupName(employMap);
            relationshipCircleMapper.updateGroupStatus(group);
        }


    }

}
