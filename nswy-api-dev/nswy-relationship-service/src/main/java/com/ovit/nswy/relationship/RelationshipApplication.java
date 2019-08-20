package com.ovit.nswy.relationship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ${huipei.x} on 2017-11-9.
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages={"com.ovit.nswy"})
@EnableDiscoveryClient
@EnableFeignClients
public class RelationshipApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RelationshipApplication.class);
        app.setWebEnvironment(true);
        app.run(args);
    }
}

