package com.ovit.nswy.converter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-05-11 下午5:12
 * created by fuck~
 **/
@Configuration
public class MultipartConfig {

    @Value("${app.home}")
    private String home;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(home + "/tmp");
        return factory.createMultipartConfig();
    }
}
