package com.ovit.nswy.im.remote;

import com.ovit.nswy.im.remote.hystrix.IMInitRemoteHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
* DESCRIPTION:
* 初始化web im面板
* @author zouyan
* @create 2018/1/23-下午3:11
* created by fuck~
**/
@FeignClient(name = "nswy-relationship-service", fallback = IMInitRemoteHystrix.class)
public interface IMInitRemote {

    /**
     * <p>Description:</p>
     * 初始化主面板列表信息
     * 从关系圈中获取关系数据
     * 新建单独的web im pool通过此接口获取我的信息、好友列表
     * 群组列表（目前关系圈没有群）故暂时去除web im群
     * <p>指标信息：<br>
     * <table border="1" width="200px">
     *  <tr><td><b>是否缓存</b></td><td>:</td><td>否</td></tr>
     * </table>
     * @param account
     * @return String
     * @author zouyan
     * @Date 2018/1/23-下午3:11
     */
    @RequestMapping(value="/im/getInitList/{account}", method = RequestMethod.GET)
    public Map<String, Object> getInitList(@PathVariable(value = "account") String  account);

    /**
     * <p>Description:</p>
     * 根据id获取用户信息
     * <p>指标信息：<br>
     * <table border="1" width="200px">
     *  <tr><td><b>是否缓存</b></td><td>:</td><td>否</td></tr>
     * </table>
     * @param id
     * @return String
     * @author zouyan
     * @Date 2018/1/24-下午1:11
     */
    @RequestMapping(value="/im/getUserById/{id}", method = RequestMethod.GET)
    public Object getUserById(@PathVariable(value = "id") String id);

}
