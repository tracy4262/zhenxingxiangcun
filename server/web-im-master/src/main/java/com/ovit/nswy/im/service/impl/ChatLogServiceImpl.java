package com.ovit.nswy.im.service.impl;

import com.ovit.nswy.im.dao.ChatLogDao;
import com.ovit.nswy.im.model.ChatMsgHistory;
import com.ovit.nswy.im.remote.IMInitRemote;
import com.ovit.nswy.im.service.ChatLogService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:
 * chat_log表数据处理
 * @author zouyan
 * @create 2018-01-25 下午4:16
 * created by fuck~
 **/
@Service
public class ChatLogServiceImpl implements ChatLogService {

    @Resource
    private ChatLogDao chatLogDao;

    @Autowired
    private IMInitRemote imInitRemote;

    //resolve a cache manager
    private CachingProvider cachingProvider = Caching.getCachingProvider();
    private CacheManager cacheManager = cachingProvider.getCacheManager();
    private MutableConfiguration<String, Map<String, Object>> thirtyMinConfig =
            new MutableConfiguration<String, Map<String, Object>>()
                    .setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.THIRTY_MINUTES))
                    .setStatisticsEnabled(false);

    private static Cache<String, Map<String, Object>> userCache;

    @PostConstruct
    public void defaultInitial() {
        userCache = cacheManager.createCache("userCache", thirtyMinConfig);
    }


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
    @Override
    public void insertChatLog(String fromAccount, String toAccount, String content, Date sendTime, String isSend, String chatType, String fromId) {
        chatLogDao.insertChatLog(fromAccount, toAccount, content, sendTime, isSend, chatType, fromId);
    }

    @Override
    public List<Map<String, Object>> getChatLogByUserId(String userId) {
        return chatLogDao.getChatLogByUserId(userId);
    }

    /**
     * 根据接收消息用户ID更新chat_log消息状态为已发送(对于接收用户为已接收)
     * @param toAccount
     */
    @Override
    public void updateChatLogMsgSend(String toAccount) {
        chatLogDao.updateChatLogMsgSend(toAccount);
    }

    /**
     * web im聊天记录页面获取聊天记录信息
     * id:好友或群组ID
     * mineId:用户自身ID
     * @param id
     * @param mineId
     * @param type
     * @return
     */
    @Override
    public List<ChatMsgHistory> getChatLogByIdAndType(String id, String mineId, String type) {
        List<ChatMsgHistory> result = new ArrayList<ChatMsgHistory>();
        List<Map<String, Object>> chatLogList;
        if ("friend".equals(type)) {
            //好友消息
            chatLogList =  chatLogDao.getFriendChatLogByIdAndType(id, mineId);
        } else {
            //群消息
            chatLogList = chatLogDao.getGroupChatLogByIdAndType(id, mineId);
        }
        if (CollectionUtils.isNotEmpty(chatLogList)) {
            for (Map<String, Object> chatLog : chatLogList) {
                String chatUserId = String.valueOf(chatLog.get("fromAccount"));
                String content = String.valueOf(chatLog.get("content"));
                long timestamp = ((Date)chatLog.get("sendTime")).getTime();
                String username;
                String avatar;
                Map<String, Object> userMap = userCache.get(chatUserId);
                if (MapUtils.isEmpty(userMap)) {
                    //远程调用获取用户信息
                    Map<String, Object> fromUserMap = (Map<String, Object>)imInitRemote.getUserById(chatUserId);;
                    userMap = (Map<String, Object>)fromUserMap.get("data");
                    userCache.put(chatUserId, userMap);
                }
                username = String.valueOf(userMap.get("displayName"));
                avatar = String.valueOf(userMap.get("avatar"));
                ChatMsgHistory chatMsgHistory = new ChatMsgHistory(chatUserId, username, avatar, content, timestamp);
                result.add(chatMsgHistory);
            }
        }
        return result;
    }
}
