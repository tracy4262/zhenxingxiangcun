package com.ovit.nswy.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * DESCRIPTION:
 * 使用@ServerEndpoint创立websocket endpoint前
 * 首先要注入ServerEndpointExporter
 * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
 * 如果使用独立的servlet容器，而不是直接使用springboot的内置容器
 * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理
 * @author zouyan
 * @create 2018-01-23 下午8:46
 * created by fuck~
 **/
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
