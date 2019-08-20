package com.ovit.nswy.im.remote.hystrix;

import com.ovit.nswy.im.remote.IMInitRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
* DESCRIPTION:
* 初始化web im面板降级操作
* @author zouyan
* @create 2018/1/23-下午3:16
* created by fuck~
**/
@Component
public class IMInitRemoteHystrix implements IMInitRemote {

    private static Logger logger = LogManager.getLogger(IMInitRemoteHystrix.class);

    /**
     * <p>Description:</p>
     * 初始化主面板列表信息
     * @param account
     * @return String
     * @author zouyan
     * @Date 2018/1/23-下午3:16
     */
    @Override
    public Map<String, Object> getInitList(String account) {
        logger.warn("IMInitRemote getInitList failed! start fallback. account:{}", account);
        return null;
    }

    /**
     * <p>Description:</p>
     * 根据id获取用户信息
     * @param id
     * @return String
     * @author zouyan
     * @Date 2018/1/24-下午1:16
     */
    @Override
    public Object getUserById(String id) {
        logger.warn("IMInitRemote getUserById failed! start fallback. id:{}", id);
        return null;
    }
}
