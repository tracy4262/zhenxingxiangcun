package com.ovit.nswy.relationship.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.google.common.collect.Lists;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.nswy.relationship.enums.ChatTypeEnum;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.model.FriendChat;
import com.ovit.nswy.relationship.model.request.FriendChatDTO;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.response.ChatMessageDTO;
import com.ovit.nswy.relationship.model.response.UserDTO;
import com.ovit.nswy.relationship.service.FriendChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by ${huipei.x} on 2018-1-2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendChatServiceImpl extends BaseService<FriendChat> implements FriendChatService {

    @Autowired
    private UserFeign userFeign;
    @Override
    public Integer saveChatMessage(FriendChatDTO friendChatDTO){
        FriendChat friendChat=new FriendChat();
        int type= ChatTypeEnum.chatTypeEscape(friendChatDTO.getType());
        friendChat.setChatType(type);
        friendChat.setContent(friendChatDTO.getContent());
        friendChat.setFromAccount(friendChatDTO.getFromid());
        friendChat.setToAccount(friendChatDTO.getToid());
        friendChat.setCreateTime(new Date());
        friendChat.setIsRead(0);
        Integer result = this.save(friendChat);
        return Integers.isEmpty(result)?0:result;
    }

    @Override
    public  List<ChatMessageDTO> getChatMessage(FriendChatDTO friendChatDTO){
       int type= ChatTypeEnum.chatTypeEscape(friendChatDTO.getType());
        Example example = new Example(FriendChat.class);
        List<ChatMessageDTO> chatMessageDTOList= Lists.newArrayList();
        example.createCriteria().andEqualTo("fromAccount", friendChatDTO.getFromid()).andEqualTo("chatType", type).orEqualTo("toAccount", friendChatDTO.getFromid());
        List<FriendChat> friendChatList=this.findByExample(example);
        for(FriendChat friendChat:friendChatList){
            GetUserQuery getUserQuery=new GetUserQuery();
            getUserQuery.setQueryType(2);
            getUserQuery.setId(friendChat.getFromAccount());
            Object rows= userFeign.getUserByQuery(getUserQuery);
            JSONObject jsonObject= JSONObject.parseObject(rows.toString());
            UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
            ChatMessageDTO chatMessageDTO=new ChatMessageDTO();
            chatMessageDTO.setId(friendChat.getFromAccount());
            chatMessageDTO.setAvatar(userDTO.getAvatar());
            chatMessageDTO.setContent(friendChat.getContent());
            chatMessageDTO.setUsername(userDTO.getDisplayName());
            chatMessageDTO.setTime(friendChat.getCreateTime());
            chatMessageDTO.setTimestamp(System.currentTimeMillis());
            chatMessageDTOList.add(chatMessageDTO);
        }

        return chatMessageDTOList;
    }

    @Override
    public  Integer getUnreadMsgCount(String  account){
        Example example = new Example(FriendChat.class);
        GetUserQuery getUserQuery=new GetUserQuery();
        getUserQuery.setQueryType(1);
        getUserQuery.setAccount(account);
        Object rows= userFeign.getUserByQuery(getUserQuery);
        JSONObject jsonObject= JSONObject.parseObject(rows.toString());
        UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
        example.createCriteria().andEqualTo("fromAccount", userDTO.getId()).andEqualTo("isRead", 0);
        Integer result=  this.count(example);
        return result;
    }

}
