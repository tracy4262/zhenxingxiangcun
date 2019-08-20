package com.ovit.nswy.relationship.service;

import java.util.Map;

/**
 * DESCRIPTION:
 * im业务数据查询处理
 * @author zouyan
 * @create 2018-01-23 上午9:55
 * created by fuck~
 **/
public interface IMService {

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
    public Map<String, Object> getInitList(String account);

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public Object getUserById(String id);

}
