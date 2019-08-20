package com.ovit.nswy.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
* DESCRIPTION:
*
* @author zouyan
* @create 2018/3/8-下午5:24
* created by fuck~
**/
//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
public class ConverterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }
}
