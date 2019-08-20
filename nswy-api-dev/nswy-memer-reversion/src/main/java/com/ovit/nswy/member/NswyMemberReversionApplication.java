package com.ovit.nswy.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}, scanBasePackages={"com.ovit.nswy"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class NswyMemberReversionApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NswyMemberReversionApplication.class);
        app.setWebEnvironment(true);
        app.run(args);
    }

}