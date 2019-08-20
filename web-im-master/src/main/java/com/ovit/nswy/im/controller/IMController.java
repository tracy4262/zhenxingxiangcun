package com.ovit.nswy.im.controller;

import com.ovit.nswy.im.model.ChatMsgHistory;
import com.ovit.nswy.im.netty.SessionManager;
import com.ovit.nswy.im.service.ChatLogService;
import com.ovit.nswy.im.service.IMService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:
 * im初始化controller
 * @author zouyan
 * @create 2018-01-23 上午9:37
 * created by fuck~
 **/
@RestController
@RequestMapping("/im")
public class IMController {

    private static final Logger logger = LogManager.getLogger(IMController.class);

    @Resource
    private IMService imService;

    @Resource
    private ChatLogService chatLogService;

    /**
     * web im聊天记录页面获取聊天记录信息
     * id:好友或群组ID
     * mineId:用户自身id
     * id=123&type=friend
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value="/chatlog")
    public Map<String, Object> chatlog(@RequestParam("id") String id, @RequestParam("mineId") String mineId, @RequestParam("type") String type) {
        List<ChatMsgHistory> data = chatLogService.getChatLogByIdAndType(id, mineId, type);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", "0");
        result.put("data", data);
        return result;
    }

    /**
     * 初始化web im面板数据
     * 我的信息、好友列表、群组列表
     * 注：好友分组支持，目前暂无提供群组列表
     * 返回数据（详见resources，im-demo示例数据）
     * @param account
     * @return
     */
    @RequestMapping(value="/getInitList/{account}", method = RequestMethod.GET)
    public Map<String, Object> getInitList(@PathVariable String  account){
        Map<String, Object> data = imService.getInitList(account);
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", " ");
        result.put("data", data);
        logger.info("account:{}, web-im初始化返回数据result：{}", account, result);
        return result;
    }

    /**
     * 通过用户id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/getUserById/{id}", method = RequestMethod.GET)
    public Object getUserById(@PathVariable String  id) {
        return imService.getUserById(id);
    }

    /**
     * 获取所有用户信息
     * 登录人员列表
     * ctx、userid对应关系
     * @return
     */
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public Map<String, Object> getUsers() {
        return SessionManager.infos();
    }
}
