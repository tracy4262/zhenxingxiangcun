package com.ovit.nswy.wiki;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * Created by ${huipei.x} on 2017-11-6.
 */
@SpringBootApplication(scanBasePackages={"com.ovit.nswy"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
public class WikiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(WikiApplication.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
    }
}
