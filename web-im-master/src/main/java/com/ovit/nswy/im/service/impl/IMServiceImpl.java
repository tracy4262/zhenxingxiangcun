package com.ovit.nswy.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.ovit.nswy.im.model.StatusType;
import com.ovit.nswy.im.netty.SessionManager;
import com.ovit.nswy.im.remote.IMInitRemote;
import com.ovit.nswy.im.service.IMService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-23 上午10:03
 * created by fuck~
 **/
@Service
public class IMServiceImpl implements IMService {

    private static final Logger logger = LogManager.getLogger(IMServiceImpl.class);

    @Autowired
    private IMInitRemote imInitRemote;
    /**
     * <p>Description:</p>
     * 初始化我的信息、好友列表、群组列表
     * 通过用户account查询relationship_group获取最上层gruop_id、gruop_name
     * （注：gruop_id单词拼写）
     * 通过用户account、最上层group_id查询relationship_friend
     * （agree_type = 2，同意添加好友）
     * <p>返回参数（详见resources，im-demo示例数据）：<br>
     * <table border="1" width="200px">
     *  <tr><td><b>code</b></td><td>:</td><td>成功、失败标识，0为成功</td></tr>
     *  <tr><td><b>msg</b></td><td>:</td><td>失败信息</td></tr>
     *  <tr><td><b>data</b></td><td>:</td><td>初始化数据信息</td></tr>
     * </table>
     * @param account
     * @return Map<String, Object>
     * @author zouyan
     * @Date 2018-01-23 上午9:55
     */
    @Override
    public Map<String, Object> getInitList(String account) {
        Map<String, Object> data = imInitRemote.getInitList(account);
        return data;
    }

    /**
     * 根据id获取用户信息
     * 返回数据示例：
     {
     "msg": "OK",
     "code": 200,
     "data": {
     "tourNumber": 0,
     "loginAccount": "chqm",//登录账号
     "loginPasswd": "7f99622fa3f4fc93a5a6c2356ffab219ff89f538",//登录密码
     "registerTime": "2018-01-02",//注册时间
     "displayName": "陈起铭",//昵称
     "isIdentityVerification": "/pro/member/step35/step38",
     "followInfo": {},
     "signaTure": "大家好！",
     "avatar": "http://192.168.7.61/00/31/wKgHPVpK5PSAK6oTAAEryTGaew0016.png",//头像
     "id": 1, //用户Id
     "userType": 5,//用户类型
     "uniqueId": "035dbd2e8tbf4820be1dde0f8aa50b45"
     }
     }
     * @param id
     * @return
     */
    @Override
    public Object getUserById(String id) {
        Object data = imInitRemote.getUserById(id);
        logger.debug("根据id:{} 远程获取到的用户信息:{}", id, data);
        return data;
    }
}
