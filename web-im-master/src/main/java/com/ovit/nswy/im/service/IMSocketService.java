package com.ovit.nswy.im.service;

import java.util.List;

/**
 * im socket业务service
 * 缓存account、username、ChannelHandlerContext
 * 及回调发送消息至客户端
 */
public interface IMSocketService {
    /**
     * websocket推送消息
     * @param JsonMessage
     * @throws Exception
     */
    public void send(String JsonMessage) throws Exception;

    /**
     * 获取好友在线状态 在线/隐身 online/hide
     * @return
     */
    public String getStatus();

    /**
     * 获取用户对应的好友ids
     * @return
     */
    public List<String> getFriendIds();

    /**
     * 获取用户对应的用户名
     * @return
     */
    public String getUserName();

    /**
     * 获取用户头像
     * @return
     */
    public String getAvatar();

    /**
     * 设置用户在线、隐身状态
     * @return
     */
    public void setStatus(String status);

}
