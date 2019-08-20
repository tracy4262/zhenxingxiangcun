package com.ovit.nswy.relationship.controller;

import com.ovit.nswy.relationship.service.IMService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * DESCRIPTION:
 * web im初始化接口
 * 初始化主面板列表信息
 * 我的信息、好友列表、群组列表
 * 农事无忧业务 用户信息、好友列表相关数据均在关系圈中
 * 故从关系圈中获取关系数据
 * 新建单独的web im pool通过此接口获取我的信息、好友列表
 * 群组列表（目前关系圈没有群）故暂时去除web im群
 * @author zouyan
 * @create 2018-01-23 下午1:49
 * created by fuck~
 **/
@RestController
@RequestMapping("/im")
public class IMController {

    private static final Logger logger = LogManager.getLogger(IMController.class);

    @Resource
    private IMService imService;

    /**
     * 初始化主面板列表信息
     * 从关系圈中获取关系数据
     * 新建单独的web im pool通过此接口获取我的信息、好友列表
     * 群组列表（目前关系圈没有群）故暂时去除web im群
     * @param account
     * @return
     */
    @RequestMapping(value="/getInitList/{account}", method = RequestMethod.GET)
    public Map<String, Object> getInitList(@PathVariable String  account) {
        Map<String, Object> result = imService.getInitList(account);
        logger.trace("account:{}, web im初始化面板数据data:{}", account, result);
        return result;
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/getUserById/{id}", method = RequestMethod.GET)
    public Object getUserById(@PathVariable(value = "id") String id) {
        Object rows = imService.getUserById(id);
        logger.debug("根据id:{} 远程获取到的用户信息:{}", id, rows);
        return rows;
    }

}