package com.ovit.nswy.relationship.service;

import com.ovit.nswy.relationship.model.request.FriendChatDTO;
import com.ovit.nswy.relationship.model.response.ChatMessageDTO;
import java.util.List;

/**
 * Created by ${huipei.x} on 2018-1-2.
 */
public interface FriendChatService {

    public List<ChatMessageDTO> getChatMessage(FriendChatDTO friendChatDTO);
    public Integer saveChatMessage(FriendChatDTO friendChatDTO);
    public  Integer getUnreadMsgCount(String  account);
}
