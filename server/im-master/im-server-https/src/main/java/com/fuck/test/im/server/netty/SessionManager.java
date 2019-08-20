package com.fuck.test.im.server.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DESCRIPTION:
 * channel 无法缓存至redis
 * 新增当前进场channel缓存便于推送消息至各客户端
 * imRoomsMap
 * key: tv_room_ + roomId value: roomMap(key:userId value:IMService)
 *
 * 缓存ctx、roomid、userid对应关系
 * key: ctxChannelId value:String[] 0 roomId 1 userId
 *
 * @author zouyan
 * @create 2018-01-05 下午2:57
 * created by fuck~
 **/
public class SessionManager {

    public static final Map<String, Object> imRoomsMap = new ConcurrentHashMap<String, Object>();

    public static final Map<String, Object> ctxMap = new ConcurrentHashMap<String, Object>();

    public static void putRoomSession(String roomKey, Map<String, Object> roomMap) {
        imRoomsMap.put(roomKey, roomMap);
    }

    public static Map<String, Object> findRoomSessionById(String roomKey) {
        return (Map<String, Object>)imRoomsMap.get(roomKey);
    }

    public static void removeRoomSession(String roomKey) {
        imRoomsMap.remove(roomKey);
    }

    public static void putCtxSession(String channelId, String roomId, String userId) {
        String[] ctxArr = new String[2];
        ctxArr[0] = roomId;
        ctxArr[1] = userId;
        ctxMap.put(channelId, ctxArr);
    }

    public static String[] getCtxSession(String channelId) {
        return (String[])ctxMap.get(channelId);
    }

    public static void removeCtxSession(String channelId) {
        ctxMap.remove(channelId);
    }
}
