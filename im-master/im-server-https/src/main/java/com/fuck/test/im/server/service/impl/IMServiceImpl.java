package com.fuck.test.im.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.fuck.test.im.server.model.IMRequest;
import com.fuck.test.im.server.service.IMService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-04 下午3:05
 * created by fuck~
 **/
public class IMServiceImpl implements IMService {

    private static final Logger logger = LogManager.getLogger(IMServiceImpl.class);

    private ChannelHandlerContext ctx;

    private String userId;

    private String userName;

    public IMServiceImpl(ChannelHandlerContext ctx, String userId, String userName) {
        this.ctx = ctx;
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * 服务端推送消息至客户端
     * @param imRequest
     * @throws Exception
     */
    @Override
    public void send(IMRequest imRequest) throws Exception {
        if (this.ctx == null || this.ctx.isRemoved()) {
            throw new Exception("websocket未成功建连，无法发送消息");
        }
        String imRequestJson = JSON.toJSONString(imRequest);
        logger.trace("发送imRequest至客户端:{}", imRequestJson);
        this.ctx.channel().write(new TextWebSocketFrame(imRequestJson));
        this.ctx.flush();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

}
