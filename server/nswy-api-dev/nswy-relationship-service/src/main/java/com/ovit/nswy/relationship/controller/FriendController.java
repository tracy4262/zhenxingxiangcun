package com.ovit.nswy.relationship.controller;

;
import com.ovit.nswy.frame.common.Response;
import com.ovit.nswy.relationship.model.request.FriendChatDTO;
import com.ovit.nswy.relationship.model.response.*;
import com.ovit.nswy.relationship.service.FriendChatService;
import com.ovit.nswy.relationship.service.GroupFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-6.
 */
@RestController
@RequestMapping("/groupfriend")
@Api(tags = "/groupfriend",description="IM管理")
public class FriendController {

    @Autowired
    private GroupFriendService groupFriendService;
    @Autowired
    private  FriendChatService friendChatService;
    @GetMapping("/getInitList/{account}")
    @ApiOperation(value="获取IM初始化好友列表接口", response = Map.class)
    public Response getInitList(@PathVariable String  account){
        InitImDTO result=groupFriendService.getInitList(account);
        return Response.successResult(0,result);
    }

    @GetMapping("/getMembers/{account}")
    @ApiOperation(value="获取IM初始化群组列表接口", response = Map.class)
    public Response getMembers(@PathVariable String  account){
        MemberDTO result=groupFriendService.getMembers(account);
        return Response.successResult(0,result);
    }

    @PostMapping("/saveChatMessage")
    @ApiOperation(value="发送IM消息接口", response = Map.class)
    public Response saveChatMessage(@RequestBody FriendChatDTO friendChatDTO){
        Integer result=friendChatService.saveChatMessage(friendChatDTO);
        return Response.successResult(0,result);
    }

    @PostMapping("/getChatMessage")
    @ApiOperation(value="获取IM消息接口", response = Map.class)
    public Response getChatMessage(@RequestBody FriendChatDTO friendChatDTO){
      List<ChatMessageDTO> result=friendChatService.getChatMessage(friendChatDTO);
        return Response.successResult(0,result);
    }

    @GetMapping("/getUnreadMsgCount/{account}")
    @ApiOperation(value="获取IM初始化群组列表接口", response = Map.class)
    public Response getUnreadMsgCount(@PathVariable String  account){
        Integer result=friendChatService.getUnreadMsgCount(account);
        return Response.successResult(0,result);
    }
}
