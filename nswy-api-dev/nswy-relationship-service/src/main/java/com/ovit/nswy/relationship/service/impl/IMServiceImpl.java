package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.mapper.RelationshipFriendMapper;
import com.ovit.nswy.relationship.mapper.RelationshipGroupMapper;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.response.UserDTO;
import com.ovit.nswy.relationship.service.IMService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;



import java.util.*;

/**
 * DESCRIPTION:
 * im业务数据查询处理
 * @author zouyan
 * @create 2018-01-23 上午10:03
 * created by fuck~
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class IMServiceImpl implements IMService {

    private static final Logger logger = LogManager.getLogger(IMServiceImpl.class);

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RelationshipGroupMapper relationshipGroupMapper;

    @Autowired
    private RelationshipFriendMapper relationshipFriendMapper;

    /**
     * <p>Description:</p>
     * 初始化我的信息、好友列表、群组列表
     * 好友分组、及分组下对应的好友获取方式如下：
     * 通过用户account查询relationship_group获取最上层gruop_id、gruop_name
     * （注：gruop_id单词拼写）
     * 通过用户account、最上层group_id查询relationship_friend
     * （agree_type = 2，同意添加好友）
     * 我的信息获取方式：
     * 远程调用接口获取
     * <p>返回参数（详见resources，im-demo示例数据）：<br>
     * <table border="1" width="200px">
     *  <tr><td><b>code</b></td><td>:</td><td>成功、失败标识，0为成功</td></tr>
     *  <tr><td><b>msg</b></td><td>:</td><td>失败信息</td></tr>
     *  <tr><td><b>data</b></td><td>:</td><td>初始化数据信息</td></tr>
     * </table>
     * @param account
     * @return Map<String, Object>
     * @author zouyan
     * @Date 2018-01-23 上午9:55
     */
    @Override
    public Map<String, Object> getInitList(String account) {
        /**
         获取我的信息
         示例数据格式
         "mine": {
         "username": "纸飞机" //我的昵称
         ,"id": "100000" //我的ID
         ,"status": "online" //在线状态 online：在线、hide：隐身
         ,"sign": "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名
         ,"avatar": "a.jpg" //我的头像
         }
         */
        Map<String, Object> mineMap = getImUser(account, true);//mine
        /**
         "friend": [{
             "groupname": "前端码屌" //好友分组名
             ,"id": 1 //分组ID
             ,"list": [{ //分组下的好友列表
             "username": "贤心" //好友昵称
             ,"id": "100001" //好友ID
             ,"avatar": "a.jpg" //好友头像
             ,"sign": "这些都是测试数据，实际使用请严格按照该格式返回" //好友签名
             ,"status": "online" //若值为offline代表离线，online或者不填为在线
             }, …… ]
             }, …… ]
         */
        List<Map<String, Object>> friendMap = new ArrayList<>();//friend
        /**
         "group": [{
             "groupname": "前端群" //群组名
             ,"id": "101" //群组ID
             ,"avatar": "a.jpg" //群组头像
             }, …… ]
         */


        Map<String,Object> friendParams = new HashMap<>();
        friendParams.put("invite","1");
        friendParams.put("account",account);


        Object result= userFeign.findGroupFriendOfIm(friendParams);


        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        JSONArray classArray = JSONArray.parseArray(jsonObject.get("data").toString());
        List<Map<String, Object>> imFriendList = jsonArrayToList(classArray);


        //List<ImGroupDTO> imGroupList== new ArrayList<ImGroupDTO>();
        //通过account 获取用户最上层好友分组
        /*Example friendGroup = new Example(RelationshipGroup.class);
        friendGroup.createCriteria().andEqualTo("account", account).andEqualTo("parentId", 0);
        List<RelationshipGroup> friendGroupList = relationshipGroupMapper.selectByExample(friendGroup);
        //遍历好友分组 获取每个最上层分组下对应的好友
        for (RelationshipGroup relationshipGroup : friendGroupList) {
            //获取每个分组下具体的所有好友
            Example friends = new Example(RelationshipFriend.class);
            friends.createCriteria().andEqualTo("account", account).andEqualTo("agreeType", 2)
                    .andEqualTo("groupId", relationshipGroup.getGruopId());
            List<RelationshipFriend> friendList = relationshipFriendMapper.selectByExample(friends);
            List<Map<String, Object>> imFriendList = new ArrayList<>();//friend-->list
            for (RelationshipFriend relationshipFriend : friendList) {
                Map<String, Object> imUser = getImUser(relationshipFriend.getFromAccount(), false);
                imFriendList.add(imUser);
            }
            imFriendList.addAll(getChildGroupImFriendList(account,relationshipGroup.getGruopId()));
            Map<String, Object> imFriendGroupMap = new LinkedHashMap<>();
            imFriendGroupMap.put("groupname", relationshipGroup.getGruopName());
            imFriendGroupMap.put("id", relationshipGroup.getGruopId());
            imFriendGroupMap.put("list", imFriendList);
            friendMap.add(imFriendGroupMap);
        }*/
        Map<String, Object> data = new HashMap<>();
        data.put("mine", mineMap);
        data.put("friend", imFriendList);
        data.put("group", new ArrayList<Map<String, Object>>());
        logger.debug("account:{}, web im 用户详情:{}", account, mineMap);
        logger.debug("account:{}, web im 用户好友分组详情:{}", account, imFriendList);
    //    initImDTO.setGroup(imGroupList);
        return data;
    }

    private Collection<? extends Map<String,Object>> getChildGroupImFriendList(String account, int groupId) {
        List<Map<String, Object>> imFriendList = new ArrayList<>();//friend-->list
        Example friendGroup = new Example(RelationshipGroup.class);
        friendGroup.createCriteria().andEqualTo("account", account).andEqualTo("parentId", groupId);
        List<RelationshipGroup> friendGroupList = relationshipGroupMapper.selectByExample(friendGroup);
        if (CollectionUtils.isNotEmpty(friendGroupList)) {
            //遍历好友分组 获取每个最上层分组下对应的好友
            for (RelationshipGroup relationshipGroup : friendGroupList) {
                //获取每个分组下具体的所有好友
                Example friends = new Example(RelationshipFriend.class);
                friends.createCriteria().andEqualTo("account", account).andEqualTo("agreeType", 2)
                        .andEqualTo("groupId", relationshipGroup.getGruopId());
                List<RelationshipFriend> friendList = relationshipFriendMapper.selectByExample(friends);

                for (RelationshipFriend relationshipFriend : friendList) {
                    Map<String, Object> imUser = getImUser(relationshipFriend.getFromAccount(), false);
                    imFriendList.add(imUser);
                }
                imFriendList.addAll(getChildGroupImFriendList(account,relationshipGroup.getGruopId()));
            }
        }
        return imFriendList;
    }

    private List<Map<String,Object>> jsonArrayToList(JSONArray jsonArray) {

        List<Map<String,Object>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Map<String,Object> t = JSONObject.toJavaObject(jsonObject, Map.class);
            list.add(t);
        }
        return list;
    }

    private UserDTO getUserDTO(GetUserQuery getUserQuery){
        Object rows= userFeign.getUserMemberName(getUserQuery);
        JSONObject jsonObject= JSONObject.parseObject(rows.toString());
        UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
        return userDTO;
    }

    /**
     * 远程查询用户信息
     * account 用户
     * isUserself true 用户自身 false 用户好友
     * @param account
     * @param isUserself
     * @return
     */
    private Map<String, Object> getImUser(String account, boolean isUserself){
        GetUserQuery getUserQuery = new GetUserQuery();
        getUserQuery.setQueryType(1);
        getUserQuery.setAccount(account);
        UserDTO userDTOOne=getUserDTO(getUserQuery);
        Map<String, Object> user = new LinkedHashMap<String, Object>();
        user.put("account", userDTOOne.getLoginAccount());
        user.put("username", userDTOOne.getDisplayName());//我的昵称
        user.put("id", String.valueOf(userDTOOne.getId()));//我的ID
        if (isUserself) {
            //用户自身 初始化时默认在线
            user.put("status", "online");//在线状态 online：在线、hide：隐身
        } else {
            //用户好友 默认离线
            user.put("status", "offline");//在线状态 若值为offline代表离线，online或者不填为在线
        }
        user.put("sign", userDTOOne.getSignaTure());//我的签名
        user.put("avatar", userDTOOne.getAvatar());//我的头像
        return user;
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @Override
    public Object getUserById(String id) {
        GetUserQuery getUserQuery=new GetUserQuery();
        getUserQuery.setQueryType(2);
        getUserQuery.setId(Integer.parseInt(id));
        Object rows= userFeign.getUserByQuery(getUserQuery);
        return rows;
    }

}
