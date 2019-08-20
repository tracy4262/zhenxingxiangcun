package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.mapper.StaffGatewayMapper;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.StaffGatewayService;
import com.ovit.nswy.member.util.TreeUtil;
import com.ovit.nswy.member.util.UuidUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StaffGatewayServiceImpl implements StaffGatewayService {

    @Autowired
    private StaffGatewayMapper staffGatewayMapper;

    @Autowired
    private LoginUserService loginUserService;


    @Override
    public List<Map<String,Object>> findGroupList(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        map.put("account", MapUtils.getString(params,"account"));
        map.put("templateId",MapUtils.getString(params,"templateId"));
        map.put("isDefault","0");
        List<Map<String,Object>> circleList = staffGatewayMapper.findRelationshipDictInfo(new HashMap<>());

        List<Map<String,Object>> circleTree = new TreeUtil().treeNodeList(circleList);

        List<Map<String,Object>> groupList = staffGatewayMapper.findGroupList(params);
        if(groupList.size() ==0 || groupList == null){
            for(Map<String,Object> circle :circleTree){
                if(MapUtils.getString(circle,"pid").equals("0") && MapUtils.getString(circle,"groupName").equals("工作圈")){
                    String id = UUID.randomUUID().toString().trim().replaceAll("-", "");
                    map.put("pid", MapUtils.getString(circle,"pid"));
                    map.put("id",id);
                    map.put("groupName",MapUtils.getString(circle,"groupName"));
                    staffGatewayMapper.saveGroup(map);
                    List<Map<String,Object>> list = (List<Map<String,Object>>) circle.get("children");
                    for(Map<String,Object> childMap : list){
                        childMap.put("pid",id);
                        childMap.put("id",UUID.randomUUID().toString().trim().replaceAll("-", ""));
                        childMap.put("isDefault","0");
                        childMap.put("account", MapUtils.getString(params,"account"));
                        childMap.put("templateId",MapUtils.getString(params,"templateId"));
                        staffGatewayMapper.saveGroup(childMap);
                    }
                }




            }
        }

        groupList = staffGatewayMapper.findGroupList(params);

        return new TreeUtil().treeNodeList(groupList);
    }

    @Override
    public void saveOrUpdateGroup(Map<String,Object> params){
        if(MapUtils.getString(params,"id").equals("0")){
            params.put("id", UUID.randomUUID().toString().trim().replaceAll("-", ""));

        }
        staffGatewayMapper.saveGroup(params);
    }

    @Override
    public void deleteGroup(Map<String,Object> params){
        staffGatewayMapper.deleteGroup(params);
    }



    @Override
    public PageList findGroupFriendList(Map<String,Object> params){
        //PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> groupMap = new HashMap<>();
        groupMap.put("account",MapUtils.getString(params,"account"));
        groupMap.put("templateId",MapUtils.getString(params,"templateId"));
        groupMap.put("groupId",MapUtils.getString(params,"groupId"));
        groupMap.put("keyword",MapUtils.getString(params,"keyword"));
        findForEachGroupFriend(list,groupMap);

        for (Map<String,Object> map : list) {
            LoginUser loginUser = loginUserService.findByLoginName(MapUtils.getString(map, "friendAccount"));
            //Map<String,Object> maps = new HashMap<>();
            //maps.put("account", MapUtils.getString(map, "groupFriendAccount"));
            map.put("session", loginUser);

        }



        return PageList.pageInfoList(params,list);
    }



    private void findForEachGroupFriend(List<Map<String,Object>> list,Map<String,Object> params){

        List<Map<String,Object>> friendlist = staffGatewayMapper.findGroupFriendList(params);
        if(friendlist.size() >0 && friendlist != null ){
            list.addAll(friendlist);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",MapUtils.getString(params,"groupId"));
        List<Map<String,Object>> groupList = staffGatewayMapper.findGroupById(map);
        if(groupList.size()>0){
            for(Map<String,Object> group:groupList){
                params.put("groupId",MapUtils.getString(group,"id"));
                findForEachGroupFriend(list,params);
            }

        }

    }

    /**
     * 同步更新非员工数据
     * @param params
     */
    @Override
    public void updateStaffOfIdentity(Map<String,Object> params){

        /*List<Map<String,Object>> friendlist = staffGatewayMapper.findGroupFriendList(params);
        for(Map<String,Object> map : friendlist){
            Map<String,Object> groupList = staffGatewayMapper.findMemberIdentityInfo(map);

            map.put("groupFriendAccountName",MapUtils.getString(groupList,"groupFriendAccountName"));
            map.put("phone",MapUtils.getString(groupList,"phone"));
            map.put("sex",MapUtils.getString(groupList,"sex"));
            map.put("card",MapUtils.getString(groupList,"card"));



        }*/
        staffGatewayMapper.updateStaffInfo(params);

    }



    @Override
    public void deleteGroupFriendInfo(Map<String,Object> params){

        Map<String,Object> map = new HashMap<>();
        map.put("id",MapUtils.getString(params,"groupId"));
        deleteNumber(map);
        staffGatewayMapper.deleteGroupFriendInfo(params);

    }

    /**
     * 添加成员
     * @param params
     */
    @Override
    public String insertNewStaff(Map<String,Object> params){
        String farmType = MapUtils.getString(params,"type");
        // 0 添加新账号
        if("0".equals(farmType)){
            params.put("unique_id", UuidUtil.get32UUID());
            String farmValue = MapUtils.getString(params,"idCard");
            String password=farmValue.substring(farmValue.length()-6,farmValue.length());
            password = new SimpleHash("SHA-1", Constants.SESSION_SALT, password).toString();
            params.put("loginPasswd",password);
            params.put("card",farmValue);
            params.put("user_type","1");
            Map<String,Object> param = new HashMap<>();
            param.put("friendAccount",MapUtils.getString(params,"friendAccount"));
            Map<String,Object> list = staffGatewayMapper.findLogin(param);
            if(MapUtils.isNotEmpty(list)){
                return "error";
            }else {
                staffGatewayMapper.insertLogin(params);

                staffGatewayMapper.insertGroupFriendInfo(params);
                Map<String,Object> map = new HashMap<>();
                map.put("id",MapUtils.getString(params,"groupId"));
                addNumber(map,false);
                return "success";
            }
        }else{
            String farmValue = new SimpleHash("SHA-1", Constants.SESSION_SALT, MapUtils.getString(params,"password")).toString();
            params.put("loginPasswd",farmValue);
            Map<String,Object> list = staffGatewayMapper.findLogin(params);
            if(MapUtils.isNotEmpty(list)){

                List<Map<String,Object>> farmList = staffGatewayMapper.findStaffGatewayGroup(params);
                if(farmList!=null && farmList.size()>0){
                    return "error1";
                }else{
                    staffGatewayMapper.insertGroupFriendInfo(params);
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",MapUtils.getString(params,"groupId"));
                    addNumber(map,false);
                    return "success";
                }

            }else {
                return "errors";
            }
        }

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
                map.put("isDefault","1");
                staffGatewayMapper.insertGroupFriendInfo(map);

                Map<String,Object> groupMap = new HashMap<>();
                groupMap.put("id",MapUtils.getString(params,"groupId"));

                addNumber(groupMap,true);
            }

        }else{
            employMap.put("groupName","工作圈");
            Map<String,Object> group = staffGatewayMapper.findGroupByGroupName(employMap);
            staffGatewayMapper.updateGroupStatus(group);
        }

    }

    private void deleteNumber(Map<String,Object> map){
        map.put("id",MapUtils.getString(map,"id"));
        List<Map<String,Object>> list = staffGatewayMapper.findGroupList(map);
        String number = MapUtils.getString(list.get(0),"number");
        String pid = MapUtils.getString(list.get(0),"pid");
        Integer count = Integer.parseInt(number)-1;
        map.put("number",count);
        staffGatewayMapper.updateGroup(map);
        if(!pid.equals("0")){
            map.put("pid",pid);
            Map<String,Object> pidMap = staffGatewayMapper.findGroupByPid(map);
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

        staffGatewayMapper.moveGroupFriendInfo(params);
    }

    private void addNumber(Map<String,Object> map,Boolean status){
        map.put("id",MapUtils.getString(map,"id"));
        List<Map<String,Object>> list = staffGatewayMapper.findGroupList(map);
        String number = MapUtils.getString(list.get(0),"number");
        String pid = MapUtils.getString(list.get(0),"pid");
        Integer count = Integer.parseInt(number)+1;
        map.put("number",count);
        staffGatewayMapper.updateGroup(map);
        if(status){
            staffGatewayMapper.updateGroupStatus(map);
        }
        if(!pid.equals("0")){
            map.put("pid",pid);
            Map<String,Object> pidMap = staffGatewayMapper.findGroupByPid(map);
            addNumber(pidMap,status);

        }

    }


}
