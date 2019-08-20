package com.fuck.test.im.server.netty.handler;

import com.alibaba.fastjson.JSON;
import com.fuck.test.im.server.model.IMRequest;
import com.fuck.test.im.server.model.IMResponse;
import com.fuck.test.im.server.model.IMType;
import com.fuck.test.im.server.netty.SessionManager;
import com.fuck.test.im.server.service.IMService;
import com.fuck.test.im.server.service.impl.IMServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * DESCRIPTION:
 * 自定义直播间公共聊天业务处理
 * @author zouyan
 * @create 2018-01-03 下午3:22
 * created by fuck~
 **/
public class IMWebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LogManager.getLogger(IMWebSocketServerHandler.class);

    //private JedisProxy jedis;

    private WebSocketServerHandshaker handshaker;

    private ChannelHandlerContext ctx;

    /*public IMWebSocketServerHandler(JedisProxy jedis) {
        this.jedis = jedis;
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        //text数据帧
        if (frame instanceof TextWebSocketFrame) {
            this.ctx = ctx;
            // 处理来自客户端的WebSocket请求
            try {
                IMRequest imRequest = JSON.parseObject(((TextWebSocketFrame)frame).text(), IMRequest.class);
                //进行request校验 不合规的将直接返回imResponse
                Boolean validateResult = validateIMRequest(imRequest);
                if (validateResult) {
                    //参数校验成功 判断所属行为类型（如：上线、下线等）进行具体业务操作
                    Integer type = imRequest.getType();
                    if (IMType.ON_LINE.code.equals(type)) {
                        //上线业务处理
                        onLine(imRequest);
                    } else if (IMType.OFF_LINE.code.equals(type)) {
                        //下线业务处理
                        offLine(imRequest);
                    } else if (IMType.SEND_MSG.code.equals(type)) {
                        //发送消息业务处理
                        sendMsg(imRequest);
                    } else {
                        logger.warn("未知数据包! frame:{}", frame);
                    }
                }
            } catch (Exception e) {
                logger.error("websocket请求出错！", e);
            }
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        removeUserCtx(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        removeUserCtx(ctx);
        ctx.close();
    }

    /**
     * 根据断链、出现错误的ctx 删除对应的ctx缓存映射及刷新在线人员列表
     * @param ctx
     */
    private void removeUserCtx(ChannelHandlerContext ctx) throws Exception {
        String ctxChannelId = ctx.channel().id().asLongText();
        String[] ctxArr = SessionManager.getCtxSession(ctxChannelId);
        if (ctxArr != null) {
            String ctxRoomId = ctxArr[0];
            String ctxUserId = ctxArr[1];
            String userName = "";
            SessionManager.removeCtxSession(ctxChannelId);
            String roomKey = "tv_room_" + ctxRoomId;
            //XXX
            //后端在线人员map key:userId value:imservice(缓存channel上下文、userId、userName)
            Map<String, Object> roomMap = SessionManager.findRoomSessionById(roomKey);
            if (MapUtils.isNotEmpty(roomMap)) {
                IMServiceImpl imServiceImpl = (IMServiceImpl) roomMap.get(ctxUserId);
                userName = imServiceImpl.getUserName();
                roomMap.remove(ctxUserId);
                if (MapUtils.isEmpty(roomMap)) {
                    //此房间最后一个在线用户 无需通知其他用户该用户下线
                    SessionManager.removeRoomSession(roomKey);
                } else {
                    //通知其他在线人员 该链接对应的用户下线
                    for (Map.Entry<String, Object> entry : roomMap.entrySet()) {
                        IMService imService = (IMService) entry.getValue();
                        //构造request发送至客户端
                        IMRequest onlineIMRequest = new IMRequest();
                        onlineIMRequest.setRoomId(ctxRoomId);
                        onlineIMRequest.setType(IMType.OFF_LINE.code);
                        onlineIMRequest.setUserId(ctxUserId);
                        imService.send(onlineIMRequest);
                    }
                }
            }
            logger.info("userName:{}, userId:{} 离开房间，房间号:{}", userName, ctxUserId, ctxRoomId);
        }
    }

    /**
     * 上线业务处理
     * 上线请求 默认能进入直播间均为已登录校验过的账号，故不再进行登录校验
     * 通过房间号 在redis中进行在线人员集合查找
     * 判断是否已经登录过，已经登录过的返回原有在线人员集合列表 减少一次redis set开销
     * 未登录过的返回在线人员列表中添加自身
     * 同时遍历在线人员集合，向其他在线用户推送此用户上线消息
     * 后端在线人员map key:userId value:imservice(缓存channel上下文、userId、userName)
     * 前端在线人员map key:userId value:userName
     * @param imRequest
     */
    private void onLine(IMRequest imRequest) throws Exception {
        //通过房间号进行在线人员集合查找
        String roomId = imRequest.getRoomId();//房间号
        String requestUserId = imRequest.getUserId();
        String requestUserName = imRequest.getUserName();
        String roomKey = "tv_room_" + roomId;
        //XXX
        //后端在线人员map key:userId value:imservice(缓存channel上下文、userId、userName)
        Map<String, Object> roomMap = SessionManager.findRoomSessionById(roomKey);
        //返回至前端的在线人员map
        Map<String, String> onlineMap = new HashMap<String, String>();
        //上线请求响应结果（成功、失败）
        IMResponse imResponse;
        if (MapUtils.isEmpty(roomMap)) {
            roomMap = new HashMap<String, Object>();
            //此房间没有用户进入过
            onlineMap.put(requestUserId, requestUserName);
            imResponse = new IMResponse.Builder().isSucc(true).message("上线成功").type(IMType.ON_LINE.code).onlines(onlineMap).build();
            logger.info("房间号：{}，用户id：{}，用户名：{} 第一个进入此直播间", roomId, requestUserId, requestUserName);
        } else {
            //获取原有在线人员列表
            //XXX
            //遍历后端在线人员map生成前端在线人员map
            for (Map.Entry<String, Object> entry : roomMap.entrySet()) {
                String userId = entry.getKey();
                IMService imService = (IMService) entry.getValue();
                String userName = ((IMServiceImpl)imService).getUserName();
                onlineMap.put(userId, userName);
            }
            //判断是否已经上线过
            if (roomMap.containsKey(requestUserId)) {
                imResponse = new IMResponse.Builder().isSucc(true).message("已经上线过，请不要重复上线").type(IMType.ON_LINE.code).onlines(onlineMap).build();
                logger.info("房间号：{}，用户id：{}，用户名：{} 已经上线过，重复上线", roomId, requestUserId, requestUserName);
            } else {
                onlineMap.put(requestUserId, requestUserName);
                imResponse = new IMResponse.Builder().isSucc(true).message("上线成功").type(IMType.ON_LINE.code).onlines(onlineMap).build();
                logger.info("房间号：{}，用户id：{}，用户名：{} 上线成功", roomId, requestUserId, requestUserName);

            }
            //新上线、重复上线重新缓存数据及链接，防止重复上线时 旧链接已关闭
        }
        roomMap.put(requestUserId, new IMServiceImpl(this.ctx, requestUserId, requestUserName));
        //XXX
        SessionManager.putRoomSession(roomKey, roomMap);
        //缓存ctx、roomid、userid对应关系
        SessionManager.putCtxSession(this.ctx.channel().id().asLongText(), roomId, requestUserId);

        sendWebSocket(imResponse);
        //遍历后端在线人员集合，向其他在线用户推送此用户上线消息
        for (Map.Entry<String, Object> entry : roomMap.entrySet()) {
            String userId = entry.getKey();
            if (userId.equals(requestUserId)) {
                continue;
            }
            IMService imService = (IMService) entry.getValue();
            //构造request发送至客户端
            IMRequest onlineIMRequest = new IMRequest();
            onlineIMRequest.setRoomId(roomId);
            onlineIMRequest.setType(IMType.ON_LINE.code);
            onlineIMRequest.setUserId(requestUserId);
            onlineIMRequest.setUserName(requestUserName);
            imService.send(onlineIMRequest);
        }
    }

    /**
     * 发送消息业务处理
     * 通过房间号 在redis中进行在线人员集合查找
     * 后端在线人员map key:userId value:imservice(缓存channel上下文、userId、userName)
     * 通知所有在线人员接收消息
     */
    private void sendMsg(IMRequest imRequest) throws Exception {
        //通过房间号进行在线人员集合查找
        String roomId = imRequest.getRoomId();//房间号
        String requestUserId = imRequest.getUserId();
        String requestUserName = imRequest.getUserName();
        String requestMessage = imRequest.getMessage();
        String roomKey = "tv_room_" + roomId;
        //XXX
        Map<String, Object> roomMap = SessionManager.findRoomSessionById(roomKey);
        if (MapUtils.isNotEmpty(roomMap)) {
            for (Map.Entry<String, Object> entry : roomMap.entrySet()) {
                IMService imService = (IMService) entry.getValue();
                //构造request发送至客户端
                IMRequest onlineIMRequest = new IMRequest();
                onlineIMRequest.setRoomId(roomId);
                onlineIMRequest.setType(IMType.RECV_MSG.code);
                onlineIMRequest.setUserId(requestUserId);
                onlineIMRequest.setUserName(requestUserName);
                onlineIMRequest.setMessage(requestMessage);
                imService.send(onlineIMRequest);
            }
            IMResponse imResponse = new IMResponse.Builder().isSucc(true).message("发送消息成功").type(IMType.SEND_MSG.code).build();
            sendWebSocket(imResponse);
        }
    }

    /**
     * 下线业务处理
     * 通过房间号 在redis中进行在线人员集合查找
     * 后端在线人员map key:userId value:imservice(缓存channel上下文、userId、userName)
     * 移除对应的的userId,判断集合大小
     * 若集合大小为0，则最后一个寂寞的在线人员也下线了，删除对应的redis key，降低redis资源占用
     * 若集合大小不为0，还存在其他的在线人员，更新redis后端在线人员map，遍历后端在线人员集合通知其他在线人员该用户下线
     * @param imRequest
     */
    private void offLine(IMRequest imRequest) throws Exception {
        //通过房间号进行在线人员集合查找
        String roomId = imRequest.getRoomId();//房间号
        String requestUserId = imRequest.getUserId();
        String roomKey = "tv_room_" + roomId;
        //XXX
        Map<String, Object> roomMap = SessionManager.findRoomSessionById(roomKey);
        //防止错误下线请求
        if (MapUtils.isNotEmpty(roomMap)) {
            //移除下线人员
            roomMap.remove(requestUserId);

            SessionManager.removeCtxSession(this.ctx.channel().id().asLongText());
            if (roomMap.isEmpty()) {
                //删除redis key 无其他用户需要通知该用户下线
                //XXX
                SessionManager.removeRoomSession(roomKey);
            } else {
                //XXX
                SessionManager.putRoomSession(roomKey, roomMap);
                for (Map.Entry<String, Object> entry : roomMap.entrySet()) {
                    IMService imService = (IMService) entry.getValue();
                    //构造request发送至客户端
                    IMRequest onlineIMRequest = new IMRequest();
                    onlineIMRequest.setRoomId(roomId);
                    onlineIMRequest.setType(IMType.OFF_LINE.code);
                    onlineIMRequest.setUserId(requestUserId);
                    imService.send(onlineIMRequest);
                }
            }
        }
        IMResponse imResponse = new IMResponse.Builder().isSucc(true).message("下线成功").build();
        sendWebSocket(imResponse);
    }

    /**
     * 进行request校验 不合规的将直接返回错误imResponse
     * imRequest参数必要性校验,当type为上下线时不校验message
     * 校验无误返回true、校验失败返回false
     * @param imRequest
     * @return
     * @throws IllegalAccessException
     */
    private Boolean validateIMRequest(IMRequest imRequest) throws Exception {
        if (imRequest == null) {
            IMResponse imResponse = new IMResponse.Builder().isSucc(false).message("imRequest解析失败").build();
            sendWebSocket(imResponse);
            return false;
        }
        Integer type = imRequest.getType();
        if (type == null) {
            IMResponse imResponse = new IMResponse.Builder().isSucc(false).message("type不能为空").build();
            sendWebSocket(imResponse);
            return false;
        }
        for (Field f : imRequest.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if ("message".equals(f.getName())) {
                if (IMType.ON_LINE.code.equals(type) || IMType.OFF_LINE.code.equals(type)) {
                    //上下线时不校验message
                    continue;
                }
            }
            if(f.get(imRequest) == null || f.get(imRequest).equals("")){
                IMResponse imResponse = new IMResponse.Builder().isSucc(false).message(f.getName() + "不能为空").build();
                sendWebSocket(imResponse);
                return false;
            }
        }
        return true;
    }

    /**
     * 发送imResponse至客户端
     * @param imResponse
     * @throws Exception
     */
    private void sendWebSocket(IMResponse imResponse) throws Exception {
        if (this.ctx == null || this.ctx.isRemoved()) {
            throw new Exception("websocket未成功建连，无法发送消息");
        }
        String imResponseJson = JSON.toJSONString(imResponse);
        logger.trace("发送imResponse至客户端:{}", imResponseJson);
        this.ctx.channel().write(new TextWebSocketFrame(imResponseJson));
        this.ctx.flush();
    }
}
