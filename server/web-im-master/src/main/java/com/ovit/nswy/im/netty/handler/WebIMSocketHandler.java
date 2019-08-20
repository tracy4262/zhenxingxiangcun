package com.ovit.nswy.im.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.im.model.ChatMsg;
import com.ovit.nswy.im.model.EmitType;
import com.ovit.nswy.im.model.StatusType;
import com.ovit.nswy.im.netty.SessionManager;
import com.ovit.nswy.im.service.ChatLogService;
import com.ovit.nswy.im.service.IMService;
import com.ovit.nswy.im.service.IMSocketService;
import com.ovit.nswy.im.service.impl.IMSocketServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * DESCRIPTION:
 * web im业务处理类
 * @author zouyan
 * @create 2018-01-24 上午9:38
 * created by fuck~
 **/
public class WebIMSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LogManager.getLogger(WebIMSocketHandler.class);

    private IMService imService;

    private ChatLogService chatLogService;

    private ThreadPoolExecutor threadPoolExecutor;

    private WebSocketServerHandshaker handshaker;

    private ChannelHandlerContext ctx;

    public WebIMSocketHandler(IMService imService, ChatLogService chatLogService, ThreadPoolExecutor threadPoolExecutor) {
        this.imService = imService;
        this.chatLogService = chatLogService;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * websocket握手阶段走http，之后才是tcp
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 断链时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("socket 断链, ctxChannelId: {}", ctx.channel().id().asLongText());
        removeUserCtx(ctx);
    }

    /**
     * 链接异常时
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.debug("socket 链接异常, ctxChannelId: {}", ctx.channel().id().asLongText());
        cause.printStackTrace();
        removeUserCtx(ctx);
        ctx.close();
    }

    /**
     * 1.删除ctx、userid对应关系
     * 2.通知该用户在线好友ids该用户下线通知
     * 3.删除用户id对应的登录人员列表
     * @param ctx
     * @throws Exception
     */
    private void removeUserCtx(ChannelHandlerContext ctx) throws Exception {
        String ctxChannelId = ctx.channel().id().asLongText();
        final String userId = SessionManager.getCtxSession(ctxChannelId);
        logger.trace("ctxChannelId:{}, userId：{}", ctxChannelId, userId);
        if (StringUtils.isNotEmpty(userId)) {
            //1.删除ctx、userid对应关系
            SessionManager.removeCtxSession(ctxChannelId);
            logger.trace("删除ctx、userid对应关系，userId:{}, ctxChannelId:{}", userId, ctxChannelId);
            //2.通知该用户在线好友ids该用户下线通知
            final IMSocketService imSocketService = SessionManager.getUserSession(userId);
            Runnable noticeOffline = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (imSocketService != null) {
                            List<String> friendIds = imSocketService.getFriendIds();
                            //通知下线消息
                            Map<String, Object> noticeOfflineMsg = new HashMap<String, Object>();
                            noticeOfflineMsg.put("userId", userId);
                            String respJson = createResp(EmitType.OFF_LINE.type, noticeOfflineMsg);
                            for (String friendId : friendIds) {
                                if (userId.equals(friendId)) {
                                    //防止用户好友中有自己，若friendId存在自己的id则不再进行对自己的下线通知
                                    continue;
                                }
                                IMSocketService friendIMSocketService = SessionManager.getUserSession(friendId);
                                if (friendIMSocketService != null) {
                                    friendIMSocketService.send(respJson);
                                    logger.debug("通知用户好友该用户下线,下线用户userId:{}, 用户好友id:{}", userId, friendId);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("通知用户好友该用户下线出错 下线用户userId:{}, noticeOnline error", userId, e);
                    }
                }
            };
            threadPoolExecutor.submit(noticeOffline);
            //3.删除用户id对应的登录人员列表
            SessionManager.removeUserSession(userId);
            logger.trace("删除用户id对应的登录人员列表，userId:{}", userId);
            logger.debug("清空userId:{} 相关web-im信息完毕", userId);
        } else {
            logger.trace("根据ctxChannelId:{}, 未查询到相关userId：{}", ctxChannelId, userId);
        }
    }

    /**
     * websocket握手阶段走http
     * @param ctx
     * @param req
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //判断解码是否成功、httpheader是否指定websocket协议
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //返回异常 400 Bad Request
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        //符合条件的握手请求,构造成功响应
        String webSocketURL =  "ws://" + req.headers().get(HttpHeaderNames.HOST);
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                webSocketURL, null, false, 5 * 1024 * 1024);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            //不支持的websocket version
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            //成功握手响应
            handshaker.handshake(ctx.channel(), req);
            //保存上下文
            this.ctx = ctx;
        }
    }

    /**
     * websocket请求
     * @param ctx
     * @param frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        //关闭数据帧
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // ping数据帧
        if (frame instanceof PingWebSocketFrame) {
            ctx.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //二进制数据帧
        if (frame instanceof BinaryWebSocketFrame) {
            // Echo the frame
            //ctx.write(frame.retain());
            //直播聊天室只支持文本聊天 目前各大主流直播平台主播间只支持文本聊天
            logger.info("websocket接收到BinaryWebSocketFrame,不做任何处理,直接丢弃");
        }
        //text数据帧
        if (frame instanceof TextWebSocketFrame) {
            // 处理来自客户端的WebSocket请求
            try {
                JSONObject message = JSONObject.parseObject(((TextWebSocketFrame)frame).text());
                String emit = message.getString("emit");
                JSONObject data = message.getJSONObject("data");
                if (EmitType.ON_LINE.type.equals(emit)) {
                    //上线业务
                    onLine(data);
                } else if (EmitType.FRIEND_MSG.type.equals(emit)) {
                    //点对点 好友消息
                    friendMsg(data);
                } else if (EmitType.STATUS_CHANGE.type.equals(emit)) {
                    //隐身在线状态切换
                    statusChange(data);
                }
            } catch (Exception e) {
                logger.error("websocket请求出错！", e);
            }
        }

    }

    /**
     * 用户隐身在线状态切换
     * 1.更新登录人员列表 该用户在线状态为隐身、在线
     * 2.实时通知该用户在线好友在线或下线（实际隐身）
     * @param data
     */
    private void statusChange(final JSONObject data){
        Runnable notice = new Runnable() {
            @Override
            public void run() {
                String userId = "";
                try {
                    userId = data.getString("userId");
                    String status = data.getString("status");
                    IMSocketService imSocketService = SessionManager.getUserSession(userId);
                    //1.更新登录人员列表 该用户在线状态为隐身、在线
                    imSocketService.setStatus(status);//online或者hide
                    //2.实时通知该用户在线好友在线或下线（实际隐身）
                    List<String> friendIds = imSocketService.getFriendIds();
                    //通知上下线消息
                    Map<String, Object> noticeMsg = new HashMap<String, Object>();
                    noticeMsg.put("userId", userId);
                    noticeMsg.put("status", status);
                    String respJson = createResp(EmitType.STATUS_CHANGE.type, noticeMsg);
                    for (String friendId : friendIds) {
                        if (userId.equals(friendId)) {
                            //防止用户好友中有自己，若friendId存在自己的id则不再进行对自己的下线通知
                            continue;
                        }
                        IMSocketService friendIMSocketService = SessionManager.getUserSession(friendId);
                        if (friendIMSocketService != null) {
                            friendIMSocketService.send(respJson);
                            logger.debug("通知用户好友该用户在线/隐身,用户userId:{}, 用户好友id:{}, 用户状态:{}", userId, friendId, status);
                        }
                    }
                } catch (Exception e) {
                    logger.error("通知用户在线/隐身状态出错! 用户id:{}", userId, e);
                }

            }
        };
        threadPoolExecutor.submit(notice);
    }

    /**
     * 点对点 好友消息业务数据示例：
     * {
         "mine": {
          "username":"陈起铭",
          "avatar":"http://192.168.7.61/00/31/wKgHPVpK5PSAK6oTAAEryTGaew0016.png",
          "id":"1",
          "mine":true,
          "content":"这是消息内容"
          },
         "to": {
            "username":"康罗娜",
            "id":"103",
            "status":"online",
            "sign":"",
            "avatar":"http://192.168.7.61/00/29/wKgHPVolJvOANHy8AAEE0RKB_F4470.jpg",
            "name":"康罗娜",
            "type":"friend"
         }
     * }
     * 返回数据示例(见pojo: ChatMsg):
     * {
         username: "纸飞机" //消息来源用户名
         ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1" //消息来源用户头像
         ,id: "100000" //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
         ,type: "friend" //聊天窗口来源类型，从发送消息传递的to里面获取
         ,content: "嗨，你好！本消息系离线消息。" //消息内容
         ,cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
         ,mine: false //是否我发送的消息，如果为true，则会显示在右方
         ,fromid: "100000" //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
         ,timestamp: 1467475443306 //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
         }
     * 点对点 好友消息业务处理
     * 1.解析获取聊天消息
     * 2.判断好友是否在线
     * 2-1.好友在线，组装返回数据至好友侧 保存消息记录 标识已发送（在线消息）
     * 2-2.好友不在线，不组装返回数据至好友侧 保存消息记录 标识未发送（离线消息）下次好友上线时发送
     * @param data
     * @throws Exception
     * @author zouyan
     */
    private void friendMsg(JSONObject data) throws Exception {
        //1.解析获取聊天消息
        JSONObject mine = data.getJSONObject("mine");
        JSONObject to = data.getJSONObject("to");
        String toUserId = to.getString("id");
        //2.判断好友是否在线
        IMSocketService friendImSocketService = SessionManager.getUserSession(toUserId);
        if (friendImSocketService != null) {
            //2-1 好友在线，组装返回数据至好友侧 保存消息记录 标识已发送（在线消息）
            String respUserName = mine.getString("username");
            String respAvatar = mine.getString("avatar");
            String respId = mine.getString("id");
            String respType = to.getString("type");
            String respContent = mine.getString("content");
            String respFromid = respId;
            long respTimestamp = System.currentTimeMillis();
            ChatMsg chatMsg = new ChatMsg.Builder(respUserName, respAvatar, respId, respType, respContent, respFromid, respTimestamp).build();
            String respJson = createResp(EmitType.FRIEND_MSG.type, chatMsg);
            friendImSocketService.send(respJson);
            chatLogService.insertChatLog(mine.getString("id"), toUserId, respContent, new Date(), "Y", "1", mine.getString("id"));
        } else {
            //2-2 好友不在线，不组装返回数据至好友侧 保存消息记录 标识未发送（离线消息）下次好友上线时发送
            chatLogService.insertChatLog(mine.getString("id"), toUserId, mine.getString("content"), new Date(), "N", "1", mine.getString("id"));
        }
    }

    /**
     * 上线业务数据示例：
     * {"emit": "online", "data": {"account": "haha"}}
     * 上线业务处理
     * 1.获取主面板数据 示例数据见resources，im-demo
     * 2.解析数据获取account对应的userId、userName
     * 3.缓存ctx、userid对应关系 便于之后通过ctx删除对应的在线人员信息
     * 4.通过已有缓存用户在线状态设置该用户好友的在线状态、离线状态（若好友为隐身状态则为离线状态）
     * 5.缓存用户id存储至登录人员列表
     *   登录人员列表 key:userId value:IMSocketService (ctx, account, userName, status, friendIds, avatar)
     * 6.通知该用户所有在线好友该用户上线
     * 7.发送主面板数据至客户端
     * 8.查询该用户离线消息 若存在，推送离线消息
     *
     * @param data
     * @throws Exception
     * @author zouyan
     */
    private void onLine(JSONObject data) throws Exception {
        final String account = data.getString("account");
        //1.获取主面板数据 示例数据见resources，im-demo
        Map<String, Object> initMap = imService.getInitList(account);
        logger.debug("根据account:{} 获取初始化主面板数据:{}", account, initMap);
        if (MapUtils.isNotEmpty(initMap)) {
            //2.解析数据获取account对应的userId、userName
            Map<String, Object> mineMap = (Map<String, Object>) initMap.get("mine");
            final String userId = String.valueOf(mineMap.get("id"));
            String avatar = String.valueOf(mineMap.get("avatar"));
            String userName = String.valueOf(mineMap.get("username"));
            //3.缓存ctx、userid对应关系 便于之后通过ctx删除对应的在线人员信息
            SessionManager.putCtxSession(ctx.channel().id().asLongText(), userId);
            logger.debug("缓存ctx、userid对应关系, ctxId:{}, userId:{}", ctx.channel().id().asLongText(), userId);
            //4.通过已有缓存用户在线状态设置该用户好友的在线状态、离线状态（若好友为隐身状态则为离线状态）
            List<Map<String, Object>> imFriendGroupList = (List<Map<String, Object>>) initMap.get("friend");
            final List<String> friendIds = new ArrayList<String>();
            for (Map<String, Object> imFriendGroupMap : imFriendGroupList) {
                for (Map.Entry<String, Object> entry : imFriendGroupMap.entrySet()) {
                    String key = entry.getKey();
                    if ("list".equals(key)) {
                        List<Map<String, Object>> imFriendList = (List<Map<String, Object>>) entry.getValue();
                        for (Map<String, Object> friendMap : imFriendList) {
                            String friendId = String.valueOf(friendMap.get("id"));
                            Boolean isOnLine = SessionManager.userOnline(friendId);
                            if (isOnLine) {
                                //设置好友为在线状态，好友隐身时不会设置为在线状态
                                friendMap.put("status", StatusType.ONLINE.type);
                                logger.trace("userId:{}, friendId:{} 设置为在线状态", userId, friendId);
                            }
                            friendIds.add(friendId);
                        }
                        sortOnline(imFriendList);
                    }
                }
            }
            //5.缓存用户id存储至登录人员列表
            IMSocketService imSocketService = new IMSocketServiceImpl(ctx, account, userName, StatusType.ONLINE.type, friendIds, avatar);
            SessionManager.putUserSession(userId, imSocketService);
            logger.debug("缓存用户id存储至登录人员列表, userId:{}, imSocketService:{}", userId, imSocketService);
            //6.通知该用户所有在线好友该用户上线
            noticeOnline(account, userId, friendIds);
            //7.发送主面板数据至客户端
            String resultJson = createResp(EmitType.ON_LINE_RES.type, initMap);
            logger.debug("web im account:{}, 主面板数据:{}", account, resultJson);
            logger.info("web im account:{} 上线", account);
            imSocketService.send(resultJson);
            //8.查询该用户离线消息 若存在，推送离线消息
            recvOffLineMsg(account, userId);
        }
    }

    /**
     * 根据用户id、用户好友ids
     * 向该用户的好友推送该用户上线通知
     * @param account
     * @param userId
     * @param friendIds
     */
    private void noticeOnline(final String account, final String userId, final List<String> friendIds) {
        Runnable noticeOnline = new Runnable() {
            @Override
            public void run() {
                try {
                    //通知上线消息
                    Map<String, Object> noticeOnlineMsg = new HashMap<String, Object>();
                    noticeOnlineMsg.put("userId", userId);
                    String respJson = createResp(EmitType.ON_LINE.type, noticeOnlineMsg);
                    for (String friendId : friendIds) {
                        if (userId.equals(friendId)) {
                            //防止用户好友中有自己，若friendId存在自己的id则不再进行对自己的上线通知
                            continue;
                        }
                        IMSocketService friendIMSocketService = SessionManager.getUserSession(friendId);
                        if (friendIMSocketService != null) {
                            friendIMSocketService.send(respJson);
                        }
                    }
                } catch (Exception e) {
                    logger.error("通知用户好友该用户上线出错 account:{}, userId:{}, noticeOnline error", account, userId, e);
                }
            }
        };
        threadPoolExecutor.submit(noticeOnline);
    }

    /**
     * 根据用户id 判断该用户是否存在离线消息未接收
     * 未接收则推送离线消息至此用户
     * 更新聊天记录表 离线消息状态为已发送
     * @param account
     * @param userId
     */
    private void recvOffLineMsg(final String account, final String userId) {
        Runnable chatMsgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //查询是否存在离线消息
                    List<Map<String, Object>> chatMsgList = chatLogService.getChatLogByUserId(userId);
                    logger.debug("web im userId:{}, 离线消息:{}", userId, chatMsgList);
                    if (CollectionUtils.isNotEmpty(chatMsgList)) {
                        //发送离线消息
                        //获取该登录用户的ctx
                        IMSocketService logInIMSocketService = SessionManager.getUserSession(userId);
                        if (logInIMSocketService != null) {
                            for (Map<String, Object> chatMsgMap : chatMsgList) {
                                //消息来源用户id
                                String chatUsername;//消息来源用户名
                                String chatAvatar;//消息来源用户头像
                                //对应chat_log(from_id)消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
                                String chatId = String.valueOf(chatMsgMap.get("fromId"));
                                //聊天窗口来源类型(friend/group)
                                String chatType = "1".equals(String.valueOf(chatMsgMap.get("chatType"))) ? "friend" : "group";
                                //消息内容
                                String chatContent = String.valueOf(chatMsgMap.get("content"));
                                String chatFromId = String.valueOf(chatMsgMap.get("fromAccount"));//消息的发送者id
                                long chatTimestamp = ((Date)chatMsgMap.get("sendTime")).getTime();//服务端时间戳毫秒数
                                //先从登录人员缓存查看是否存在此用户信息 否则远程调用获取用户信息
                                IMSocketService fromIMSocketService = SessionManager.getUserSession(chatFromId);
                                if (fromIMSocketService != null) {
                                    chatUsername = fromIMSocketService.getUserName();
                                    chatAvatar = fromIMSocketService.getAvatar();
                                } else {
                                    //远程调用获取用户信息
                                    Map<String, Object> fromUserMap = (Map<String, Object>)imService.getUserById(chatFromId);
                                    Map<String, Object> userDataMap = (Map<String, Object>)fromUserMap.get("data");

                                    //JSONObject jsonUser = JSONObject.parseObject(fromUserObj.toString());
                                    //JSONObject jsonUserData = jsonUser.getJSONObject("data");
                                    chatUsername = String.valueOf(userDataMap.get("displayName"));
                                    chatAvatar = String.valueOf(userDataMap.get("avatar"));
                                }
                                ChatMsg chatMsg = new ChatMsg.Builder(chatUsername, chatAvatar,
                                        chatId, chatType, chatContent, chatFromId, chatTimestamp).build();
                                String respJson = "";
                                if ("friend".equals(chatType)) {
                                    respJson = createResp(EmitType.FRIEND_MSG.type, chatMsg);
                                } else {
                                    respJson = createResp(EmitType.GROUP_MSG.type, chatMsg);
                                }
                                logger.debug("web im用户离线消息, 用户account:{}, userId:{}, 好友id:{}, 好友名字:{}, 消息内容:{}", account, userId, chatFromId, chatUsername, chatContent);
                                logInIMSocketService.send(respJson);
                            }
                            //更新消息状态为已发送消息
                            chatLogService.updateChatLogMsgSend(userId);
                            logger.debug("根据用户id更新离线消息为已发送，对于此用户来说为已接收, 用户id:{}", userId);
                        }
                    }
                } catch (Exception e) {
                    logger.error("通知用户好友离线消息出错 account:{}, userId:{}, noticeOnline error", account, userId, e);
                }
            }
        };
        threadPoolExecutor.submit(chatMsgRunnable);
    }

    /**
     * http status code非200返回错误响应
     * 非keep alive或非200 关闭链接
     * @param ctx
     * @param req
     * @param res
     */
    private static void sendHttpResponse(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static void sortOnline(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map<String, Object> map1 = (Map<String, Object>) o1;
                Map<String, Object> map2 = (Map<String, Object>) o2;
                return -(String.valueOf(map1.get("status")).compareTo(String.valueOf(map2.get("status"))));
            }
        });
    }

    /**
     * 创建推送至客户端消息
     * @return
     */
    private String createResp(String type, Object dataObj) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("emit", type);
        respMap.put("data", dataObj);
        String respJson = JSON.toJSONString(respMap);
        return respJson;
    }

}
