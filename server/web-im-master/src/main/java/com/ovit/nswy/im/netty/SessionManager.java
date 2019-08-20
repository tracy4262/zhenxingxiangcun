package com.ovit.nswy.im.netty;

import com.ovit.nswy.im.model.StatusType;
import com.ovit.nswy.im.service.IMSocketService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DESCRIPTION:
 * 1.缓存用户id存储至登录人员列表
 * 登录人员列表 key:userId value:IMSocketService (ctx, account, userName, status, friendIds)
 * 2.缓存ctx、userid对应关系 便于之后通过ctx删除对应的在线人员信息
 * key: ctxChannelId value: userId
 * @author zouyan
 * @create 2018-01-24 下午2:57
 * created by fuck~
 **/
public class SessionManager {

    //登录人员列表
    public static final Map<String, IMSocketService> usersMap = new ConcurrentHashMap<String, IMSocketService>();

    //ctx、userid对应关系
    public static final Map<String, String> ctxMap = new ConcurrentHashMap<String, String>();

    //所有信息
    public static final Map<String, Object> infos = new ConcurrentHashMap<String, Object>();

    //登录人员列表
    public static void putUserSession(String userId, IMSocketService imSocketService) {
        usersMap.put(userId, imSocketService);
    }

    public static IMSocketService getUserSession(String userId) {
        return usersMap.get(userId);
    }

    public static void removeUserSession(String userId) {
        usersMap.remove(userId);
    }

    public static Map<String, Object> infos() {
        infos.put("登录人员列表", usersMap);
        infos.put("ctx、userid对应关系", ctxMap);
        return infos;
    }

    //ctx、userid对应关系
    public static void putCtxSession(String channelId, String userId) {
        ctxMap.put(channelId, userId);
    }

    public static String getCtxSession(String channelId) {
        return ctxMap.get(channelId);
    }

    public static void removeCtxSession(String channelId) {
        ctxMap.remove(channelId);
    }

    public static Boolean userOnline(String userId) {
        String userStatus = StatusType.OFFLINE.type;
        IMSocketService imSocketService = usersMap.get(userId);
        if (imSocketService != null) {
            userStatus = imSocketService.getStatus();
        }
        return StatusType.ONLINE.type.equals(userStatus);
    }

}
