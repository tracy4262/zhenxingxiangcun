package com.ovit.nswy.relationship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;

@Configuration
public class SessionConfiguration {
    @Bean
    public SessionRepository<ExpiringSession> sessionRepository() {
        return new MapSessionRepository();
    }
}
