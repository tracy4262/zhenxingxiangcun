package com.fuck.test.im.server.service;

import com.fuck.test.im.server.model.IMRequest;

import java.io.Serializable;

/**
 * DESCRIPTION:
 * 回调函数
 * @author zouyan
 * @create 2018-01-03 下午5:45
 * created by fuck~
 **/
public interface IMService {

    public void send(IMRequest imRequest) throws Exception;

}
