package com.ovit.nswy.im.dao.impl;

import com.ovit.nswy.im.dao.ChatLogDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-25 下午4:29
 * created by fuck~
 **/
@Repository
public class ChatLogDaoImpl implements ChatLogDao {

    private static final Logger logger = LogManager.getLogger(ChatLogDaoImpl.class);

    @Resource
    private SqlSession sqlSession;

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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fromAccount", fromAccount);
        params.put("toAccount", toAccount);
        params.put("content", content);
        params.put("sendTime", sendTime);
        params.put("isSend", isSend);
        params.put("chatType", chatType);
        params.put("fromId", fromId);
        logger.debug("保存用户聊天数据:{}", params);
        sqlSession.insert("chatLogMapper.insertChatLog", params);
    }


    /**
     * 通过用户id查询该用户未接收的且需要推送至此用户的消息记录
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getChatLogByUserId(String userId) {
        List<Map<String, Object>> resultList = sqlSession.selectList("chatLogMapper.getChatLogByUserId", userId);
        logger.debug("通过用户id查询对应的离线聊天数据, userId:{}, data:{}", userId, resultList);
        return resultList;
    }

    /**
     * 根据接收消息用户ID更新chat_log消息状态为已发送(对于接收用户为已接收)
     * @param toAccount
     */
    @Override
    public void updateChatLogMsgSend(String toAccount) {
        logger.debug("根据用户id更新离线消息为已发送，对于此用户来说为已接收, 用户id:{}", toAccount);
        sqlSession.update("chatLogMapper.updateChatLogMsgSend", toAccount);
    }

    /**
     * web im聊天记录页面获取好友聊天记录信息
     * id:好友ID
     * mineId:用户自身ID
     * @param id
     * @param mineId
     * @return
     */
    @Override
    public List<Map<String, Object>> getFriendChatLogByIdAndType(String id, String mineId) {
        logger.debug("根据用户id查询好友聊天记录, 好友id:{}, mineId:{}", id, mineId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mineId", mineId);
        params.put("userId", id);
        return sqlSession.selectList("chatLogMapper.getFriendChatLogByIdAndType", params);
    }

    /**
     * web im聊天记录页面获取群聊天记录信息
     * id:群ID
     * mineId:用户自身ID
     * @param id
     * @param mineId
     * @return
     */
    @Override
    public List<Map<String, Object>> getGroupChatLogByIdAndType(String id, String mineId) {
        logger.debug("根据用户id查询群聊天记录, 群id:{}, mineId:{}", id, mineId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mineId", mineId);
        params.put("groupId", id);
        return sqlSession.selectList("chatLogMapper.getGroupChatLogByIdAndType", params);
    }
}
