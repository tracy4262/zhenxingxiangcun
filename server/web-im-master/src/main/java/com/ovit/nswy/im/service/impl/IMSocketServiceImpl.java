package com.ovit.nswy.im.service.impl;

import com.ovit.nswy.im.service.IMSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * DESCRIPTION:
 * im socket业务service
 * 缓存account、username、ChannelHandlerContext
 * 及回调发送消息至客户端
 * @author zouyan
 * @create 2018-01-24 上午11:51
 * created by fuck~
 **/
public class IMSocketServiceImpl implements IMSocketService {

    private static final Logger logger = LogManager.getLogger(IMSocketServiceImpl.class);

    private ChannelHandlerContext ctx;
    //用户account
    private String account;
    //用户名
    private String userName;
    //用户在线状态 online/hide(在线/隐身)
    private String status;
    //用户好友
    private List<String> friendIds;
    //用户头像
    private String avatar;

    public IMSocketServiceImpl(ChannelHandlerContext ctx, String account,
                               String userName, String status, List<String> friendIds, String avatar) {
        this.ctx = ctx;
        this.account = account;
        this.userName = userName;
        this.status = status;
        this.friendIds = friendIds;
        this.avatar = avatar;
    }

    /**
     * 服务端推送消息至客户端
     * @param JsonMessage
     * @throws Exception
     */
    @Override
    public void send(String JsonMessage) throws Exception {
        if (this.ctx == null || this.ctx.isRemoved()) {
            throw new Exception("websocket未成功建连，无法发送消息");
        }
        logger.trace("发送JsonMessage至客户端:{}", JsonMessage);
        this.ctx.channel().write(new TextWebSocketFrame(JsonMessage));
        this.ctx.flush();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "IMSocketServiceImpl{" +
                "ctx=" + ctx.channel().id().asLongText() +
                ", account='" + account + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", friendIds=" + friendIds +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
