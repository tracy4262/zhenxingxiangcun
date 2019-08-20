package com.ovit.nswy.member.config;

import com.mongodb.MongoClientOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * DESCRIPTION:
 *  mongodb 配置类
 **/
@Configuration("mongoOptions")
public class MongoOptions {

    private static final Logger logger = LogManager.getLogger(MongoOptions.class);

    //最大连接数
    @Value("${nswy.mongodb.options.connectionsPerHost: 100}")
    private int connectionsPerHost;

    //最小连接数
    @Value("${nswy.mongodb.options.minConnectionsPerHost: 0}")
    private int minConnectionsPerHost;

    //连接的最大闲置时间 毫秒数
    @Value("${nswy.mongodb.options.maxConnectionIdleTime: 0}")
    private int maxConnectionIdleTime;

    //连接的最大生存时间 毫秒数
    @Value("${nswy.mongodb.options.maxConnectionLifeTime: 0}")
    private int maxConnectionLifeTime;

    //最大等待可用连接的时间 毫秒数
    @Value("${nswy.mongodb.options.maxWaitTime: 120000}")
    private int maxWaitTime;

    //连接超时时间 毫秒数
    @Value("${nswy.mongodb.options.connectTimeout: 10000}")
    private int connectTimeout;

    public MongoClientOptions.Builder getOptionsBuilder() {
        return MongoClientOptions.builder()
                .connectionsPerHost(connectionsPerHost)
                .minConnectionsPerHost(minConnectionsPerHost)
                .maxConnectionIdleTime(maxConnectionIdleTime)
                .maxConnectionLifeTime(maxConnectionLifeTime)
                .maxWaitTime(maxWaitTime)
                .connectTimeout(connectTimeout);
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
