package com.ovit.nswy.frame.config.bean;

import com.ovit.nswy.frame.common.util.ApplicationContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ${huipei.x} on 2017-11-11.
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    public ApplicationContextHolder initApplicationContextHolder(){
        return new ApplicationContextHolder();
    }
}
