package com.ovit.nswy.im.service;

import com.ovit.nswy.im.model.ChatMsgHistory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:
 * chat_log表数据处理
 * @author zouyan
 * @create 2018-01-25 下午4:15
 * created by fuck~
 **/
public interface ChatLogService {

    /**
     * 插入数据至chat_log
     * fromAccount:发送消息用户ID
     * toAccount:接收消息用户ID
     * content:消息内容
     * sendTime: 发送时间
     * isSend: Y:已发送（在线消息），N:未发送（离线消息）
     * chatType: 消息类型：1 朋友分组，2 群组
     * fromId: 消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
     * @param fromAccount
     * @param toAccount
     * @param content
     * @param isSend
     * @param chatType
     * @param fromId
     */
    public void insertChatLog(String fromAccount, String toAccount, String content, Date sendTime, String isSend, String chatType, String fromId);

    /**
     * 通过用户id查询该用户未接收的且需要推送至此用户的消息记录
     * @param userId
     * @return
     */
    public List<Map<String, Object>> getChatLogByUserId(String userId);

    /**
     * 根据接收消息用户ID更新chat_log消息状态为已发送(对于接收用户为已接收)
     * @param toAccount
     */
    public void updateChatLogMsgSend(String toAccount);

    /**
     * web im聊天记录页面获取聊天记录信息
     * id:好友或群组ID
     * mineId:用户自身ID
     * @param id
     * @param mineId
     * @param type
     * @return
     */
    public List<ChatMsgHistory> getChatLogByIdAndType(String id, String mineId, String type);
}
