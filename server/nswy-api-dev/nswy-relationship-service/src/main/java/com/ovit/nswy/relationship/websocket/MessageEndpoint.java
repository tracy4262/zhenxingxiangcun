package com.ovit.nswy.relationship.websocket;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.frame.common.util.SpringUtil;
import com.ovit.nswy.relationship.feign.UserFeign;
import com.ovit.nswy.relationship.model.ChatMessage;
import com.ovit.nswy.relationship.model.request.GetUserQuery;
import com.ovit.nswy.relationship.model.response.UserDTO;
import com.ovit.nswy.relationship.service.GroupFriendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ${huipei.x} on 2018-1-3.
 */
@Component
@ServerEndpoint(value = "/websocket/{userName}")
public class MessageEndpoint {

    /** 日志 */
    //private final static Logger logger = Logger.getLogger(MessageEndpoint.class);
private Logger logger = LogManager.getLogger(this.getClass());
    private static final String NOTICE_MESSAGE_USERNAME = "-10000";

    /** 在线人数 */
    private static int ONLINE_COUNT = 0;

    /** userName和session对应关系的map */
    private static ConcurrentHashMap<String, Session> user2SessionMap = new ConcurrentHashMap<>();

    /** session和userName对应关系的map */
    private static ConcurrentHashMap<Session, String> session2UserMap = new ConcurrentHashMap<>();
    private static UserFeign userFeign;

    private static synchronized UserFeign getUserFeign(){
        if(userFeign==null){
            userFeign = SpringUtil.getBean(UserFeign.class);
        }
        return userFeign;
    }

    private static GroupFriendService groupFriendService;

    private static synchronized GroupFriendService getGroupFriendService(){
        if(groupFriendService==null){
            groupFriendService = SpringUtil.getBean(GroupFriendService.class);
        }
        return groupFriendService;
    }

    /**
     * 建立websocket连接
     * @param session
     * 连接websocket的session
     * @param userName
     *    用户名
     */
    @OnOpen
    public void open(Session session, @PathParam("userName") String userName) {
        if (NOTICE_MESSAGE_USERNAME.equals(userName)) {
            return;
        }
        GetUserQuery getUserQuery=new GetUserQuery();
        getUserQuery.setQueryType(1);
        getUserQuery.setAccount(userName);
        Object rows= getUserFeign().getUserByQuery(getUserQuery);
        JSONObject jsonObject= JSONObject.parseObject(rows.toString());
        UserDTO userDTO= JSONHelper.parseObject(jsonObject.get("data").toString(), UserDTO.class);
        user2SessionMap.put(String.valueOf(userDTO.getId()), session);
        session2UserMap.put(session, String.valueOf(userDTO.getId()));
        // 上线通知由客户端自主发起，在线数加1
        ONLINE_COUNT++;
        logger.info("userName: " + userName + ";" + "open websocket: sessionId " + session.getId() + ";"
                + "online count: " + ONLINE_COUNT);
    }

    /**
     * 关闭websocket连接
     * @param session
     * 关闭websocket的session
     */

    @OnClose
    public void close(Session session) {
        if (!session2UserMap.containsKey(session)) {
            return;
        }
        String userName = session2UserMap.get(session);
        // 清理数据
        ONLINE_COUNT--;
        user2SessionMap.remove(userName);
        session2UserMap.remove(session);
        logger.info("userName: " + userName + ";" + "close websocket: sessionId " + session.getId() + ";"
                + "online count: " + ONLINE_COUNT);
    }

    /**
     * 通过websocket发送消息
     * @param session
     * 发送消息的websocket的session
     * @param msg
     * 消息内容
     */
    @OnMessage
    public void message(Session session, String msg) {
        String userName = session2UserMap.get(session);
        logger.info("userName: " + userName + ";" + "sessionId: " + session.getId() + ";" + "message: " + msg);
        logger.info("userName1: " + userName + ";" + "sessionId1: " + session.getId() + ";" + "message: " + msg);
        JSONObject message = JSONObject.parseObject(msg);
        String emit = message.getString("emit");
        switch (emit) {
            case "chatMessage": {
                // 聊天的消息处理
                JSONObject data = message.getJSONObject("data");
                JSONObject mine = data.getJSONObject("mine");
                JSONObject to = data.getJSONObject("to");
                ChatMessage chatMessage = new ChatMessage(mine.getString("username"), mine.getString("avatar"),
                        mine.getString("id"), to.getString("type"), mine.getString("content"), 0, false,
                        mine.getString("id"), System.currentTimeMillis());
                JSONObject outputMessage = new JSONObject();
                outputMessage.put("emit", "chatMessage");
                outputMessage.put("data", JSONObject.toJSON(chatMessage));
                // 先测试单聊
                Session sessionTo = user2SessionMap.get(to.getString("id"));
                if (sessionTo != null) {
                    sessionTo.getAsyncRemote().sendText(outputMessage.toString());
                }
                break;
            }

            case "groupMessage": {
                // 聊天的消息处理
                JSONObject data = message.getJSONObject("data");
                JSONObject mine = data.getJSONObject("mine");
                JSONObject to = data.getJSONObject("to");
                logger.info("群组数据:{}" + JSONObject.toJSON(to));
                ChatMessage chatMessage = new ChatMessage(mine.getString("username"), mine.getString("avatar"),
                        to.getString("id"), to.getString("type"), mine.getString("content"), 0, false,
                        mine.getString("id"), System.currentTimeMillis());
                JSONObject outputMessage = new JSONObject();
                outputMessage.put("emit", "groupMessage");
                outputMessage.put("data", JSONObject.toJSON(chatMessage));
                logger.info("群组Id:{}"+ to.getString("id"));
               List<Integer> members=getGroupFriendService().getUserIdByGroupId( mine.getString("id"),Integer.valueOf(to.getString("id")));
                logger.info("群员Id:{}"+ members);
                for(Integer member: members) {
                    Session sessionTo = user2SessionMap.get(String.valueOf(member));
                    logger.info("sessionTo:{}"+ sessionTo);
                    if(sessionTo != null) {
                        sessionTo.getAsyncRemote().sendText(outputMessage.toString());
                    }
                }
                break;
            }
            case "noticeMessage": {
                // 消息的处理
                String to = message.getString("to");
                if (!user2SessionMap.containsKey(to)) {
                    return;
                }
                Session sessionTo = user2SessionMap.get(to);
                JSONObject outputMessage = new JSONObject();
                outputMessage.put("emit", "noticeMessage");
                sessionTo.getAsyncRemote().sendText(outputMessage.toString());
                break;
            }
            case "addList": {
                // 将好友信息添加到面板中
                String myId = message.getString("myId");
                String toId = message.getString("toId");
                String toGroup = message.getString("toGroup");
                if (!user2SessionMap.containsKey(toId)) {
                    return;
                }
                Session sessionTo = user2SessionMap.get(toId);
                JSONObject outputMessage = new JSONObject();
                outputMessage.put("emit", "addList");
                outputMessage.put("toId", myId);
                outputMessage.put("myGroup", toGroup);
                sessionTo.getAsyncRemote().sendText(outputMessage.toString());
                break;
            }
            default:
                break;
        }


    }

    /**
     * 通信错误
     *
     * @param throwable
     */
    @OnError
    public void error(Throwable throwable) {
        logger.error("websocket error!!!");
        throwable.printStackTrace();
    }


}
