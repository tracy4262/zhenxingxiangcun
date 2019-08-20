/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.fuck.test.im.server.netty.handler;

import com.alibaba.fastjson.JSON;
import com.fuck.test.im.server.netty.SessionManager;
import com.fuck.test.im.server.util.OKHttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Outputs index page content.
 */
public class WebSocketIndexPageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    //示例demo url
    private final String indexUrl;

    //农事无忧 主播url
    private final String anchorUrl;

    //农事无忧 游客url
    private final String visitorUrl;

    public WebSocketIndexPageHandler(String indexUrl, String anchorUrl, String visitorUrl) {
        this.indexUrl = indexUrl;
        this.anchorUrl = anchorUrl;
        this.visitorUrl = visitorUrl;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Send the index page
        if ("/".equals(req.uri()) || "/index.html".equals(req.uri())
                || "/anchor".equals(req.uri()) || "/visitor".equals(req.uri())) {
            // / /index 示例demo
            // /anchor /visitor 配合农事无忧的自定义主播、游客url
            String respContent = "";
            if ("/".equals(req.uri()) || "/index.html".equals(req.uri())) {
                respContent = OKHttpUtil.getInstance().excuteHttpGet(indexUrl);
            } else if ("/anchor".equals(req.uri())) {
                respContent = OKHttpUtil.getInstance().excuteHttpGet(anchorUrl);
            } else if ("/visitor".equals(req.uri())) {
                respContent = OKHttpUtil.getInstance().excuteHttpGet(visitorUrl);
            }
            ByteBuf content =Unpooled.copiedBuffer(respContent.getBytes());
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
        } else if ("/rooms".equals(req.uri())) {
            //获取未注销的房间列表及房间在线人员列表
            //key: tv_room_ + roomId value: roomMap(key:userId value:IMService)
            Map<String, Object> imRoomsMap = SessionManager.imRoomsMap;
            //key: roomId value:userIds
            //{"roomId-1":["userid-2","userid-1"],"roomId-2":["userid-2","userid-1"],"roomId-3":["userid-1"]}
            Map<String, Object> respMap = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : imRoomsMap.entrySet()) {
                String roomKey = entry.getKey();
                Map<String, Object> roomMap = (Map<String, Object>)entry.getValue();
               // roomMap.keySet()
                respMap.put(roomKey.replace("tv_room_", ""), roomMap.keySet());
            }
            String respContent = JSON.toJSONString(respMap);
            ByteBuf content =Unpooled.copiedBuffer(respContent.getBytes());
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);

        } else {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
        }
    }

    /*@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }*/

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
    }
}
