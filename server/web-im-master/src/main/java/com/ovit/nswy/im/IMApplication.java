package com.ovit.nswy.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-22 下午5:21
 * created by fuck~
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class IMApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);
    }
}
