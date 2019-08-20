package com.fuck.test.im.server.controller;

import com.alibaba.fastjson.JSON;
import com.fuck.test.im.server.netty.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * DESCRIPTION:
 * 模拟外部接口调用获取首页
 * @author zouyan
 * @create 2018-01-09 下午4:26
 * created by fuck~
 **/
@RestController
public class DemoController {

    @RequestMapping("/rooms")
    public String demo() {
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
        return JSON.toJSONString(respMap);

    }
}
